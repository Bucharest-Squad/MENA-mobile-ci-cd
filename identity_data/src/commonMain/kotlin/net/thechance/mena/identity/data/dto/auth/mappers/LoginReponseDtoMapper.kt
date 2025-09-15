package net.thechance.mena.identity.data.dto.auth.mappers

import net.thechance.mena.identity.data.dto.auth.LoginResponseDto
import net.thechance.mena.identity.domain.entity.AuthToken

fun LoginResponseDto.toDomain(): AuthToken{
    return AuthToken(
        accessToken = this.accessToken,
        refreshToken = this.refreshToken
    )
}