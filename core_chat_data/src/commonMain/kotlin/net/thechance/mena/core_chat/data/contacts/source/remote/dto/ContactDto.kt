package net.thechance.mena.core_chat.data.contacts.source.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContactDto (
    @SerialName("name")
    val name: String? = null,
    @SerialName("phone")
    val phone: String? = null,
    @SerialName("isMenaUser")
    val isMenaUser: Boolean? = null,
    @SerialName("imageUrl")
    val imageUrl: String? = null,
)