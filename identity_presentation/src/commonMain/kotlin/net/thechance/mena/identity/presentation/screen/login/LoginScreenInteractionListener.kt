package net.thechance.mena.identity.presentation.screen.login

import net.thechance.mena.identity.presentation.base.BaseInteractionListener
import net.thechance.mena.identity.presentation.countryPicker.menaCountries.MenaCountry

interface LoginScreenInteractionListener : BaseInteractionListener {

    fun onRegisterClicked()
    fun onForgotPasswordClicked()
    fun onLoginClicked()
    fun onPhoneCodeClicked()

    fun onPhoneCodeChanged(phoneCode: String)
    fun onPhoneChanged(phone: String)
    fun onPasswordChanged(password: String)

    fun onPasswordVisibilityToggled()
    fun clearErrorMessage()

    fun onClickCountryPicker()
    fun onSelectCountryItem(country: MenaCountry)
    fun onClickConfirmButton()
    fun onDismissBottomSheet()
}