package net.thechance.mena.core_chat.presentation.screen.messaging

interface MessagingInteractionListener {
    //header
    fun onBackClick()
    fun onMenuClick()

    // input bar
    fun onInputMessageChange(value: String)
    fun onSendMessageClick()

    //messages
    fun onMessageClick(messageId: String)
    fun onFailedMessageClick(message: TextMessageUiState)

    // resend dialog
    fun onResendMessageClick()
    fun onDeleteMessageClick()
    fun onDismissResendMessageDialog()

    //chat actions dialog
    fun onDismissChatActionsDialog()
    fun onDeleteChatClick()

    //delete chat dialog
    fun onDismissDeleteChatDialog()
    fun onConfirmDeleteChat()
}