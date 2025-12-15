package net.thechance.mena.identity.presentation.feature.authentication.register.otp

data class RegisterOtpUIState(
    val otpValue: String = "",
    val isVerifyEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val isResendEnabled: Boolean = true,
    val timer: String = "",
)