package net.thechance.mena.identity.domain.useCase

import net.thechance.mena.identity.domain.exception.InvalidPasswordException
import net.thechance.mena.identity.domain.repository.AuthenticationRepository
import net.thechance.mena.identity.domain.useCase.validation.mobileNumber.PasswordValidator

open class ResetPasswordUseCase(
    private val authenticationRepository: AuthenticationRepository,
    private val passwordValidator: PasswordValidator
) {

    open suspend fun execute(token: String, newPassword: String) {
        if (!isPasswordValid(newPassword)) throw InvalidPasswordException()

        authenticationRepository.resetPassword(
            token = token,
            newPassword = newPassword
        )
    }

    open fun isPasswordValid(password: String): Boolean {
        return passwordValidator.isValid(password)
    }

    fun resetPassword(string: kotlin.String) {}

    private companion object {
         const val PASSWORD_MIN_LENGTH = 8
     }
}