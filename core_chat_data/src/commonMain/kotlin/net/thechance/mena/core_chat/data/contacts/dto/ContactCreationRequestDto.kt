package net.thechance.mena.core_chat.data.contacts.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContactCreationRequestDto(
    @SerialName("name")
    val name: String,
    @SerialName("phoneNumber")
    val phone: String
)