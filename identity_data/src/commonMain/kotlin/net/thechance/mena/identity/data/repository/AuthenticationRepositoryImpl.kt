package net.thechance.mena.identity.data.repository

import net.thechance.mena.identity.data.datasource.AuthRemoteDataSource
import net.thechance.mena.identity.data.datasource.LocalDataSource
import net.thechance.mena.identity.data.dto.auth.LoginRequestDto
import net.thechance.mena.identity.data.dto.auth.LoginResponseDto
import net.thechance.mena.identity.data.dto.auth.RefreshRequestDto
import net.thechance.mena.identity.data.utils.safeWrapper
import net.thechance.mena.identity.domain.repository.AuthenticationRepository

class AuthenticationRepositoryImpl(
    private val RemoteAuthService: AuthRemoteDataSource,
    private val localDataSource: LocalDataSource,
) : AuthenticationRepository {
    override suspend fun login(mobileNumber: String, password: String) {
        return safeWrapper {
            val loginResponse = RemoteAuthService.login(LoginRequestDto(mobileNumber, password))
            saveAuthTokens(loginResponse)
        }
    }

    override suspend fun getAccessToken(): String {
        val refreshResponse = safeWrapper {
            RemoteAuthService.refreshToken(RefreshRequestDto(localDataSource.getRefreshToken()))
        }
        saveAuthTokens(refreshResponse)
        return localDataSource.getAccessToken()
    }

    private fun saveAuthTokens(loginResponseDto: LoginResponseDto) {
        localDataSource.saveAccessToken(loginResponseDto.accessToken)
        localDataSource.saveRefreshToken(loginResponseDto.refreshToken)
    }
}