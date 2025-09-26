package net.thechance.mena.core_chat.presentation.screen.messaging

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import net.thechance.mena.core_chat.presentation.screen.messaging.components.ChatHeader
import net.thechance.mena.core_chat.presentation.screen.messaging.components.ChatInputBar
import net.thechance.mena.core_chat.presentation.screen.messaging.components.ChatList
import net.thechance.mena.core_chat.presentation.screen.messaging.components.messagingScreenOverlays
import net.thechance.mena.designsystem.presentation.component.scaffold.Scaffold
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MessagingScreen(
    viewModel: MessagingViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()
    MessagingScreenContent(
        state = state,
        interactions = viewModel
    )

}

@Composable
fun MessagingScreenContent(
    state: MessagingScreenState = MessagingScreenState(),
    interactions: MessagingInteractionListener
) {
    Scaffold(
        topBar = {
            ChatHeader(
                chatName = state.chat.name,
                onMenuClick = interactions::onMenuClick,
                onBackClick = interactions::onBackClick,
                modifier = Modifier
                    .fillMaxWidth()
            )
        },
        bottomBar = {
            ChatInputBar(
                userInput = state.inputMessage,
                onTextChange = interactions::onInputMessageChange,
                onSendButtonClick = interactions::onSendMessageClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Theme.colorScheme.background.surface)
            )
        },
        overlays = {
            messagingScreenOverlays(
                showChatActionsDialog = state.isChatActionsDialogVisible,
                showResendMessageDialog = state.isResendMessageDialogVisible,
                showDeleteChatDialog = state.isDeleteChatDialogVisible,
                onDeleteChatClick = interactions::onDeleteChatClick,
                onDismissChatActionsDialog = interactions::onDismissChatActionsDialog,
                onDismissDeleteChatDialog = interactions::onDismissDeleteChatDialog,
                onDismissResendMessageDialog = interactions::onDismissResendMessageDialog,
                onConfirmDeleteChatClick = interactions::onConfirmDeleteChat,
                onDeleteFailedMessageClick = interactions::onDeleteFailedMessageClick,
                onResendFailedMessageClick = interactions::onResendMessageClick,
            )
        }
    ) {
        ChatList(
            items = state.chatListItems,
            chat = state.chat,
            onMessageClick = interactions::onMessageClick,
            onFailedMessageClick = interactions::onFailedMessageClick,
        )
    }
}

@Composable
@Preview()
private fun PreviewMessagingScreenDark() {

    MenaTheme {
        MessagingScreen()
    }
}
