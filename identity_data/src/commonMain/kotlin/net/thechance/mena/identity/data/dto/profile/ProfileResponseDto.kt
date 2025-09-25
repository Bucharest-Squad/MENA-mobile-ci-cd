package net.thechance.mena.identity.data.dto.profile

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponseDto(
    @SerialName("first_name")
    val firstName: String,
    @SerialName("last_name")
    val lastName: String,
    @SerialName("image_url")
    val imageUrl: String,
    @SerialName("username")
    val username: String,
)
