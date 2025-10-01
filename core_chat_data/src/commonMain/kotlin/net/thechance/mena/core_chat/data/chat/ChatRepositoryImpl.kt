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
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import net.thechance.mena.core_chat.data.chat.dto.ChatDto
import net.thechance.mena.core_chat.data.chat.dto.MessageDto
import net.thechance.mena.core_chat.data.chat.dto.SendMessageDto
import net.thechance.mena.core_chat.data.database.dao.MessageDao
import net.thechance.mena.core_chat.data.network.ApiConstants.CHAT_ENDPOINT
import net.thechance.mena.core_chat.data.network.ApiConstants.CHAT_HISTORY_ENDPOINT
import net.thechance.mena.core_chat.data.network.ApiConstants.WEB_SOCKETS_ENDPOINT
import net.thechance.mena.core_chat.data.shared.BaseRepository
import net.thechance.mena.core_chat.data.shared.dto.PagedDataDto
import net.thechance.mena.core_chat.domain.entity.Chat
import net.thechance.mena.core_chat.domain.entity.Message
import net.thechance.mena.core_chat.domain.entity.MessageStatus
import net.thechance.mena.core_chat.domain.exception.ChatNotFoundException
import net.thechance.mena.core_chat.domain.exception.SendMessageFailedException
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
    private val messageDao: MessageDao
) : ChatRepository, BaseRepository {

    private var isWebSocketSessionActive = false
    private var webSocketSession: DefaultClientWebSocketSession? = null
    private val messageFlows = MutableSharedFlow<Message>()
    private val scope = CoroutineScope(Dispatchers.IO)
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Error in ChatRepositoryImpl: ${throwable.message}")
        isWebSocketSessionActive = false
    }


    override suspend fun loadMessages(chatId: Uuid): List<Message> {
        return tryNetworkCall<PagedDataDto<MessageDto>>(
            bodyType = typeInfo<PagedDataDto<MessageDto>>()
        ) {
            val token = authenticationRepository.getAccessToken()

            client.get(CHAT_HISTORY_ENDPOINT) {
                parameter("chatId", chatId)
                parameter("page", 0)
                bearerAuth(token)
            }
        }?.data?.map { it.toDomain() } ?: emptyList()
    }


    override suspend fun sendMessage(message: Message) {
        val sendingMessage = message.copy(status = MessageStatus.LOADING)
        try {
            messageDao.insertMessage(sendingMessage.toMessageEntity())
            println("Message inserted into local DB with LOADING status: ${sendingMessage.text}, id: ${sendingMessage.id}")
            if (webSocketSession?.isActive == true) {
                val messageJson = json.encodeToString(
                    SendMessageDto.serializer(),
                    sendingMessage.toSendMessageRequestDto()
                )
                val frameText =
                    "SEND\ndestination:/app/chat.privateMessage\n\n$messageJson\n\n\u0000"

                println("Sending frame : $frameText")
                webSocketSession?.send(Frame.Text(frameText))
                println("Message sent successfully: ${sendingMessage.text}, id: ${sendingMessage.id}")

                messageDao.deleteMessage(sendingMessage.id.toString())
                println("Message deleted from local DB after sending: ${sendingMessage.id}")

            } else {
                val failedMessage = sendingMessage.copy(status = MessageStatus.FAILED)
                messageDao.updateMessageStatus(failedMessage.id.toString(), net.thechance.mena.core_chat.data.database.entity.MessageStatus.FAILED)
                throw SendMessageFailedException("WebSocket is not active. Failed to send message.")
            }
        } catch (e: Exception) {
            val failedMessage = sendingMessage.copy(status = MessageStatus.FAILED)
            messageDao.updateMessageStatus(failedMessage.id.toString(), net.thechance.mena.core_chat.data.database.entity.MessageStatus.FAILED)
            println("Error sending message: ${e.message}")
            throw SendMessageFailedException("Failed to send message: ${e.message}")
        }
    }


    override fun subscribeToMessages(chatId: Uuid): Flow<Message> {
        scope.launch(exceptionHandler) {
            initializeWebsocketConnection(chatId.toString())
        }
        return messageFlows
    }

    override suspend fun getChatByContactUserId(userId: Uuid): Chat {
        return tryNetworkCall<ChatDto>(
            bodyType = typeInfo<ChatDto>()
        ) {
            val token = authenticationRepository.getAccessToken()

            client.get(CHAT_ENDPOINT) {
                parameter("receiverId", userId)
                bearerAuth(token)
            }
        }?.toDomain() ?: throw ChatNotFoundException("Chat not found")
    }

    private suspend fun initializeWebsocketConnection(chatId: String) {
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
                println("Connection established: isActive=$isWebSocketSessionActive")

                val connectFrame = "CONNECT\naccept-version:1.2\nheart-beat:10000,10000\n\n\u0000"
                send(Frame.Text(connectFrame))
                println("STOMP CONNECT sent")

                var connected = false

                for (frame in incoming) {
                    if (frame is Frame.Text) {
                        val text = frame.readText()
                        println("Frame received: $text")

                        when {
                            text.startsWith("CONNECTED") -> {
                                println("STOMP CONNECTED from server ✅")
                                connected = true

                                val subscribeFrame =
                                    "SUBSCRIBE\nid:sub-0\ndestination:/user/$chatId/queue/messages\n\n\u0000"
                                send(Frame.Text(subscribeFrame))
                                println("STOMP SUBSCRIBE sent to /user/$chatId/queue/messages")
                            }

                            text.startsWith("MESSAGE") -> {
                                val body = text.substringAfter("\n\n").trimEnd('\u0000')
                                println("Message payload: $body")

                                try {
                                    val messageDto =
                                        json.decodeFromString(MessageDto.serializer(), body)
                                    messageFlows.emit(messageDto.toDomain())
                                } catch (e: Exception) {
                                    println("Error parsing message: ${e.message}")
                                }
                            }

                            text.startsWith("ERROR") -> {
                                println("STOMP ERROR: $text")
                            }
                        }
                    }
                }

                println("WebSocket session ended.")
            }

        } catch (e: Exception) {
            isWebSocketSessionActive = false
            println("Error in websocket: ${e.message}")
            throw e
        }
    }
}