package net.thechance.mena.identity.domain.useCase

import net.thechance.mena.identity.domain.repository.AuthenticationRepository

class GetAuthenticationTokenUseCase(
    private val authenticationRepository: AuthenticationRepository
) {
    suspend operator fun invoke() = authenticationRepository.getToken()
}