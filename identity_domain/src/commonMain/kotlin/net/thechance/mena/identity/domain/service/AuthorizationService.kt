package net.thechance.mena.identity.domain.service

import net.thechance.mena.identity.domain.repository.AuthenticationRepository

class AuthorizationService (private val authenticationRepository: AuthenticationRepository){

    suspend fun getAccessToken(){
        authenticationRepository.getAccessToken()
    }

    suspend fun refreshToken(){
        authenticationRepository.refreshAccessToken()
    }
}