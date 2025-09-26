@file:OptIn(ExperimentalUuidApi::class)

package net.thechance.mena.core_chat.presentation.screen.messaging

import net.thechance.mena.core_chat.domain.entity.Chat
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class ChatUiState(
    val id: String = "",
    val name: String = "",
    val avatarUrl: String = ""
)

sealed interface ChatListItem {
    data class DateSeparator(val label: String): ChatListItem
    data class Message(val data: MarkedMessageUiState): ChatListItem
}

fun Chat.toUi() = ChatUiState(
    id = id.toString(),
    name = name,
    avatarUrl = imageUrl.orEmpty()
)

fun ChatUiState.toEntity() = Chat(
    id = Uuid.parse(id),
    imageUrl = avatarUrl,
    name = name
)