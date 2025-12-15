package net.thechance.mena.identity.presentation.feature.authentication.resetPassword.setNewPassword

import net.thechance.mena.identity.presentation.core.base.BaseInteractionListener

interface SetNewPasswordScreenInteractionListener : BaseInteractionListener {
    fun onChangeNewPassword(password: String)
    fun onChangeConfirmPassword(password: String)
    fun onToggleNewPasswordVisibility()
    fun onToggleConfirmPasswordVisibility()
    fun onClickResetPassword()
    fun onClickBack()
    fun onClickOk()
}