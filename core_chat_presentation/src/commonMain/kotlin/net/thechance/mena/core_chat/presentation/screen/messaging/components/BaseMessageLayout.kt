@file:OptIn(ExperimentalTime::class)

package net.thechance.mena.core_chat.presentation.screen.messaging.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import net.thechance.mena.core_chat.presentation.screen.messaging.MessageStatus
import net.thechance.mena.core_chat.presentation.screen.messaging.MessageUiState
import net.thechance.mena.core_chat.presentation.screen.messaging.TextMessageUiState
import net.thechance.mena.designsystem.presentation.component.text.Text
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Composable
fun BaseMessageLayout(
    message: MessageUiState,
    showSenderAvatar: Boolean,
    showMessageInfo: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val messageBackground = if (message.isMine)
        Theme.colorScheme.background.surfaceLow
    else
        Theme.colorScheme.brand.brandVariant

    val messagePadding = if (message.isMine)
        Theme.spacing._24
    else if (showSenderAvatar)
        Theme.spacing._8
    else
        Theme.spacing._32

    val messageShape = if (message.isMine)
        RoundedCornerShape(
            topStart = Theme.radius.md,
            topEnd = Theme.radius.md,
            bottomStart = Theme.radius.md,
            bottomEnd = Theme.radius.xxs
        )
    else
        RoundedCornerShape(
            topStart = Theme.radius.md,
            topEnd = Theme.radius.md,
            bottomStart = Theme.radius.xxs,
            bottomEnd = Theme.radius.md
        )

    val messageTimeAlignment = if (message.isMine)
        Alignment.Start
    else
        Alignment.End

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(Theme.spacing._2),
    ) {
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            if (!message.isMine && showSenderAvatar) {
                AsyncImage(
                    modifier = Modifier
                        .size(Theme.spacing._24)
                        .clip(CircleShape),
                    model = message.senderAvatarUrl,
                    contentScale = ContentScale.Crop,
                    contentDescription = "Sender Avatar",
                )
            }

            Box(
                modifier = Modifier
                    .padding(start = messagePadding)
                    .clip(messageShape)
                    .background(
                        color = messageBackground,
                        shape = messageShape
                    )
                    .padding(
                        horizontal = Theme.spacing._8,
                        vertical = Theme.spacing._4
                    )
            ) {
                content()
            }

        }

        if (showMessageInfo) {
            MessageInfo(
                messageTime = message.time,
                messageStatus = message.status,
                modifier = Modifier
                    .align(messageTimeAlignment)
                    .padding(start = messagePadding)
            )
        }
    }
}

@Composable
@Preview()
private fun PreviewBaseMessageLayout() {
    MenaTheme {
        Box(
            modifier = Modifier
                .background(Theme.colorScheme.background.surface)
        ) {
            BaseMessageLayout(
                message = TextMessageUiState(
                    "0",
                    "1",
                    "https://avatars.githubusercontent.com/u/75501067?v=4",
                    "2",
                    Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                    MessageStatus.READ,
                    false,

                    ""
                ),
                showMessageInfo = true,
                showSenderAvatar = true,
                modifier = Modifier
            ) {
                Text(
                    text = "Hello,\nBilal",
                    style = Theme.typography.body.small
                )
            }
        }

    }
}

