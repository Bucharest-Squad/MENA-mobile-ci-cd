package net.thechance.mena.identity.domain.repository

import net.thechance.mena.identity.domain.entity.PhoneNumber

interface ResetPasswordRepository {
    suspend fun resetPassword(
        newPassword: String,
        confirmPassword: String,
        phoneNumber: PhoneNumber
    )
}