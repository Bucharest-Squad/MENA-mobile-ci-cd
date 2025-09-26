package net.thechance.mena.core_chat.presentation.screen.messaging

interface MessagingInteractionListener: ChatInteractionListener, MessageListInteractionListener {

    fun onBackClicked()
    fun onMenuClicked()

    fun onChatActionsDialogDismissed()

    fun onInputMessageChanged(value: String)
    fun onSendMessageClicked()
}

interface ChatInteractionListener {
    fun onDeleteChatClicked()
    fun onDeleteChatDialogDismissed()
    fun onConfirmDeleteChat()
}

interface MessageListInteractionListener {
    fun onMessageClicked(messageId: String)

    fun onFailedMessageClicked(messageId: String)

    fun onResendMessageClicked(messageId: String)
    fun onResendMessageDialogDismissed()
}