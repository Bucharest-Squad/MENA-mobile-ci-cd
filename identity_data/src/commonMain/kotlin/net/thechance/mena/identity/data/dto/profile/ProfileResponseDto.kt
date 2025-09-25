package net.thechance.mena.identity.data.dto.profile

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProfileResponseDto(
    @SerialName("firstName")
    val firstName: String,
    @SerialName("lastName")
    val lastName: String,
    @SerialName("profileImageUrl")
    val profileImageUrl: String,
    @SerialName("username")
    val username: String,
)
