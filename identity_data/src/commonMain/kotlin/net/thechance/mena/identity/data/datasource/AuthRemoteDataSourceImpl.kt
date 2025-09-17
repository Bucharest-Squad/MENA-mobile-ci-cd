package net.thechance.mena.identity.data.datasource

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import net.thechance.mena.identity.data.dto.auth.LoginRequestDto
import net.thechance.mena.identity.data.dto.auth.LoginResponseDto
import net.thechance.mena.identity.data.dto.auth.RefreshRequestDto

class AuthRemoteDataSourceImpl(
    private val client: HttpClient
) :AuthRemoteDataSource{
    override suspend fun login(loginRequest: LoginRequestDto): LoginResponseDto {
        return client.postJson(loginRequest, ApiConstants.LOGIN)
    }

    override suspend fun refreshToken(refreshRequest: RefreshRequestDto): LoginResponseDto {
        return client.postJson(refreshRequest, ApiConstants.REFRESH)
    }
}

private suspend inline fun <reified T, reified R> HttpClient.postJson(
    requestDto: T,
    path: String
): R {
    return this.post {
        url(path)
        contentType(ContentType.Application.Json)
        setBody(requestDto)
    }.body()
}
object ApiConstants {
    const val LOGIN = "identity/login"
    const val REFRESH = "identity/refresh"
}



