package net.thechance.mena.core_chat.presentation.screen.messaging

interface MessagingInteractionListener: ChatInteractionListener, MessageListInteractionListener {

    fun onBackClick()
    fun onMenuClick()

    fun onDismissChatActionsDialog()

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

    fun onFailedMessageClick(message: MessageUiState)

    fun onDeleteFailedMessageClick()
    fun onResendMessageClick()
    fun onDismissResendMessageDialog()
}