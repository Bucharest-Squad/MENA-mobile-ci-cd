package net.thechance.mena.core_chat.presentation.screen.chat


interface ChatInteractionListener : MessageListInteractionListener {

    fun onBackClicked()
    fun onMenuClicked()

    fun onChatActionsDialogDismissed()

    fun onInputMessageChanged(value: String)
    fun onSendMessageClicked()

    fun onDeleteChatClicked()
    fun onDeleteChatDialogDismissed()
    fun onConfirmDeleteChat()
}

interface MessageListInteractionListener {
    fun onMessageClicked(messageId: String)

    fun onFailedMessageClicked(message: MessageUiState)

    fun onDeleteFailedMessageClicked()

    fun onResendMessageClicked()
    fun onResendMessageDialogDismissed()
}