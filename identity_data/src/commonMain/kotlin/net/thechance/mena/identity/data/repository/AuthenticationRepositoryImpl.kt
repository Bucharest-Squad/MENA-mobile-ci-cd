package net.thechance.mena.identity.data.repository

import net.thechance.mena.identity.data.datasource.remoteDataSource.UserRemoteDataSource
import net.thechance.mena.identity.data.datasource.localDataSource.UserLocalDataSource
import net.thechance.mena.identity.data.dto.auth.LoginRequestDto
import net.thechance.mena.identity.data.dto.auth.LoginResponseDto
import net.thechance.mena.identity.data.dto.auth.RefreshRequestDto
import net.thechance.mena.identity.data.utils.safeWrapper
import net.thechance.mena.identity.domain.repository.AuthenticationRepository

class AuthenticationRepositoryImpl(
    private val remoteAuthService: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource,
) : AuthenticationRepository {
    override suspend fun login(countryCode: String, number: String, password: String) {
        return safeWrapper {
            val mobileNumber = countryCode + number
            val loginResponse = remoteAuthService.login(LoginRequestDto(mobileNumber, password))
            saveAuthTokens(loginResponse)
        }
    }

    override suspend fun refreshAccessToken(): String {
        val refreshResponse = safeWrapper {
            remoteAuthService.refreshToken(RefreshRequestDto(userLocalDataSource.getRefreshToken()))
        }
        saveAuthTokens(refreshResponse)
        return userLocalDataSource.getAccessToken()
    }

    override suspend fun getAccessToken(): String {
        return userLocalDataSource.getAccessToken()
    }

    private fun saveAuthTokens(loginResponseDto: LoginResponseDto) {
        userLocalDataSource.saveAccessToken(loginResponseDto.accessToken)
        userLocalDataSource.saveRefreshToken(loginResponseDto.refreshToken)
    }
}