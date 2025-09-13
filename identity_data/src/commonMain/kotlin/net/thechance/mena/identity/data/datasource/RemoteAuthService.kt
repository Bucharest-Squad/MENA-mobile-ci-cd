package net.thechance.mena.identity.data.datasource

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import net.thechance.mena.identity.data.datautils.ApiConstants
import net.thechance.mena.identity.data.datautils.safeWrapper
import net.thechance.mena.identity.data.dto.LoginRequestDto
import net.thechance.mena.identity.data.dto.LoginResponseDto
import net.thechance.mena.identity.data.dto.RefreshRequestDto

class RemoteAuthService(
    private val client: HttpClient
) {
    suspend fun login(loginRequest: LoginRequestDto): LoginResponseDto {
        return safeWrapper {
            client.getJson(loginRequest, ApiConstants.LOGIN)
        }
    }

    suspend fun refreshToken(refreshRequest: RefreshRequestDto): LoginResponseDto {
        return safeWrapper {
            client.getJson(refreshRequest, ApiConstants.REFRESH)
        }
    }
}

private suspend inline fun <reified T, reified R> HttpClient.getJson(
    requestDto: T,
    path: String
): R {
    return this.get {
        url(path)
        contentType(ContentType.Application.Json)
        setBody(requestDto)
    }.body()
}

