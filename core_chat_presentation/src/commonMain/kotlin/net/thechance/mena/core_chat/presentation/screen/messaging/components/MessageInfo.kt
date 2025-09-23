@file:OptIn(ExperimentalTime::class)

package net.thechance.mena.core_chat.presentation.screen.messaging.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import mena.core_chat_presentation.generated.resources.Res
import mena.core_chat_presentation.generated.resources.ic_close_circle
import mena.core_chat_presentation.generated.resources.ic_message_read
import mena.core_chat_presentation.generated.resources.ic_message_sent
import net.thechance.mena.core_chat.presentation.screen.messaging.MessageStatus
import net.thechance.mena.core_chat.presentation.utils.formatAsTime
import net.thechance.mena.designsystem.presentation.component.icon.Icon
import net.thechance.mena.designsystem.presentation.component.indicator.DotsProgressIndicator
import net.thechance.mena.designsystem.presentation.component.text.Text
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.time.Clock
import kotlin.time.ExperimentalTime


@Composable
fun MessageInfo(
    messageTime: LocalDateTime,
    messageStatus: MessageStatus,
    modifier: Modifier = Modifier
) {
    val messageInfoColor = if (messageStatus == MessageStatus.FAILED)
        Theme.colorScheme.error
    else
        Theme.colorScheme.shadeTertiary

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(Theme.spacing._4),
    ) {
        Text(
            text = messageTime.formatAsTime(),
            style = Theme.typography.label.extraSmall,
            color = messageInfoColor
        )

        when (messageStatus) {
            MessageStatus.SENDING -> {
                DotsProgressIndicator(
                    numberOfDots = 3,
                    colors = listOf(
                        Theme.colorScheme.stroke,
                        Theme.colorScheme.shadeTertiary,
                        Theme.colorScheme.primary.primary
                    )
                )
            }

            MessageStatus.SENT -> {
                Icon(
                    painter = painterResource(Res.drawable.ic_message_sent),
                    contentDescription = "Sent",
                    tint = messageInfoColor,
                    modifier = Modifier.size(16.dp)
                )
            }

            MessageStatus.READ -> {
                Icon(
                    painter = painterResource(Res.drawable.ic_message_read),
                    contentDescription = "Read",
                    tint = messageInfoColor,
                    modifier = Modifier.size(16.dp)
                )
            }

            MessageStatus.FAILED -> {
                Icon(
                    painter = painterResource(Res.drawable.ic_close_circle),
                    contentDescription = "Failed",
                    tint = messageInfoColor,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}


@Composable
@Preview()
private fun PreviewReadMessageInfo() {
    MenaTheme {
        Box(
            modifier = Modifier
                .background(Theme.colorScheme.background.surface)
                .padding(12.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            MessageInfo(
                modifier = Modifier,
                messageTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                messageStatus = MessageStatus.READ
            )
        }
    }
}

@Composable
@Preview()
private fun PreviewSentMessageInfo() {
    MenaTheme {
        Box(
            modifier = Modifier
                .background(Theme.colorScheme.background.surface)
                .padding(12.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            MessageInfo(
                modifier = Modifier,
                messageTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                messageStatus = MessageStatus.SENT
            )
        }
    }
}

@Composable
@Preview()
private fun PreviewFailedMessageInfo() {
    MenaTheme {
        Box(
            modifier = Modifier
                .background(Theme.colorScheme.background.surface)
                .padding(12.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            MessageInfo(
                modifier = Modifier,
                messageTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                messageStatus = MessageStatus.FAILED
            )
        }
    }
}

@Composable
@Preview()
private fun PreviewSendingMessageInfo() {
    MenaTheme {
        Box(
            modifier = Modifier
                .background(Theme.colorScheme.background.surface)
                .padding(12.dp),
            contentAlignment = Alignment.BottomStart
        ) {
            MessageInfo(
                modifier = Modifier,
                messageTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
                messageStatus = MessageStatus.SENDING
            )
        }
    }
}
