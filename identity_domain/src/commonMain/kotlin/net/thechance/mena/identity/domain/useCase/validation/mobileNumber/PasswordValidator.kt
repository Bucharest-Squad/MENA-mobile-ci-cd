package net.thechance.mena.identity.domain.useCase.validation.mobileNumber

class PasswordValidator {
    fun isValid(password: String): Boolean {
        return password.length >= 8 &&
                password.any { it.isDigit() } &&
                password.any { it.isUpperCase() }
    }

    fun validate(password: String): String? {
        if (password.length < 8) return "Password must be at least 8 characters."
        if (!password.any { it.isDigit() }) return "Password must contain a number."
        if (!password.any { it.isUpperCase() }) return "Password must contain an uppercase letter."
        return null
    }
}