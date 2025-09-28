package net.thechance.mena.core_chat.data.chat

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import io.ktor.client.HttpClient
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.util.reflect.typeInfo
import kotlinx.coroutines.flow.Flow
import net.thechance.mena.core_chat.data.network.ApiConstants.CHAT_ENDPOINT
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
    private val authenticationRepository: AuthenticationRepository,

    ) : ChatRepository, BaseRepository {
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


}