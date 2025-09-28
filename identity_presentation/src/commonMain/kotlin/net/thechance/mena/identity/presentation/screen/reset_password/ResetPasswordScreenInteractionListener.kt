package net.thechance.mena.identity.presentation.screen.reset_password

import net.thechance.mena.identity.presentation.base.BaseInteractionListener

interface ResetPasswordScreenInteractionListener : BaseInteractionListener {
    fun onNewPasswordChanged(password: String)
    fun onConfirmPasswordChanged(password: String)
    fun onNewPasswordVisibilityToggled()
    fun onConfirmPasswordVisibilityToggled()
    fun onResetPasswordClicked()
    fun onBackClicked()
}