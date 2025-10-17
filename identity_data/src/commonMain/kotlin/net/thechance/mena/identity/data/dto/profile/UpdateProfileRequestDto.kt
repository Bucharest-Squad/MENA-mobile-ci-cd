package net.thechance.mena.identity.data.dto.profile

import kotlinx.serialization.SerialName

data class UpdateProfileRequestDto(
    @SerialName("firstName")
    val firstName: String,
    @SerialName("lastName")
    val lastName: String,
    @SerialName("profileImageUrl")
    val profileImageUrl: String,
    @SerialName("username")
    val username: String,
    @SerialName("birthDate")
    val birthDate: String,
    @SerialName("gender")
    val gender: Int,
    @SerialName("updateImage")
    val shouldUpdateImage: Boolean,
)