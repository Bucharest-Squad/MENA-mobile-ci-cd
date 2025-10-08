package net.thechance.mena.core_chat.presentation.screen.chats

import net.thechance.mena.core_chat.domain.entity.MessageStatus
import net.thechance.mena.core_chat.presentation.screen.chat.TextMessageUiState

data class ChatsUiState(
    val isLoading: Boolean = false,
    val isSynced: Boolean = false,
    val name: String = "",
    val lastMessage: String = "",
    val isMenaUser: Boolean = false,
    val messageStatus: MessageStatus = MessageStatus.SENT,
    val balance: Double = 0.0,
    val messageUiState: TextMessageUiState,
)