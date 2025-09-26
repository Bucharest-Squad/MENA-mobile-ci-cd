package net.thechance.mena.identity.domain.repository

interface AuthenticationRepository {
    suspend fun login(countryCode: String, number: String, password: String)
    suspend fun refreshAccessToken(): String
    suspend fun getAccessToken(): String
    suspend fun resetPassword(token: String, newPassword: String)
}