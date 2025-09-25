@file:OptIn(ExperimentalUuidApi::class)

package net.thechance.mena.core_chat.domain.entity

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class Chat(
    val id: Uuid,
    val imageUrl: String?,
    val name: String
)
