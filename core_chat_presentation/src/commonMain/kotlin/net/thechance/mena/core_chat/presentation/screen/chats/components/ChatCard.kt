package net.thechance.mena.core_chat.presentation.screen.chats.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import kotlinx.datetime.LocalDateTime
import mena.core_chat_presentation.generated.resources.Res
import mena.core_chat_presentation.generated.resources.ic_message_read
import mena.core_chat_presentation.generated.resources.ic_message_sent
import mena.core_chat_presentation.generated.resources.ic_profile_placeholder
import net.thechance.mena.core_chat.domain.entity.MessageStatus
import net.thechance.mena.core_chat.presentation.screen.chat.MessageStatusUiState
import net.thechance.mena.core_chat.presentation.screen.chat.TextMessageUiState
import net.thechance.mena.core_chat.presentation.utils.formatAsTime
import net.thechance.mena.core_chat.presentation.utils.now
import net.thechance.mena.designsystem.presentation.component.icon.Icon
import net.thechance.mena.designsystem.presentation.component.text.Text
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.uuid.ExperimentalUuidApi

@Composable
fun ChatCard(
    name: String,
    lastMessage: String,
    isMenaUser: Boolean,
    balance: Double,
    messageStatus: MessageStatus,
    messageUiState: TextMessageUiState,
    onClick: () -> Unit,
    chatAvatarUrl: String? = null,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        if (
            isMenaUser
        ) {
            Box(
                modifier = Modifier.padding(end = Theme.spacing._8)
                    .clip(CircleShape)
                    .background(
                        color = Theme.colorScheme.background.surfaceLow,
                        shape = CircleShape
                    )
                    .size(48.dp)
                    .padding(10.dp),
                contentAlignment = Alignment.Center
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = chatAvatarUrl,
                    placeholder = painterResource(Res.drawable.ic_profile_placeholder),
                    error = painterResource(Res.drawable.ic_profile_placeholder),
                    contentScale = ContentScale.Crop,
                    contentDescription = "Contact photo",
                )

            }
        }

        Column(
            modifier = Modifier.padding(vertical = Theme.spacing._4).weight(1f).padding(end = Theme.spacing._4),
            verticalArrangement = Arrangement.spacedBy(Theme.spacing._2)
        ) {
            Text(
                text = name,
                style = Theme.typography.label.medium,
                color = Theme.colorScheme.shadePrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = lastMessage,
                style = Theme.typography.label.small,
                color = Theme.colorScheme.shadeTertiary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Column(
            modifier = Modifier.padding(vertical = Theme.spacing._4),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(Theme.spacing._2, Alignment.Top) ) {
            Text(
                text = messageUiState.sendTime.formatAsTime(),
                style = Theme.typography.label.small,
                color = Theme.colorScheme.shadeSecondary
            )
            if (messageUiState.isMine) {
                if (messageStatus == MessageStatus.SENT) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_message_sent),
                        contentDescription = null,
                        tint = Theme.colorScheme.shadeTertiary
                    )
                } else if (messageStatus == MessageStatus.READ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_message_read),
                        contentDescription = null,
                        tint = Theme.colorScheme.shadeTertiary
                    )
                }
            } else {
                if (messageStatus == MessageStatus.SENT) {
                    Box(
                        modifier = Modifier
                            .clip(CircleShape)
                            .background(
                                color = Theme.colorScheme.brand.brand,
                                shape = CircleShape
                            )
                            .size(20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "$balance",
                            style = Theme.typography.label.small,
                            color = Theme.colorScheme.primary.onPrimary
                        )
                    }
                }
            }
        }
    }

}

@OptIn(ExperimentalUuidApi::class)
@Composable
@Preview
private fun ChatCardPreview() {
    MenaTheme {
        ChatCard(
            name = "Mona Ayman",
            lastMessage = "hi my name is mona",
            isMenaUser = true,
            messageStatus = MessageStatus.READ,
            messageUiState = TextMessageUiState(
                sendTime = LocalDateTime.now(),
                status = MessageStatusUiState.READ,
                isMine = false,
                text = ""
            ),
            onClick = {},
            chatAvatarUrl = "",
            balance = 100.0
        )
    }
}