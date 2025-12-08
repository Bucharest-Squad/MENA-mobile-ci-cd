package net.thechance.mena.identity.presentation.feature.authentication.resetPassword.otp

data class ResetPasswordOtpScreenUIState (
    val otpValue: String = "",
    val isVerifyEnabled: Boolean = false,
    val isLoading: Boolean = false,
    val isResendEnabled: Boolean = true,
    val timer: String = "",
)