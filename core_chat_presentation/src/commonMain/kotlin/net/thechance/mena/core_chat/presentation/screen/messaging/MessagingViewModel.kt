@file:OptIn(ExperimentalUuidApi::class)

package net.thechance.mena.core_chat.presentation.screen.messaging

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import kotlinx.datetime.LocalDateTime
import mena.core_chat_presentation.generated.resources.Res
import mena.core_chat_presentation.generated.resources.error
import mena.core_chat_presentation.generated.resources.error_cant_get_messages
import mena.core_chat_presentation.generated.resources.error_cant_subscribe_to_new_messages
import net.thechance.mena.core_chat.domain.entity.Message
import net.thechance.mena.core_chat.domain.repository.MessageRepository
import net.thechance.mena.core_chat.presentation.components.SnackBarData
import net.thechance.mena.core_chat.presentation.navigation.ChatEffector
import net.thechance.mena.core_chat.presentation.navigation.MessagingRoute
import net.thechance.mena.core_chat.presentation.shared.BaseViewModel
import net.thechance.mena.core_chat.presentation.utils.UiText
import net.thechance.mena.core_chat.presentation.utils.now
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid


class MessagingViewModel(
    private val repository: MessageRepository,
    savedStateHandle: SavedStateHandle,
    effector: ChatEffector,
) : BaseViewModel<MessagingScreenState>(MessagingScreenState(), effector),
    MessagingInteractionListener {

    init {
        val chatId = savedStateHandle.toRoute<MessagingRoute>().chatId

        getChat(chatId)
        loadChatHistory(chatId)
        subscribeToNewMessages(chatId)
    }


    override fun onBackClick() {
        popBackStack()
    }

    override fun onMenuClick() {
        updateState { it.copy(isChatActionsDialogVisible = true) }
    }

    override fun onInputMessageChange(value: String) {
        updateState { it.copy(inputMessage = value) }
    }

    override fun onSendMessageClick() {
        val chatId = state.value.chat.id
        val senderId = state.value.userId
        val text = state.value.inputMessage.trim()
        if (text.isEmpty()) return

        // create temporary UI message to send
        val now = LocalDateTime.now()
        val uiMessage = TextMessageUiState(
            id = Uuid.random().toString(),
            senderId = senderId,
            chatId = chatId,
            sendTime = now,
            status = MessageStatus.SENDING,
            isMine = true,
            text = text
        )

        // add to in-memory list to show immediately
        updateState { s ->
            val newMessages = s.uiMessages + uiMessage
            s.copy(
                uiMessages = newMessages,
                inputMessage = "",
                chatListItems = buildListItems(newMessages)
            )
        }

        tryToExecute(
            onSuccess = {
                updateState { s ->
                    val updated = s.uiMessages.map {
                        if (it.id == uiMessage.id) (it as TextMessageUiState).copy(status = MessageStatus.SENT) else it
                    }
                    s.copy(uiMessages = updated, chatListItems = buildListItems(updated))
                }
            },
            onError = {
                updateState { s ->
                    val updated = s.uiMessages.map {
                        if (it.id == uiMessage.id) (it as TextMessageUiState).copy(status = MessageStatus.FAILED) else it
                    }
                    s.copy(uiMessages = updated, chatListItems = buildListItems(updated))
                }
            },
            execute = { repository.sendMessage(uiMessage.toEntity()) }
        )
    }

    override fun onMessageClick(messageId: String) {
        updateState {
            it.copy(
                chatListItems = it.chatListItems.map { item ->
                    if (item is ChatListItem.Message && item.data.message.id == messageId) {
                        item.copy(
                            data = item.data.copy(
                                showMessageInfo = !item.data.showMessageInfo
                            )
                        )
                    } else {
                        item
                    }
                }
            )
        }
    }


    override fun onFailedMessageClick(message: MessageUiState) {
        updateState {
            it.copy(
                isResendMessageDialogVisible = true,
                failedMessageToReSend = message
            )
        }
    }

    override fun onDeleteFailedMessageClick() {
        state.value.failedMessageToReSend?.let { failedMessage ->
            updateState { s ->
                val updatedMessages = s.uiMessages.filterNot { it.id == failedMessage.id }
                s.copy(
                    uiMessages = updatedMessages,
                    chatListItems = buildListItems(updatedMessages),
                    failedMessageToReSend = null,
                    isResendMessageDialogVisible = false
                )
            }
        }
    }


    override fun onResendMessageClick() {
        state.value.failedMessageToReSend?.let { message ->
            // mark as SENDING
            updateState { s ->
                val updated = s.uiMessages.map {
                    if (it.id == message.id) (it as TextMessageUiState).copy(status = MessageStatus.SENDING) else it
                }
                s.copy(uiMessages = updated, chatListItems = buildListItems(updated))
            }

            tryToExecute(
                onSuccess = { onResendMessageSuccess(message) },
                onError = { onResendMessageError(message) },
                execute = { repository.sendMessage((message as TextMessageUiState).toEntity()) } // temp casting
            )
        }
    }

    fun onResendMessageSuccess(message: MessageUiState) {
        updateState { s ->
            val updated = s.uiMessages.map {
                if (it.id == message.id) (it as TextMessageUiState).copy(status = MessageStatus.SENT) else it
            }
            s.copy(uiMessages = updated, chatListItems = buildListItems(updated))
        }
    }

    fun onResendMessageError(message: MessageUiState) {
        updateState { s ->
            val updated = s.uiMessages.map {
                if (it.id == message.id) (it as TextMessageUiState).copy(status = MessageStatus.FAILED) else it
            }
            s.copy(uiMessages = updated, chatListItems = buildListItems(updated))
        }
    }

    override fun onDismissResendMessageDialog() {
        updateState { it.copy(isResendMessageDialogVisible = false) }
    }

    override fun onDismissChatActionsDialog() {
        updateState { it.copy(isChatActionsDialogVisible = false) }
    }

    override fun onDeleteChatClick() {
        updateState { it.copy(isDeleteChatDialogVisible = true) }
    }

    override fun onDismissDeleteChatDialog() {
        updateState { it.copy(isDeleteChatDialogVisible = false) }
    }

    private fun getChat(chatId: String) {
        // todo get chat from repository
        // temp chat
        val chat = ChatUiState(
            id = chatId,
            name = "Noor"
        )
        updateState { s -> s.copy(chat = chat) }
    }

    private fun loadChatHistory(chatId: String) {
        tryToExecute(
            execute = {
                repository.loadMessages(Uuid.parse(chatId))
            },
            onSuccess = ::onLoadChatHistorySuccess,
            onError = ::onLoadChatHistoryError
        )
    }

    private fun onLoadChatHistorySuccess(messages: List<Message>) {
        val uiMessages = messages.map { it.toUi(state.value.userId) }
        updateState { s ->
            s.copy(
                uiMessages = uiMessages,
                chatListItems = buildListItems(uiMessages)
            )
        }
    }

    private fun onLoadChatHistoryError(throwable: Throwable) {
        showSnackBar(
            SnackBarData(
                title = UiText.StringRes(Res.string.error),
                message = UiText.StringRes(Res.string.error_cant_get_messages)
            )
        )
    }

    private fun subscribeToNewMessages(chatId: String) {
        // todo should be flow
        tryToExecute(
            execute = {
                repository.subscribeToMessages(Uuid.parse(chatId))
            },
            onSuccess = ::onSubscribeToNewMessagesSuccess,
            onError = ::onSubscribeToNewMessagesError
        )
    }

    private fun onSubscribeToNewMessagesSuccess(newBatch: List<Message>) {
        if (newBatch.isNotEmpty()) {
            val incomingUi = newBatch.map { it.toUi(state.value.userId) }
            updateState { s ->
                val merged = (s.uiMessages + incomingUi).distinctBy { it.id }
                s.copy(uiMessages = merged, chatListItems = buildListItems(merged))
            }
        }
    }

    private fun onSubscribeToNewMessagesError(throwable: Throwable) {
        showSnackBar(
            snackBarData = SnackBarData(
                title = UiText.StringRes(Res.string.error),
                message = UiText.StringRes(Res.string.error_cant_subscribe_to_new_messages),
            )
        )
    }


    private fun buildListItems(uiMessages: List<MessageUiState>): List<ChatListItem> {
        val marked = uiMessages.markLastInSeries()
        return marked.withDateSeparators()
    }


    override fun onConfirmDeleteChat() {
        // TODO: actual delete next sprint
    }
}
