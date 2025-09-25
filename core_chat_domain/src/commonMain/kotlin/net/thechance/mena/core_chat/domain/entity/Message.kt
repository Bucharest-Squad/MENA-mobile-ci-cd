@file:OptIn(ExperimentalUuidApi::class)

package net.thechance.mena.core_chat.domain.entity

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlinx.datetime.LocalDateTime

data class Message(
    val id: Uuid,
    val senderId: Uuid,
    val text: String,
    val chatId: Uuid,
    val status: MessageStatus,
    val sendAt: LocalDateTime
)

enum class MessageStatus {
    SENT,
    Read,
    Failed,
    Loading
}