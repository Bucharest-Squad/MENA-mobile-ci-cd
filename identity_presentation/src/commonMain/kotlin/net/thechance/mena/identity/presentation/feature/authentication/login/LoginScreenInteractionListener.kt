package net.thechance.mena.identity.presentation.feature.authentication.login

import net.thechance.mena.identity.presentation.base.BaseInteractionListener
import net.thechance.mena.identity.presentation.components.countryPicker.menaCountries.MenaCountry

interface LoginScreenInteractionListener : BaseInteractionListener {

    fun onRegisterClicked()
    fun onForgotPasswordClicked()
    fun onLoginClicked()
    fun onPhoneCodeClicked()
    fun onPhoneChanged(phone: String)
    fun onPasswordChanged(password: String)
    fun onPasswordVisibilityToggled()
    fun onConfirmCountryItem(country: MenaCountry)
    fun onDismissBottomSheet()
}