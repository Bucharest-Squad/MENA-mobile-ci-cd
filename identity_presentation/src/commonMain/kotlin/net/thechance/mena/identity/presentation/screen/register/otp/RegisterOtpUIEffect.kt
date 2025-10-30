package net.thechance.mena.identity.presentation.screen.register.otp

sealed class RegisterOtpUIEffect {
    data object NavigateToCreatePassword : RegisterOtpUIEffect()
    data object NavigateBack : RegisterOtpUIEffect()
}