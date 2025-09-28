package net.thechance.mena.identity.presentation.screen.reset_password

sealed interface ResetPasswordScreenUIEffect {
    data object NavigateBackToLogin : ResetPasswordScreenUIEffect
}