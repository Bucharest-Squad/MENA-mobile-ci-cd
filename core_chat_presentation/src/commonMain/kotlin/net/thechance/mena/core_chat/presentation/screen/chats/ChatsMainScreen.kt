package net.thechance.mena.core_chat.presentation.screen.chats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mena.core_chat_presentation.generated.resources.Res
import mena.core_chat_presentation.generated.resources.chats
import mena.core_chat_presentation.generated.resources.ic_warning
import mena.core_chat_presentation.generated.resources.mena
import net.thechance.mena.core_chat.presentation.screen.chats.components.ChatCard
import net.thechance.mena.designsystem.presentation.component.appBar.AppBar
import net.thechance.mena.designsystem.presentation.component.icon.Icon
import net.thechance.mena.designsystem.presentation.component.text.Text
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
@Composable
fun ChatsMainScreen(
    chatsUiState: List<ChatsUiState> = emptyList(),
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .background(
                    color = Theme.colorScheme.background.surface
                )
        ) {
            AppBar(
                title = stringResource(Res.string.mena),
                trailingContent = {
                    Text(
                        text = "134",
                        color = Theme.colorScheme.shadeSecondary,
                        style = Theme.typography.label.small,
                        modifier = Modifier.padding(vertical = Theme.spacing._4),
                    )
                    Icon(
                        painter = painterResource(Res.drawable.ic_warning),
                        contentDescription = null,
                    )
                }

            )
            Text(
                text = stringResource(Res.string.chats),
                color = Theme.colorScheme.shadePrimary,
                style = Theme.typography.title.small,
                modifier = Modifier.fillMaxWidth().padding(horizontal = Theme.spacing._16)
            )

            LazyColumn(
                modifier = Modifier.padding(horizontal = Theme.spacing._16)
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(vertical = Theme.spacing._12),
                verticalArrangement = Arrangement.spacedBy(Theme.spacing._16)
            ) {
                items(chatsUiState) { chatUiState ->
                    ChatCard(
                        name = chatUiState.name,
                        lastMessage = chatUiState.lastMessage,
                        isMenaUser = chatUiState.isMenaUser,
                        messageStatus = chatUiState.messageStatus,
                        messageUiState = chatUiState.messageUiState,
                        balance = chatUiState.balance,
                        onClick = {},
                        chatAvatarUrl = ""
                    )
                }
            }
        }
        Box(
            modifier = Modifier.padding(Theme.spacing._16)
                .size(56.dp)
                .background(
                    color = Theme.colorScheme.primary.primary,
                    shape = RoundedCornerShape(Theme.radius.md)
                )
                .align(Alignment.BottomEnd),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_warning),
                contentDescription = null,
                tint = Theme.colorScheme.primary.onPrimary
            )
        }
    }
}

@Composable
@Preview
private fun ChatsMainScreenPreview() {
    MenaTheme {
        ChatsMainScreen()
    }
}