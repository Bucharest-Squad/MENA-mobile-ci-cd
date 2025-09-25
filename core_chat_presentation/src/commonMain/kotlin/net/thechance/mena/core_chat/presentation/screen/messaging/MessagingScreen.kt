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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import net.thechance.mena.core_chat.presentation.screen.messaging.components.ChatHeader
import net.thechance.mena.core_chat.presentation.screen.messaging.components.ChatInputBar
import net.thechance.mena.core_chat.presentation.screen.messaging.components.ResendMessageDialog
import net.thechance.mena.core_chat.presentation.screen.messaging.components.TextMessageItem
import net.thechance.mena.designsystem.presentation.component.scaffold.Scaffold
import net.thechance.mena.designsystem.presentation.component.text.Text
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun MessagingScreen() {
    var state by remember { mutableStateOf(MessagingScreenState()) }
    MessagingScreenContent(state) { messageId -> // temp until handling the view model
        state = state.copy(
            chatListItems = state.chatListItems.map { item ->
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

@Composable
fun MessagingScreenContent(
    state: MessagingScreenState = MessagingScreenState(),
    onMessageClick: (String) -> Unit = {}
) {
    var showChatActionsDialog by remember { mutableStateOf(false) }
    var showResendMessageDialog by remember { mutableStateOf(false) }
    var showDeleteChatDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            ChatHeader(
                chatName = state.chat.name,
                onMenuClick = { showChatActionsDialog = true },
                onBackClick = { },
                modifier = Modifier
                    .fillMaxWidth()
            )
        },
        bottomBar = {
            ChatInputBar(
                userInput = state.inputMessage,
                onTextChange = { },
                onAttachButtonClick = { },
                onSendButtonClick = { },
                onVoiceRecordClick = { },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Theme.colorScheme.background.surface)
            )
        },
        overlays = {
            dialog(showChatActionsDialog) {
                // todo
            }

            dialog(showDeleteChatDialog) {
                // todo
            }

            dialog(showResendMessageDialog) {
                ResendMessageDialog(
                    onDeleteMessageClick = { },
                    onResendClick = { },
                    onDismiss = { showResendMessageDialog = false }
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
                                showMessageInfo = markedMessage.isMarkedLastInSeries || markedMessage.showMessageInfo,
                                onClick = {
                                    onMessageClick(markedMessage.message.id)
                                },
                                onFailClick = {
                                    showResendMessageDialog = true
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
