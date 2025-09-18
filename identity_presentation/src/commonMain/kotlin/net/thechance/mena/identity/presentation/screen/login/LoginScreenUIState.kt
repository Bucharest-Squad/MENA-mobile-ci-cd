package net.thechance.mena.identity.presentation.screen.login

import net.thechance.mena.identity.presentation.countryPicker.CountryPickerUIState

data class LoginScreenUIState(
    val showBottomSheet: Boolean = false,
    val countryPickerUIState: CountryPickerUIState = CountryPickerUIState()
)