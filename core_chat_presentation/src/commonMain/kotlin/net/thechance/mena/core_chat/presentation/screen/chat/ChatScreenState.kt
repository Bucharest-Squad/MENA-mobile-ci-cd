package net.thechance.mena.core_chat.presentation.screen.chat


data class ChatScreenState(
    val chat: ChatUiState = ChatUiState(),
    val inputMessage: String = "",
    val chatListItems: List<ChatListItem> = emptyList(),
    val uiMessages: List<MessageUiState> = emptyList(),

    val userId: String = "bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb", // temp until login

    val isResendMessageDialogVisible: Boolean = false,

    val failedMessageToReSend: MessageUiState? = null
)
