package net.thechance.mena.identity.presentation.feature.authentication.login

import org.jetbrains.compose.resources.StringResource

sealed interface LoginScreenUIEffect {
    data object NavigateToRegister : LoginScreenUIEffect
    data object NavigateToForgotPassword : LoginScreenUIEffect
    data object NavigateToHome : LoginScreenUIEffect

    data class ShowSnackBarError(val errorStringResource: StringResource) : LoginScreenUIEffect
}