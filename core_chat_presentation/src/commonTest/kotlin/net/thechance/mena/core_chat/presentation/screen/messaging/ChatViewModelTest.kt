package net.thechance.mena.core_chat.presentation.screen.messaging

import assertk.assertThat
import assertk.assertions.doesNotContain
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import dev.mokkery.MockMode
import dev.mokkery.answering.returns
import dev.mokkery.answering.throws
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.LocalDateTime
import mena.core_chat_presentation.generated.resources.Res
import mena.core_chat_presentation.generated.resources.error
import mena.core_chat_presentation.generated.resources.error_cant_get_messages
import net.thechance.mena.core_chat.domain.entity.Chat
import net.thechance.mena.core_chat.domain.entity.Message
import net.thechance.mena.core_chat.domain.entity.MessageStatus
import net.thechance.mena.core_chat.domain.repository.ChatRepository
import net.thechance.mena.core_chat.presentation.components.SnackBarData
import net.thechance.mena.core_chat.presentation.navigation.ChatEffector
import net.thechance.mena.core_chat.presentation.screen.chat.ChatArgs
import net.thechance.mena.core_chat.presentation.screen.chat.ChatViewModel
import net.thechance.mena.core_chat.presentation.screen.chat.MessageStatusUiState
import net.thechance.mena.core_chat.presentation.screen.chat.TextMessageUiState
import net.thechance.mena.core_chat.presentation.screen.chat.toEntity
import net.thechance.mena.core_chat.presentation.screen.chat.toUi
import net.thechance.mena.core_chat.presentation.utils.UiText
import net.thechance.mena.core_chat.presentation.utils.now
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid
import kotlin.test.Test

@OptIn(ExperimentalUuidApi::class, ExperimentalCoroutinesApi::class)
class ChatViewModelTest {
    private val repository = mock<ChatRepository>()
    val chatId = Uuid.parse("11111111-1111-1111-1111-111111111111")
    val currentUserId = "bbbbbbbb-bbbb-bbbb-bbbb-bbbbbbbbbbbb"
    private val chatArgs = mock<ChatArgs>()
    private val effector = mock<ChatEffector>(MockMode.autofill)
    private lateinit var chatViewModel: ChatViewModel

