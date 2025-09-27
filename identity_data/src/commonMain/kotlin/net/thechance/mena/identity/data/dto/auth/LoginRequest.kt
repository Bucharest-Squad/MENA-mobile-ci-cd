package net.thechance.mena.identity.data.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val phoneNumber: String,
    val password: String
)
