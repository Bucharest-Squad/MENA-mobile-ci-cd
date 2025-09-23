@file:OptIn(ExperimentalTime::class)

package net.thechance.mena.core_chat.presentation.screen.messaging.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import net.thechance.mena.core_chat.presentation.screen.messaging.MessageStatus
import net.thechance.mena.core_chat.presentation.screen.messaging.TextMessageUiState
import net.thechance.mena.designsystem.presentation.component.text.Text
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Composable
fun TextMessageItem(
    message: TextMessageUiState,
    showSenderAvatar: Boolean,
    showMessageInfo: Boolean,
    modifier: Modifier = Modifier
) {
    BaseMessageLayout(
        message = message,
        showMessageInfo = showMessageInfo,
        showSenderAvatar = showSenderAvatar,
        modifier = modifier
    ) {
        Text(
            text = message.text,
            style = Theme.typography.body.small
        )
    }
}

@Composable
@Preview()
private fun PreviewTextMessageItem() {
    MenaTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Theme.colorScheme.background.surface)
                .padding(12.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            TextMessageItem(
                modifier = Modifier,
                message = TextMessageUiState(
                    "0",
                    "1",
                    "https://avatars.githubusercontent.com/u/75501067?v=4",
                    "2",
                    Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                    MessageStatus.READ,
                    false,
                    "Hello Bilal"
                ),
                showSenderAvatar = true,
                showMessageInfo = true,
            )


        }
    }
}