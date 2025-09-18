package net.thechance.mena.identity.presentation.screen.login

import net.thechance.mena.identity.presentation.base.BaseInteractionListener
import net.thechance.mena.identity.presentation.countryPicker.menaCountries.MenaCountries

interface LoginScreenInteractionListener : BaseInteractionListener {

    fun onClickCountryPicker()
    fun onSelectCountryItem(country: MenaCountries)
    fun onClickConfirmButton()
    fun onDismissBottomSheet()
}