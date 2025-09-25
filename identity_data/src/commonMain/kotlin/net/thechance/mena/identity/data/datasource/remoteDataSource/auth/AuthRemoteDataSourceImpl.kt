package net.thechance.mena.identity.data.datasource.remoteDataSource.auth

import io.ktor.client.HttpClient
import net.thechance.mena.identity.data.datasource.utils.postJson
import net.thechance.mena.identity.data.dto.auth.LoginRequestDto
import net.thechance.mena.identity.data.dto.auth.LoginResponseDto
import net.thechance.mena.identity.data.dto.auth.RefreshRequestDto

class AuthRemoteDataSourceImpl(
    private val client: HttpClient
) : AuthRemoteDataSource {
    override suspend fun login(loginRequest: LoginRequestDto): LoginResponseDto {
        return client.postJson(loginRequest, LOGIN)
    }

    override suspend fun refreshToken(refreshRequest: RefreshRequestDto): LoginResponseDto {
        return client.postJson(refreshRequest, REFRESH)
    }

    companion object {
        const val LOGIN = "identity/login"
        const val REFRESH = "identity/refresh"
    }
}