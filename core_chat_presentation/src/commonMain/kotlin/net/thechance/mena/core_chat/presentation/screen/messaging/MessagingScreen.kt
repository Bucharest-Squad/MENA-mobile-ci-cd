package net.thechance.mena.core_chat.presentation.screen.messaging

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.thechance.mena.core_chat.presentation.screen.messaging.components.ChatHeader
import net.thechance.mena.core_chat.presentation.screen.messaging.components.ChatInputBar
import net.thechance.mena.core_chat.presentation.screen.messaging.components.TextMessageItem
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun MessagingScreen() {
    MessagingScreenContent()

}

@Composable
fun MessagingScreenContent(
    state: MessagingScreenState = MessagingScreenState()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colorScheme.background.surface)
            .navigationBarsPadding()
    ) {

        ChatHeader(
            chatName = state.chat.name,
            onMenuClick = { },
            onBackClick = { },
            modifier = Modifier
                .fillMaxWidth()
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = Theme.spacing._12),
            reverseLayout = true,
            contentPadding = PaddingValues(
                top = Theme.spacing._4
            )
        ) {
            items(
                items = state.messages,
                key = { it.message.id },
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = if (it.message.isMine) {
                        Arrangement.End
                    } else {
                        Arrangement.Start
                    }
                ) {
                    TextMessageItem(
                        message = it.message as TextMessageUiState,
                        chatAvatarUrl = if (it.isMarkedLastInSeries) state.chat.avatarUrl else null,
                        showMessageInfo = it.isMarkedLastInSeries
                    )
                }
            }
        }


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
    }


}

@Composable
@Preview()
private fun PreviewMessagingScreenDark() {

    MenaTheme {
        MessagingScreen()
    }
}
