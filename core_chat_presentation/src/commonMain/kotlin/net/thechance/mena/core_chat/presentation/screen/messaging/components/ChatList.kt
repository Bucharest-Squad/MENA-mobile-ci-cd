package net.thechance.mena.core_chat.presentation.screen.messaging.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.thechance.mena.core_chat.presentation.screen.messaging.ChatListItem
import net.thechance.mena.core_chat.presentation.screen.messaging.ChatUiState
import net.thechance.mena.core_chat.presentation.screen.messaging.MessageUiState
import net.thechance.mena.designsystem.presentation.theme.theme.Theme

@Composable
fun ChatList(
    items: List<ChatListItem>,
    chat: ChatUiState,
    onMessageClick: (String) -> Unit,
    onFailedMessageClick: (MessageUiState) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Theme.spacing._12),
        reverseLayout = true,
        contentPadding = PaddingValues(top = Theme.spacing._4)
    ) {
        items(
            items = items,
            key = {
                when (it) {
                    is ChatListItem.DateSeparator -> "header-${it.label}"
                    is ChatListItem.Message -> it.data.message.id
                }
            }
        ) { item ->
            ChatListItem(
                item = item,
                chat = chat,
                onMessageClick = onMessageClick,
                onFailedMessageClick = onFailedMessageClick
            )
        }
    }
}
