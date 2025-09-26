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
    fun onFailedMessageClick(messageId: String)

    // resend dialog
    fun onResendMessageClick(messageId: String)
    fun onDismissResendMessageDialog()

    //chat actions dialog
    fun onChatActionsDialogDismiss()
    fun onDeleteChatClick()

    //ic_delete chat dialog
    fun onDismissDeleteChatDialog()
    fun onConfirmDeleteChat()
}