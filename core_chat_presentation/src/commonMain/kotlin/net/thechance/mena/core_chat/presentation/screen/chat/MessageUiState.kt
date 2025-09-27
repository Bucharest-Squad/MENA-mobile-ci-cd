@file:OptIn(ExperimentalUuidApi::class)

package net.thechance.mena.core_chat.presentation.screen.chat

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import net.thechance.mena.core_chat.domain.entity.Message
import net.thechance.mena.core_chat.presentation.utils.format
import net.thechance.mena.core_chat.presentation.utils.minusDays
import net.thechance.mena.core_chat.presentation.utils.now
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import net.thechance.mena.core_chat.domain.entity.MessageStatus as DomainMessageStatus

abstract class MessageUiState(
    open val id: String,
    open val senderId: String,
    open val chatId: String,
    open val sendTime: LocalDateTime,
    open val status: MessageStatusUiState,
    open val isMine: Boolean,
)

enum class MessageStatusUiState {
    SENDING,
    SENT,
    READ,
    FAILED
}

data class MarkedMessageUiState(
    val message: MessageUiState,
    val isMarkedLastInSeries: Boolean,
    val showMessageInfo: Boolean = false
)

data class TextMessageUiState(
    override val id: String = "",
    override val senderId: String = "",
    override val chatId: String = "",
    override val sendTime: LocalDateTime,
    override val status: MessageStatusUiState,
    override val isMine: Boolean,
    val text: String
) : MessageUiState(
    id,
    senderId,
    chatId,
    sendTime,
    status,
    isMine
)

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
        val messageDate = item.message.sendTime.date

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
                else -> messageDate.format()
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
        chatId = chatId.toString(),
        sendTime = sendAt,
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
        sendAt = sendTime,
        status = status.toEntityStatus()
    )
}


private fun DomainMessageStatus.toUiStatus(): MessageStatusUiState {
    return when (this) {
        DomainMessageStatus.LOADING -> MessageStatusUiState.SENDING
        DomainMessageStatus.SENT -> MessageStatusUiState.SENT
        DomainMessageStatus.READ -> MessageStatusUiState.READ
        DomainMessageStatus.FAILED -> MessageStatusUiState.FAILED
    }
}

private fun MessageStatusUiState.toEntityStatus(): DomainMessageStatus {
    return when (this) {
        MessageStatusUiState.SENDING -> DomainMessageStatus.LOADING
        MessageStatusUiState.SENT -> DomainMessageStatus.SENT
        MessageStatusUiState.READ -> DomainMessageStatus.READ
        MessageStatusUiState.FAILED -> DomainMessageStatus.FAILED
    }
}