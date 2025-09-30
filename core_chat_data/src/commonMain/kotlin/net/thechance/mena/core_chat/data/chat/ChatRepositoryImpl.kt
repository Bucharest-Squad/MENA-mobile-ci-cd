package net.thechance.mena.core_chat.data.chat

import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.webSocket
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.util.reflect.typeInfo
import io.ktor.websocket.Frame
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.isActive
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
    private val baseUrl: String,
    private val authenticationRepository: AuthenticationRepository,
) : ChatRepository, BaseRepository {

    private var isWebSocketSessionActive = false
    private var webSocketSession: DefaultClientWebSocketSession? = null

    override suspend fun sendMessage(message: Message) {
        TODO("Not yet implemented")
    }

    override suspend fun loadMessages(chatId: Uuid): List<Message> {
        TODO("Not yet implemented")
    }

    override suspend fun markMessagesAsRead(chatId: Uuid) {
        TODO("Not yet implemented")
    }

    override fun subscribeToMessages(chatId: Uuid): Flow<Message> {
        TODO("Not yet implemented")
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
        val webSocketUrlString =
            "${baseUrl.replace("https", "ws").replace("http", "ws")}$WEB_SOCKETS_ENDPOINT"
        println(webSocketUrlString)
        client.webSocket(
            urlString = webSocketUrlString
        ) {
            webSocketSession = this
            isWebSocketSessionActive = isActive
            println("Connection established : isWebSocketActive = $isWebSocketSessionActive")

            val connectFrame = "CONNECT\naccept-version:1.2\nheart-beat:10000,10000\n\n\u0000"
            send(Frame.Text(connectFrame))
            println("STOMP connected! 1- ok $connectFrame")

        }

    }
}