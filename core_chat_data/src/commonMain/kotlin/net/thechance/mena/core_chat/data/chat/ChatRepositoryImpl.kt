package net.thechance.mena.core_chat.data.chat

import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.util.reflect.typeInfo
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
import kotlinx.serialization.json.Json
import net.thechance.mena.core_chat.data.chat.dto.MessageDto
import net.thechance.mena.core_chat.data.chat.dto.SendMessageDto
import net.thechance.mena.core_chat.data.network.ApiConstants.CHAT_ENDPOINT
import net.thechance.mena.core_chat.data.network.ApiConstants.WEB_SOCKETS_ENDPOINT
import net.thechance.mena.core_chat.data.shared.BaseRepository
import net.thechance.mena.core_chat.domain.entity.Chat
import net.thechance.mena.core_chat.domain.entity.Message
import net.thechance.mena.core_chat.domain.repository.ChatRepository
import net.thechance.mena.identity.domain.repository.AuthenticationRepository
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class ChatRepositoryImpl(
    private val client: HttpClient,
    private val json: Json,
    private val baseUrl: String,
    private val authenticationRepository: AuthenticationRepository,
) : ChatRepository, BaseRepository {

    private var isWebSocketSessionActive = false
    private var webSocketSession: DefaultClientWebSocketSession? = null
    private val messageFlows = MutableSharedFlow<Message>()
    private val scope = CoroutineScope(Dispatchers.IO)


    override suspend fun sendMessage(message: Message) {
        if (webSocketSession != null && webSocketSession?.isActive == true) {
            val messageJson = json.encodeToString(
                SendMessageDto.serializer(),
                message.toSendMessageRequestDto()
            )
            val frameText = "SEND\ndestination:/app/chat.privateMessage\n\n$messageJson\n\n\u0000"
            val frame = Frame.Text(frameText)
            println("Sending frame : $frameText")
            webSocketSession?.send(frame)
        }
    }

    override suspend fun loadMessages(chatId: Uuid): List<Message> {

        TODO("Not yet implemented")
    }

    override suspend fun markMessagesAsRead(chatId: Uuid) {

    }

    override fun subscribeToMessages(chatId: Uuid): Flow<Message> {
        scope.launch {
            initializeWebsocketConnection()
            if (webSocketSession != null && webSocketSession?.isActive == true) {
                observeMessages(chatId.toString())

            }
        }
        return messageFlows
    }

    override suspend fun getChatById(chatId: Uuid): Chat {
        TODO("Not yet implemented")
    }

    override suspend fun getOrCreateConversation(
        receiverId: String,
    ): Chat? {
        return tryNetworkCall<Chat>(
            bodyType = typeInfo<Chat>()
        ) {
            val token = authenticationRepository.getAccessToken()

            client.get(CHAT_ENDPOINT) {
                parameter("receiverId", receiverId)
                bearerAuth(token)
            }
        }
    }

    override suspend fun getChatByContactUserId(userId: Uuid): Chat {
        TODO("Not yet implemented")
    }

    // --------------------------------WebSocket Connection--------------------------------

    private suspend fun initializeWebsocketConnection() {
        try {
            val webSocketUrlString =
                "${baseUrl.replace("https", "ws").replace("http", "ws")}$WEB_SOCKETS_ENDPOINT"
            println("WebSocket URL String : $webSocketUrlString")

            val bearerToken = authenticationRepository.getAccessToken()
            client.webSocket(
                urlString = webSocketUrlString,
                request = {
                    bearerAuth(bearerToken)
                }
            ) {
                webSocketSession = this
                isWebSocketSessionActive = isActive
                println("Connection established : isWebSocketActive = $isWebSocketSessionActive")

                val connectFrame = "CONNECT\naccept-version:1.2\nheart-beat:10000,10000\n\n\u0000"
                send(Frame.Text(connectFrame))
                println("STOMP connected! 1- ok $connectFrame")

            }
        } catch (e: Exception) {
            println("Error: ${e.message}")
        }

    }

    private suspend fun observeMessages(chatId: String) {
        try {
            val subscribeFrame =
                "SUBSCRIBE\nid:sub-0\ndestination:/user/$chatId/queue/messages\n\n\u0000"
            println("STOMP subscribeFrame! 2- ok $subscribeFrame")
            webSocketSession?.send(Frame.Text(subscribeFrame))


            webSocketSession?.incoming
                ?.receiveAsFlow()
                ?.filter { it is Frame.Text }
                ?.collect { frame ->
                    println("Received frame : $frame")
                    println("Received frame : ${(frame as Frame.Text).readText()}")
                    val payload = frame.readText()
                    if (payload.startsWith("MESSAGE")) {
                        val body = payload.substringAfter("\n\n").trimEnd('\u0000')
                        val messageDto = json.decodeFromString(MessageDto.serializer(), body)
                        messageFlows.emit(messageDto.toDomain())
                    }
                }
        } catch (e: Exception) {
            println("Error: ${e.printStackTrace()}")
        }
    }

}