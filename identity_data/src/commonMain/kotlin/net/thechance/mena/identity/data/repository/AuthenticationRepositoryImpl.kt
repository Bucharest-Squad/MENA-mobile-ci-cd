package net.thechance.mena.identity.data.repository

import com.russhwolf.settings.Settings
import io.ktor.client.HttpClient
import net.thechance.mena.identity.data.dto.auth.LoginRequestDto
import net.thechance.mena.identity.data.dto.auth.AuthenticationResponse
import net.thechance.mena.identity.data.dto.auth.RefreshRequestDto
import net.thechance.mena.identity.data.utils.postJson
import net.thechance.mena.identity.data.utils.safeWrapper
import net.thechance.mena.identity.domain.repository.AuthenticationRepository

class AuthenticationRepositoryImpl(
    private val client: HttpClient,
    private val settings: Settings
) : AuthenticationRepository {
    override suspend fun login(countryCode: String, number: String, password: String) {
        return safeWrapper {
            val mobileNumber = countryCode + number
            val loginResponse: AuthenticationResponse = client.postJson(LoginRequestDto(mobileNumber, password), LOGIN)
            saveAuthTokens(loginResponse)
        }
    }

    override suspend fun refreshAccessToken(): String {
        val refreshResponse: AuthenticationResponse = safeWrapper {
            client.postJson(RefreshRequestDto(settings.getString(REFRESH_TOKEN, "")), REFRESH)
        }
        saveAuthTokens(refreshResponse)
        return settings.getString(ACCESS_TOKEN, "")
    }

    override suspend fun getAccessToken(): String {
        return settings.getString(ACCESS_TOKEN, "")
    }

    private fun saveAuthTokens(loginResponseDto: AuthenticationResponse) {
        settings.putString(ACCESS_TOKEN, loginResponseDto.accessToken)
        settings.putString(REFRESH_TOKEN, loginResponseDto.refreshToken)
    }

    companion object {
        const val LOGIN = "identity/login"
        const val REFRESH = "identity/refresh"
        const val ACCESS_TOKEN = "access_token"
        const val REFRESH_TOKEN = "refresh_token"
    }
}