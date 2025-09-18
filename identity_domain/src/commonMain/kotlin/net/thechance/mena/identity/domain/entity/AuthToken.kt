package net.thechance.mena.identity.domain.entity

data class AuthToken(
    val accessToken: String,
    val refreshToken: String
)