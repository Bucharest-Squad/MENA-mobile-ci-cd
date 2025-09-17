package net.thechance.mena.identity.domain.repository

import net.thechance.mena.identity.domain.entity.AuthToken

interface AuthenticationRepository {
    suspend fun login(mobileNumber: String, password: String)
    suspend fun getAccessToken(): String
}

