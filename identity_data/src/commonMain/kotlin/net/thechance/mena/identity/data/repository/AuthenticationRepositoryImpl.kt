package net.thechance.mena.identity.data.repository

import com.russhwolf.settings.Settings
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import net.thechance.mena.identity.data.dto.auth.AuthenticationResponse
import net.thechance.mena.identity.data.dto.auth.LoginRequestDto
import net.thechance.mena.identity.data.dto.auth.RefreshRequestDto
import net.thechance.mena.identity.data.dataSource.local.setting.accessToken
import net.thechance.mena.identity.data.mapper.toDomain
import net.thechance.mena.identity.data.utils.postJson
import net.thechance.mena.identity.data.dataSource.local.setting.refreshToken
import net.thechance.mena.identity.data.utils.safeWrapper
import net.thechance.mena.identity.domain.entity.PhoneNumber
import net.thechance.mena.identity.domain.model.AuthenticationTokens
import net.thechance.mena.identity.domain.repository.AuthenticationRepository
import kotlin.concurrent.Volatile

class AuthenticationRepositoryImpl(
    private val client: HttpClient,
    private val settings: Settings
) : AuthenticationRepository {

    private val observableToken: MutableStateFlow<String> = MutableStateFlow(settings.accessToken)
    
    @Volatile
    private var isTemporaryTokenMode: Boolean = false

    override suspend fun login(phoneNumber: PhoneNumber, password: String) = safeWrapper {
        val response: AuthenticationResponse = client.postJson(
            LoginRequestDto(phoneNumber.getFormattedPhoneNumber(), password),
            LOGIN_ENDPOINT
        )
        saveAuthTokens(response.toDomain())
    }

    override suspend fun refreshAccessToken(): String {
        val response: AuthenticationResponse = safeWrapper {
            client.postJson(
                RefreshRequestDto(settings.refreshToken),
                REFRESH_ENDPOINT
            )
        }
        saveTokens(response.toDomain(), shouldEmit = !isTemporaryTokenMode)
        return settings.accessToken
    }

    override suspend fun getAccessToken(): String = settings.accessToken

    override suspend fun saveAuthTokensWithoutEmit(authTokens: AuthenticationTokens) {
        isTemporaryTokenMode = true
        saveTokensToSettings(authTokens)
    }

    override suspend fun saveAuthTokensAndEmit(authTokens: AuthenticationTokens) {
        saveAuthTokens(authTokens)
    }

    override suspend fun clearAuthTokens() {
        saveTokensToSettings(AuthenticationTokens(accessToken = "", refreshToken = ""))
        isTemporaryTokenMode = false
    }

    override fun observeTokenChange(): StateFlow<String> = observableToken

    private suspend fun saveAuthTokens(authTokens: AuthenticationTokens) {
        saveTokens(authTokens, shouldEmit = !isTemporaryTokenMode)
    }

    private suspend fun saveTokens(authTokens: AuthenticationTokens, shouldEmit: Boolean) {
        saveTokensToSettings(authTokens)
        if (shouldEmit) {
            observableToken.emit(authTokens.accessToken)
        }
    }

    private fun saveTokensToSettings(authTokens: AuthenticationTokens) {
        settings.accessToken = authTokens.accessToken
        settings.refreshToken = authTokens.refreshToken
    }

    companion object {
        const val LOGIN_ENDPOINT = "identity/authentication/login"
        const val REFRESH_ENDPOINT = "identity/authentication/refresh"
    }
}