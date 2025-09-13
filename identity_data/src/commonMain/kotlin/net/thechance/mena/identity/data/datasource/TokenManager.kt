package net.thechance.mena.identity.data.datasource

expect class TokenManager {
    suspend fun saveAccessToken(accessToken: String)
    suspend fun getAccessToken(): String
    suspend fun saveRefreshToken(refreshToken: String)
    suspend fun getRefreshToken(): String
    suspend fun clearTokens()
}