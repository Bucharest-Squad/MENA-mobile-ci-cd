@file:OptIn(ExperimentalUuidApi::class, ExperimentalTime::class)

package net.thechance.mena.core_chat.data.chat

import net.thechance.mena.core_chat.data.chat.dto.MessageDto
import net.thechance.mena.core_chat.data.chat.utils.toLocalDateTime
import net.thechance.mena.core_chat.domain.entity.Message
import net.thechance.mena.core_chat.domain.entity.MessageStatus
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


fun MessageDto.toDomain() = Message(
    id = Uuid.parse(id),
    senderId = Uuid.parse(senderId),
    chatId = Uuid.parse(chatId),
    text = text,
    sendAt = Instant.parse(sendAt).toLocalDateTime(),
    status = if (isRead) MessageStatus.READ else MessageStatus.SENT
)

fun Message.toDto() = MessageDto(
    id = id.toString(),
    senderId = senderId.toString(),
    chatId = chatId.toString(),
    text = text,
    sendAt = sendAt.toString(),
    isRead = status == MessageStatus.READ
)