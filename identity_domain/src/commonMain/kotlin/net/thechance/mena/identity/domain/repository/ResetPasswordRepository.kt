package net.thechance.mena.identity.domain.repository

interface ResetPasswordRepository {
    suspend fun resetPassword(
        newPassword: String,
        confirmPassword: String,
        phoneNumber: String
    )
}