package net.thechance.mena.core_chat.data.contacts.source.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ContactDto (
    @SerialName("name")
    val name: String? = null,
    @SerialName("phoneNumber")
    val phoneNumber: String? = null,
    @SerialName("isMenaMember")
    val isMenaMember: Boolean? = null,
    @SerialName("imageUrl")
    val imageUrl: String? = null,
)