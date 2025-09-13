package net.thechance.mena.identity.domain.useCase

import net.thechance.mena.identity.domain.exception.InvalidMobileNumberException
import net.thechance.mena.identity.domain.exception.InvalidPasswordException
import net.thechance.mena.identity.domain.exception.UserIsBlockedException
import net.thechance.mena.identity.domain.repository.AuthenticationRepository
import net.thechance.mena.identity.domain.useCase.validation.mobileNumber.MobileNumberValidator

class LoginUseCase(
    private val authenticationRepository: AuthenticationRepository,
    private val mobileNumberValidator: MobileNumberValidator
) {
    suspend fun login(countryCode: String, number: String, password: String) {
        if (isPasswordValid(password)) {
            throw InvalidPasswordException()
        }
        if (isMobileNumberValid(countryCode, number)) {
            throw InvalidMobileNumberException(number)
        }
        if (isUserBlocked(countryCode, number)) {
            throw UserIsBlockedException("${countryCode}${number}")
        }
        authenticationRepository.login(
            countryCode = countryCode,
            number = number,
            password = password
        )
    }

    fun isMobileNumberValid(countryCode: String, number: String): Boolean {
        return mobileNumberValidator.isValid(countryCode = countryCode, number = number)
    }

    fun isPasswordValid(password: String) = password.length >= 8

    private suspend fun isUserBlocked(countryCode: String, number: String): Boolean {
        return authenticationRepository.isUserBlocked(countryCode = countryCode, number = number)
    }
}