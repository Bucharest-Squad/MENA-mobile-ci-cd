package net.thechance.mena.core_chat.presentation.screen.messaging

interface MessagingInteractionListener: ChatInteractionListener, MessageListInteractionListener {

    fun onBackClick()
    fun onMenuClick()

    fun onChatActionsDialogDismiss()

    fun onInputMessageChange(value: String)
    fun onSendMessageClick()
}

interface ChatInteractionListener {

    fun onDeleteChatClick()
    fun onDismissDeleteChatDialog()
    fun onConfirmDeleteChat()
}


interface MessageListInteractionListener {

    fun onMessageClick(messageId: String)

    fun onFailedMessageClick(messageId: String)

    fun onResendMessageClick(messageId: String)
    fun onDismissResendMessageDialog()
}