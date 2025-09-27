package net.thechance.mena.identity.data.dto.auth

import kotlinx.serialization.Serializable

@Serializable
data class RefreshRequest(
    val refreshToken: String
)