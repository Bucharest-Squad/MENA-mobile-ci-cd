package net.thechance.mena.core_chat.presentation.screen.messaging

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import mena.core_chat_presentation.generated.resources.Res
import mena.core_chat_presentation.generated.resources.confirm_delete_chat_message
import mena.core_chat_presentation.generated.resources.delete
import mena.core_chat_presentation.generated.resources.delete_chat
import net.thechance.mena.core_chat.presentation.screen.messaging.components.ChatActionsDialog
import net.thechance.mena.core_chat.presentation.screen.messaging.components.ChatHeader
import net.thechance.mena.core_chat.presentation.screen.messaging.components.ChatInputBar
import net.thechance.mena.core_chat.presentation.screen.messaging.components.ResendMessageDialog
import net.thechance.mena.core_chat.presentation.screen.messaging.components.TextMessageItem
import net.thechance.mena.designsystem.presentation.component.dialog.Dialog
import net.thechance.mena.designsystem.presentation.component.scaffold.Scaffold
import net.thechance.mena.designsystem.presentation.component.text.Text
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.resources.stringResource
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
            dialog(state.isChatActionsDialogVisible) {
                ChatActionsDialog(
                    onDeleteChatClick = interactions::onDeleteChatClick,
                    onDismiss = interactions::onDismissChatActionsDialog
                )
            }

            dialog(state.isDeleteChatDialogVisible) {
                Dialog(
                    title = stringResource(Res.string.delete_chat),
                    message = stringResource(Res.string.confirm_delete_chat_message),
                    buttonText = stringResource(Res.string.delete),
                    onActionClick = interactions::onConfirmDeleteChat,
                    onDismiss = interactions::onDismissDeleteChatDialog
                )
            }

            dialog(state.isResendMessageDialogVisible) {
                ResendMessageDialog(
                    onDeleteMessageClick = interactions::onDeleteMessageClick,
                    onResendClick = interactions::onResendMessageClick,
                    onDismiss = interactions::onDismissResendMessageDialog
                )
            }
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Theme.spacing._12),
            reverseLayout = true,
            contentPadding = PaddingValues(top = Theme.spacing._4)
        ) {
            items(
                items = state.chatListItems,
                key = {
                    when (it) {
                        is ChatListItem.DateSeparator -> "header-${it.label}"
                        is ChatListItem.Message -> it.data.message.id
                    }
                }
            ) { item ->
                when (item) {
                    is ChatListItem.DateSeparator -> {
                        Text(
                            text = item.label,
                            style = Theme.typography.label.small,
                            color = Theme.colorScheme.shadeTertiary,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = Theme.spacing._8),
                            textAlign = TextAlign.Center
                        )
                    }

                    is ChatListItem.Message -> {
                        val markedMessage = item.data
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = if (markedMessage.message.isMine) Arrangement.End else Arrangement.Start
                        ) {
                            TextMessageItem(
                                message = markedMessage.message as TextMessageUiState, // temporal casting until more MessageTypes involved
                                chatAvatarUrl = if (markedMessage.isMarkedLastInSeries) state.chat.avatarUrl else null,
                                showMessageInfo = markedMessage.showMessageInfo,
                                isMarkedLastInSeries = markedMessage.isMarkedLastInSeries,
                                onClick = { interactions.onMessageClick(markedMessage.message.id) },
                                onFailClick = { interactions.onFailedMessageClick(markedMessage.message)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview()
private fun PreviewMessagingScreenDark() {
    MenaTheme {
        MessagingScreen()
    }
}