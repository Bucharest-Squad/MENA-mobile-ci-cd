package net.thechance.mena.identity.presentation.feature.authentication.resetPassword.setNewPassword

import org.jetbrains.compose.resources.StringResource

sealed interface SetNewPasswordScreenUIEffect {
    data object NavigateBackToLogin : SetNewPasswordScreenUIEffect
    data class ShowSnackBarError(val errorStringResource: StringResource) :
        SetNewPasswordScreenUIEffect

}