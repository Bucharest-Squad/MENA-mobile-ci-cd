package net.thechance.mena.core_chat.presentation.screen.messaging

import kotlinx.datetime.LocalDateTime

abstract class MessageUiState(
    open val id: String,
    open val senderId: String,
    open val time: LocalDateTime,
    open val status: MessageStatus,
    open val isMine: Boolean
)

enum class MessageStatus {
    SENDING,
    SENT,
    READ,
    FAILED
}

data class MarkedMessageUiState(
    val message: MessageUiState,
    val isMarkedLastInSeries: Boolean
)

data class TextMessageUiState(
    override val id: String,
    override val senderId: String,
    override val time: LocalDateTime,
    override val status: MessageStatus,
    override val isMine: Boolean,
    val text: String
): MessageUiState(
    id,
    senderId,
    time,
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

