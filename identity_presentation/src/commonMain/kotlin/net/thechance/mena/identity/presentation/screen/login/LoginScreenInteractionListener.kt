package net.thechance.mena.identity.presentation.screen.login

import net.thechance.mena.identity.presentation.base.BaseInteractionListener
import net.thechance.mena.identity.presentation.countryPicker.menaCountries.MenaCountry

interface LoginScreenInteractionListener : BaseInteractionListener {

    fun onClickCountryPicker()
    fun onSelectCountryItem(country: MenaCountry)
    fun onClickConfirmButton()
    fun onDismissBottomSheet()
}