package net.thechance.mena.identity.domain.useCase.validation.mobileNumber

interface MobileNumberValidator {
    fun isValid(countryCode: String, number: String): Boolean
}