    private val testDispatcher = StandardTestDispatcher()


    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        every { chatArgs.chatId } returns chatId.toString()
        chatViewModel = ChatViewModel(repository, chatArgs, effector, testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init should update chat ui state when its loaded the chat successfully`() {
        val chat = Chat(chatId, "https://image.com", "Noor")
        everySuspend { repository.getChatById(chatId) } returns chat

        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(chatViewModel.state.value.chat).isEqualTo(chat.toUi())
    }

    @Test
    fun `init should update chat list when its loaded messages successfully`() {
        everySuspend { repository.loadMessages(chatId) } returns messages

        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(chatViewModel.state.value.uiMessages).isEqualTo(messages.map {
            it.toUi(
                currentUserId
            )
        })
    }

    @Test
    fun `init should send snack bar effect when its loading the messages failed`() {
        everySuspend { repository.loadMessages(chatId) } throws Exception()

        testDispatcher.scheduler.advanceUntilIdle()

        verifySuspend {
            effector.showSnackBar(
                SnackBarData(
                    title = UiText.StringRes(Res.string.error),
                    message = UiText.StringRes(Res.string.error_cant_get_messages)
                )
            )
        }
    }

    @Test
    fun `init should update uiMessage and chatListItems when receive new message`() {
        every { repository.subscribeToMessages(chatId) } returns  flowOf(messages.first())

        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(chatViewModel.state.value.uiMessages).isEqualTo(listOf(messages.first().toUi(currentUserId)))
    }


    @Test
    fun `onBackClicked should send pop back stack effect when its call`() {
        chatViewModel.onBackClicked()

        testDispatcher.scheduler.advanceUntilIdle()

        verifySuspend { effector.popBackStack() }
    }

    @Test
    fun `onMenuClicked should set isChatActionsDialogVisible to true when its call`() {
        chatViewModel.onMenuClicked()

        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(chatViewModel.state.value.isChatActionsDialogVisible).isTrue()
    }

    @Test
    fun `onInputMessageChanged should update the inputMessage value with provided value when its call`() {
        val inputMessage = "Hi Noor"

        chatViewModel.onInputMessageChanged(inputMessage)
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(chatViewModel.state.value.inputMessage).isEqualTo(inputMessage)
    }

    @Test
    fun `onResendMessageDialogDismissed should set isResendMessageDialogVisible to false when its call`() {
        chatViewModel.onResendMessageDialogDismissed()

        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(chatViewModel.state.value.isResendMessageDialogVisible).isFalse()
    }

    @Test
    fun `onResendMessageDialogDismissed should set isChatActionsDialogVisible to false when its called`() {
        chatViewModel.onResendMessageDialogDismissed()

        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(chatViewModel.state.value.isResendMessageDialogVisible).isFalse()
    }


    @Test
    fun `onDeleteChatClick should set isDeleteChatDialogVisible to true when its called`() {
        chatViewModel.onDeleteChatClicked()

        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(chatViewModel.state.value.isDeleteChatDialogVisible).isTrue()
    }

    @Test
    fun `onDeleteChatDialogDismissed should set isDeleteChatDialogVisible to false when its called`() {
        chatViewModel.onDeleteChatDialogDismissed()

        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(chatViewModel.state.value.isDeleteChatDialogVisible).isFalse()
    }


    @Test
    fun `onSendMessageClicked should update current messages with sent state and reset the user input when its successfully sent `() {
        val inputMessage = "hi"
        chatViewModel.updateState {
            chatViewModel.state.value.copy(
                chat = it.chat.copy(id = chatId.toString()),
                inputMessage = inputMessage
            )
        }
        everySuspend { repository.sendMessage(any()) } returns Unit

        chatViewModel.onSendMessageClicked()
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(chatViewModel.state.value.uiMessages.first().chatId).isEqualTo(chatId.toString())
        assertThat(chatViewModel.state.value.uiMessages.first().senderId).isEqualTo(
            currentUserId
        )
        assertThat(chatViewModel.state.value.uiMessages.first().status).isEqualTo(
            MessageStatusUiState.SENT
        )
        assertThat(chatViewModel.state.value.inputMessage).isEmpty()
    }

    @Test
    fun `onSendMessageClicked should update current messages with failed state and reset the user input when its call`() {
        val inputMessage = "hi"
        chatViewModel.updateState {
            chatViewModel.state.value.copy(
                chat = it.chat.copy(id = chatId.toString()),
                inputMessage = inputMessage
            )
        }


        chatViewModel.onSendMessageClicked()
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(chatViewModel.state.value.uiMessages.first().chatId).isEqualTo(chatId.toString())
        assertThat(chatViewModel.state.value.uiMessages.first().senderId).isEqualTo(
            currentUserId
        )
        assertThat(chatViewModel.state.value.uiMessages.first().status).isEqualTo(
            MessageStatusUiState.FAILED
        )
        assertThat(chatViewModel.state.value.inputMessage).isEmpty()
    }

    @Test
    fun `onFailedMessageClick should make the resend message dialog visible and update current failed message its called`() {
        val failedMessage = TextMessageUiState(
            id = Uuid.random().toString(),
            senderId = currentUserId,
            chatId = chatId.toString(),
            sendTime = LocalDateTime.now(),
            status = MessageStatusUiState.SENDING,
            isMine = true,
            text = "hello,world"
        )

        chatViewModel.onFailedMessageClicked(failedMessage)
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(chatViewModel.state.value.isDeleteChatDialogVisible).isFalse()
        assertThat(chatViewModel.state.value.failedMessageToReSend).isEqualTo(failedMessage)

    }

    @Test
    fun `onDeleteFailedMessageClick should delete the clicked failed message when its call`() {
        chatViewModel.updateState { it.copy(
            failedMessageToReSend = messages.first().toUi(currentUserId),
            uiMessages = listOf(messages.first().toUi(currentUserId))
        ) }

        chatViewModel.onDeleteFailedMessageClicked()
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(chatViewModel.state.value.uiMessages).doesNotContain(messages.first().toUi(currentUserId))
    }

    @Test
    fun `onResendMessageClick should update the resend message state to sent when resend message success`() {
        val failedMessage = messages.first().toUi(currentUserId)
        chatViewModel.updateState { it.copy(
            failedMessageToReSend = failedMessage,
            uiMessages = listOf(messages.first().toUi(currentUserId))
        ) }
        everySuspend { repository.sendMessage(failedMessage.toEntity()) } returns Unit

        chatViewModel.onResendMessageClicked()

        assertThat(chatViewModel.state.value.uiMessages.first().status).isEqualTo(MessageStatusUiState.SENDING)
        testDispatcher.scheduler.advanceUntilIdle()
        assertThat(chatViewModel.state.value.uiMessages.first().status).isEqualTo(MessageStatusUiState.SENT)
    }

    @Test
    fun `onResendMessageClick should update the resend message state to failed when resend message failed`() {
        val failedMessage = messages.first().toUi(currentUserId)
        chatViewModel.updateState { it.copy(
            failedMessageToReSend = failedMessage,
            uiMessages = listOf(messages.first().toUi(currentUserId))
        ) }

        chatViewModel.onResendMessageClicked()

        assertThat(chatViewModel.state.value.uiMessages.first().status).isEqualTo(MessageStatusUiState.SENDING)
        testDispatcher.scheduler.advanceUntilIdle()
        assertThat(chatViewModel.state.value.uiMessages.first().status).isEqualTo(MessageStatusUiState.FAILED)
    }

    private val messages =
        listOf(
            Message(
                Uuid.random(),
                Uuid.parse(currentUserId),
                chatId,
                "Hello, World", LocalDateTime.now(),
                MessageStatus.SENT
            ),
            Message(
                Uuid.random(),
                Uuid.parse(currentUserId),
                chatId,
                "Hello, World2", LocalDateTime.now(),
                MessageStatus.SENT
            )
        )

}