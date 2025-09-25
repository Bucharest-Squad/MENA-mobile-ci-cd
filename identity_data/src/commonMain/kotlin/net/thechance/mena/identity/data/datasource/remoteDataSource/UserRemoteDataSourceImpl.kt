package net.thechance.mena.identity.data.datasource.remoteDataSource

import io.ktor.client.HttpClient
import net.thechance.mena.identity.data.datasource.utils.getJson
import net.thechance.mena.identity.data.datasource.utils.postJson
import net.thechance.mena.identity.data.dto.auth.LoginRequestDto
import net.thechance.mena.identity.data.dto.auth.LoginResponseDto
import net.thechance.mena.identity.data.dto.auth.RefreshRequestDto
import net.thechance.mena.identity.data.dto.profile.ProfileResponseDto

class UserRemoteDataSourceImpl(
    private val client: HttpClient
) : UserRemoteDataSource {
    override suspend fun login(loginRequest: LoginRequestDto): LoginResponseDto {
        return client.postJson(loginRequest, LOGIN)
    }

    override suspend fun refreshToken(refreshRequest: RefreshRequestDto): LoginResponseDto {
        return client.postJson(refreshRequest, REFRESH)
    }

    override suspend fun getUserInfo(): ProfileResponseDto {
        return client.getJson(PROFILE)
    }


    companion object {
        const val LOGIN = "identity/login"
        const val REFRESH = "identity/refresh"
        const val PROFILE = "profile"

    }
}