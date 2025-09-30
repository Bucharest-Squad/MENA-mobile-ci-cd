package net.thechance.mena.core_chat.data.chat

import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.client.request.bearerAuth
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.json.Json
import net.thechance.mena.core_chat.data.chat.dto.MessageDto
import net.thechance.mena.core_chat.data.chat.utils.minusMinutes
import net.thechance.mena.core_chat.data.chat.utils.now
import net.thechance.mena.core_chat.data.network.ApiConstants.WEB_SOCKETS_ENDPOINT
import net.thechance.mena.core_chat.domain.entity.Chat
import net.thechance.mena.core_chat.domain.entity.Message
import net.thechance.mena.core_chat.domain.entity.MessageStatus
import net.thechance.mena.core_chat.domain.exception.NotMenaMemberException
import net.thechance.mena.core_chat.domain.repository.ChatRepository
import net.thechance.mena.identity.domain.repository.AuthenticationRepository
import kotlin.time.ExperimentalTime
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)
class FakeChatRepository(
    private val client: HttpClient,
    private val json: Json,
    private val baseUrl: String,
    private val authenticationRepository: AuthenticationRepository,
) : ChatRepository {

    private var isWebSocketSessionActive = false
    private var webSocketSession: DefaultClientWebSocketSession? = null
    private val scope = CoroutineScope(Dispatchers.IO)
    private lateinit var token: String
    private val chats = mutableMapOf<Uuid, Chat>()
    private val messages = mutableMapOf<Uuid, MutableList<Message>>()
    private val messageFlows = mutableMapOf<Uuid, MutableSharedFlow<Message>>()

    init {
        scope.launch {
            token = authenticationRepository.getAccessToken()
        }
        val chat1Id = Uuid.parse("11111111-1111-1111-1111-111111111111")
        val chat2Id = Uuid.parse("22222222-2222-2222-2222-222222222222")

        val userNoor = Uuid.parse("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
        val userBilal = Uuid.parse("bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb")
        val otherOne = Uuid.parse("cccccccc-cccc-cccc-cccc-cccccccccccc")

        chats[chat1Id] = Chat(chat1Id, "https://i.ibb.co/fGB4f3cK/tc-logo.png", "General Chat")
        chats[chat2Id] = Chat(chat2Id, "https://picsum.photos/201", "Random Chat")

        messages[chat1Id] = (1..20).map { index ->
            Message(
                id = Uuid.random(),
                senderId = if (index % 2 == 0) userNoor else userBilal,
                chatId = chat1Id,
                text = if (index % 2 == 0) "Noor’s message #$index" else "Bilal’s reply #$index",
                sendAt = LocalDateTime.now().minusMinutes((21 - index)),
                status = when {
                    index < 10 -> MessageStatus.READ
                    index in 10..18 -> MessageStatus.SENT
                    else -> MessageStatus.LOADING
                }
            )
        }.toMutableList()

        messages[chat2Id] = (1..10).map { index ->
            Message(
                id = Uuid.random(),
                senderId = if (index % 2 == 0) userNoor else otherOne,
                chatId = chat2Id,
                text = if (index % 2 == 0) "Random Noor msg #$index" else "Random otherOne msg #$index",
                sendAt = LocalDateTime.now().minusMinutes((21 - index)),
                status = if (index <= 5) MessageStatus.READ else MessageStatus.SENT
            )
        }.toMutableList()
    }


    override suspend fun sendMessage(message: Message) {
        val chatId = message.chatId
        val list = messages.getOrPut(chatId) { mutableListOf() }
        list.add(message)

        val flow = messageFlows.getOrPut(chatId) { MutableSharedFlow(extraBufferCapacity = 64) }
        flow.emit(message)

        sendMessageViaWebSocketSession(message.copy(
            chatId = Uuid.parse("506044c9-522f-4102-a8f6-2422eba35ee1"),
            senderId = Uuid.parse("9a629aaa-8907-4dc6-ac33-f79fef7b4251")
        ))
    }

    override suspend fun getOrCreateConversation(receiverId: String): Chat {
        TODO("Not yet implemented")
    }

    override suspend fun loadMessages(chatId: Uuid): List<Message> {
        return messages[chatId]?.toList() ?: emptyList()
    }

    override suspend fun markMessagesAsRead(chatId: Uuid) {
        val updated = messages[chatId]?.map {
            it.copy(status = MessageStatus.READ)
        } ?: return
        messages[chatId] = updated.toMutableList()
    }

    override fun subscribeToMessages(chatId: Uuid): Flow<Message> {
        scope.launch {
            initializeWebsocketConnection("506044c9-522f-4102-a8f6-2422eba35ee1")
            println("Connection established : isWebSocketActive = $isWebSocketSessionActive")
            //if (isWebSocketSessionActive) observeMessages("506044c9-522f-4102-a8f6-2422eba35ee1")
        }

        return messageFlows.getOrPut(chatId) { MutableSharedFlow(extraBufferCapacity = 64) }
    }

    override suspend fun getChatById(chatId: Uuid): Chat {
        return chats.getOrPut(chatId) { Chat(chatId, null, "Mock Chat $chatId") }
    }

    override suspend fun getChatByContactUserId(userId: Uuid): Chat {
        val chatId = messages.entries
            .firstOrNull { (_, msgs) ->
                msgs.any { it.senderId == userId }
            }?.key
        val contactMenaMemberId = Uuid.parse("cccccccc-dddd-cccc-cccc-cccccccccccc")

        if (userId == contactMenaMemberId) return Chat(
            Uuid.random(),
            "https://picsum.photos/201",
            "First Time Chat"
        )

        return chatId?.let { chats[it] }
            ?: throw NotMenaMemberException(logMessage = "Can not get Chat for non MENA member")
    }

    // --------------------------------WebSocket Connection--------------------------------

    private suspend fun initializeWebsocketConnection(chatId: String) {
        val webSocketUrlString =
            "${baseUrl.replace("https", "ws").replace("http", "ws")}$WEB_SOCKETS_ENDPOINT"
        println("WebSocket URL String : $webSocketUrlString")
        client.webSocket(
            urlString = webSocketUrlString,
            request = {
                bearerAuth(token)
            }
        ) {
            webSocketSession = this
            isWebSocketSessionActive = isActive
            println("Connection established : isWebSocketActive = $isWebSocketSessionActive")

            val connectFrame = "CONNECT\naccept-version:1.2\nheart-beat:10000,10000\n\n\u0000"
            send(Frame.Text(connectFrame))
            println("STOMP connected! 1- ok $connectFrame")


            val subscribeFrame = "SUBSCRIBE\nid:sub-0\ndestination:/user/$chatId/queue/messages\n\n\u0000"
            println("STOMP subscribeFrame! 2- ok $subscribeFrame")
            webSocketSession?.send(Frame.Text(subscribeFrame))

            incoming
                .receiveAsFlow()
                .filter { it is Frame.Text }
                .collect { frame ->
                    println("Received frame : $frame")
                    println("Received frame : ${(frame as Frame.Text).readText()}")
                }
        }

    }

    private suspend fun observeMessages(chatId: String) {
        val subscribeFrame = "SUBSCRIBE\nid:sub-0\ndestination:/user/$chatId/queue/messages\n\n\u0000"
        println("STOMP subscribeFrame! 2- ok $subscribeFrame")
        webSocketSession?.send(Frame.Text(subscribeFrame))

        webSocketSession
            ?.incoming
            ?.receiveAsFlow()
            ?.filter { it is Frame.Text }
            ?.collect { frame ->
                println("Received frame : $frame")
            }
    }

    private suspend fun sendMessageViaWebSocketSession(message: Message) {
        if (webSocketSession != null) {
            val messageJson = json.encodeToString(MessageDto.serializer(), message.toDto())
            val frameText = "SEND\ndestination:/app/chat/chat.privateMessage\n\n$messageJson\u0000"
            val frame = Frame.Text(frameText)
            println("Sending frame : $frameText")
            webSocketSession?.send(frame)
        }
    }

}