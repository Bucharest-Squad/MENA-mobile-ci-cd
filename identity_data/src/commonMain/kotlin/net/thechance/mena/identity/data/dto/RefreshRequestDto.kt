package net.thechance.mena.identity.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class RefreshRequestDto(val refreshToken: String)