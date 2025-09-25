@file:OptIn(ExperimentalUuidApi::class)

package net.thechance.mena.core_chat.presentation.utils

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import net.thechance.mena.core_chat.domain.entity.Message
import net.thechance.mena.core_chat.presentation.screen.messaging.ChatListItem
import net.thechance.mena.core_chat.presentation.screen.messaging.MarkedMessageUiState
import net.thechance.mena.core_chat.presentation.screen.messaging.MessageStatus
import net.thechance.mena.core_chat.presentation.screen.messaging.MessageUiState
import net.thechance.mena.core_chat.presentation.screen.messaging.TextMessageUiState
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import net.thechance.mena.core_chat.domain.entity.MessageStatus as DomainMessageStatus


fun List<MessageUiState>.markLastInSeries(): List<MarkedMessageUiState> {
    return this.mapIndexed { index, message ->
        val nextSender = this.getOrNull(index - 1)?.senderId
        val isLastInSeries = nextSender == null || nextSender != message.senderId

        MarkedMessageUiState(message, isLastInSeries)
    }
}


fun List<MarkedMessageUiState>.withDateSeparators(
    todayLabel: String = "Today",
    yesterdayLabel: String = "Yesterday"
): List<ChatListItem> {
    if (this.isEmpty()) return emptyList()

    val today = LocalDateTime.now().date
    val yesterday = today.minusDays(1)

    val result = mutableListOf<ChatListItem>()
    var lastDate: LocalDate? = null
    var lastMessageIndexInDate: Int? = null

    val reversed = this.asReversed()

    for ((index, item) in reversed.withIndex()) {
        val messageDate = item.message.time.date

        if (messageDate != lastDate) {
            lastMessageIndexInDate?.let { idx ->
                val msgItem = result[idx] as ChatListItem.Message
                result[idx] = msgItem.copy(
                    data = msgItem.data.copy(isMarkedLastInSeries = true)
                )
            }

            val label = when (messageDate) {
                today -> todayLabel
                yesterday -> yesterdayLabel
                else -> messageDate.toString()
            }
            result.add(ChatListItem.DateSeparator(label))
            lastDate = messageDate
        }

        result.add(ChatListItem.Message(item))
        lastMessageIndexInDate = result.lastIndex

        if (index == reversed.lastIndex) {
            val msgItem = result[lastMessageIndexInDate] as ChatListItem.Message
            result[lastMessageIndexInDate] = msgItem.copy(
                data = msgItem.data.copy(isMarkedLastInSeries = true)
            )
        }
    }

    return result.asReversed()
}


fun Message.toUi(currentUserId: String): TextMessageUiState {
    return TextMessageUiState(
        id = id.toString(),
        senderId = senderId.toString(),
        time = sendAt,
        status = status.toUiStatus(),
        isMine = senderId.toString() == currentUserId,
        text = text
    )
}

fun TextMessageUiState.toEntity(): Message {
    return Message(
        id = Uuid.parse(id),
        senderId = Uuid.parse(senderId),
        chatId = Uuid.parse(chatId),
        text = text,
        sendAt = time,
        status = status.toEntityStatus()
    )
}


private fun DomainMessageStatus.toUiStatus(): MessageStatus {
    return when (this) {
        DomainMessageStatus.LOADING -> MessageStatus.SENDING
        DomainMessageStatus.SENT -> MessageStatus.SENT
        DomainMessageStatus.READ -> MessageStatus.READ
        DomainMessageStatus.FAILED -> MessageStatus.FAILED
    }
}

private fun MessageStatus.toEntityStatus(): DomainMessageStatus {
    return when (this) {
        MessageStatus.SENDING -> DomainMessageStatus.LOADING
        MessageStatus.SENT -> DomainMessageStatus.SENT
        MessageStatus.READ -> DomainMessageStatus.READ
        MessageStatus.FAILED -> DomainMessageStatus.FAILED
    }
}