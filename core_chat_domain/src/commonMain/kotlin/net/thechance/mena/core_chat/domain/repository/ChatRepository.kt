@file:OptIn(ExperimentalUuidApi::class)

package net.thechance.mena.core_chat.domain.repository

import net.thechance.mena.core_chat.domain.entity.Chat
import net.thechance.mena.core_chat.domain.entity.Message
import net.thechance.mena.core_chat.domain.model.PagedData
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

interface ChatRepository {
    suspend fun getChatById(chatId: Uuid): Chat
    suspend fun getChatBySenderId(senderId: Uuid): Chat

    fun getIncomingMessages(chatId: Uuid): Flow<Message>
    suspend fun getChatHistory(chatId: Uuid, pageNumber: Int, pageSize: Int): PagedData<Message>

    suspend fun sendMessage(
        chatId: Uuid,
        message: Message
    )
}