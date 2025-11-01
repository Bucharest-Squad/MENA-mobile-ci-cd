package net.thechance.mena.admin_panel.data.service

import net.thechance.mena.admin_panel.domain.repository.authentication.AdminAuthenticationRepository
import org.koin.core.annotation.Single

@Single
class AuthenticationService(
    private val adminAuthenticationRepository:
    AdminAuthenticationRepository
) {
    suspend fun getAccessToken() = adminAuthenticationRepository.getAccessToken()

    suspend fun refreshToken() = adminAuthenticationRepository.refreshAccessToken()
}