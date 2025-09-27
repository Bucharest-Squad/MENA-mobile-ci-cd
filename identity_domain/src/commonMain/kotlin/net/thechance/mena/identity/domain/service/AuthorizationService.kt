package net.thechance.mena.identity.domain.service

import net.thechance.mena.identity.domain.repository.AuthenticationRepository

class AuthorizationService (private val authenticationRepository: AuthenticationRepository){

    suspend fun getAccessToken(): String {
        return authenticationRepository.getAccessToken()
    }

    suspend fun refreshToken(): String {
        return authenticationRepository.refreshAccessToken()
    }
}