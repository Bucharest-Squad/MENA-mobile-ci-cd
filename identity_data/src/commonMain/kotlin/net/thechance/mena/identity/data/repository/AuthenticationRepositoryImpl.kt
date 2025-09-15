package net.thechance.mena.identity.data.repository

import net.thechance.mena.identity.data.datasource.RemoteAuthService
import net.thechance.mena.identity.data.datasource.TokenManager
import net.thechance.mena.identity.data.datautils.safeWrapper
import net.thechance.mena.identity.data.dto.auth.LoginRequestDto
import net.thechance.mena.identity.data.dto.auth.LoginResponseDto
import net.thechance.mena.identity.data.dto.auth.RefreshRequestDto
import net.thechance.mena.identity.data.dto.auth.mappers.toDomain
import net.thechance.mena.identity.domain.entity.AuthToken
import net.thechance.mena.identity.domain.repository.AuthenticationRepository

class AuthenticationRepositoryImpl(
    private val remoteAuthService: RemoteAuthService,
    private val tokenManager: TokenManager,
) : AuthenticationRepository {
    override suspend fun login(mobileNumber: String, password: String) {
        return safeWrapper {
            val response = remoteAuthService.login(LoginRequestDto(mobileNumber, password))
            tokenManager.saveAccessToken(response.accessToken)
            tokenManager.saveRefreshToken(response.refreshToken)
        }
    }

    override suspend fun getAccessToken(): AuthToken {
        val response= safeWrapper {
            remoteAuthService.refreshToken(RefreshRequestDto(tokenManager.getRefreshToken()))
        }
        tokenManager.saveAccessToken(response.accessToken)
        tokenManager.saveRefreshToken(response.refreshToken)
        return LoginResponseDto(
            tokenManager.getAccessToken(),
            tokenManager.getRefreshToken()
        ).toDomain()
    }
}