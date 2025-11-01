package net.thechance.mena.admin_panel.data.service

import net.thechance.mena.admin_panel.domain.repository.AdminAuthenticationRepository
import org.koin.core.annotation.Single

@Single
class AuthenticationService(
    private val adminAuthenticationRepository:
    AdminAuthenticationRepository
) {
    suspend fun getAccessToken(): String {
        return adminAuthenticationRepository.getAccessToken()
    }

    suspend fun refreshToken(): String {
        return adminAuthenticationRepository.refreshAccessToken()
    }
}