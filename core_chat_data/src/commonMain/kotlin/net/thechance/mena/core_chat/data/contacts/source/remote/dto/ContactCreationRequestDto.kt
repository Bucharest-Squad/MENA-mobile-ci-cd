package net.thechance.mena.core_chat.data.contacts.source.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ContactCreationRequestDto(
    val name: String,
    val phone: String
)