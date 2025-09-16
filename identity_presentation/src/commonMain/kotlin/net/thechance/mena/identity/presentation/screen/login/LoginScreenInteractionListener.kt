package net.thechance.mena.identity.presentation.screen.login

import net.thechance.mena.identity.presentation.base.BaseInteractionListener

interface LoginScreenInteractionListener : BaseInteractionListener {

    fun onRegisterClicked()
    fun onForgotPasswordClicked()
    fun onLoginClicked()
    fun onPhoneCodeClicked()

    fun onPhoneCodeChanged(phoneCode: String)
    fun onPhoneChanged(phone: String)
    fun onPasswordChanged(password: String)

    fun onPasswordVisibilityToggled()

}