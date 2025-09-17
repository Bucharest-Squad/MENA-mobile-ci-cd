package net.thechance.mena.identity.domain.repository

interface AuthenticationRepository {
    suspend fun login(countryCode: String, number: String, password: String)
    suspend fun isUserBlocked(countryCode: String, number: String): Boolean
    suspend fun getToken(): String
}