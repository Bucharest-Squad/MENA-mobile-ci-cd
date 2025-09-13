package net.thechance.mena.identity.domain.repository

interface AuthenticationRepository {
    suspend fun login(mobileNumber: String, password: String)
    suspend fun getAccessToken(): String
}

