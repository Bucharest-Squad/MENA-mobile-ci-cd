package net.thechance.mena.identity.presentation.feature.authentication.resetPassword.phoneEntry

import net.thechance.mena.identity.presentation.components.countryPicker.menaCountries.MenaCountry

data class ResetPasswordPhoneEntryScreenUIState(
    val phoneNumber: String = "",
    val showCountryBottomSheet: Boolean = false,
    val currentCountry: MenaCountry = MenaCountry.IRAQ,
    val isContinueEnabled: Boolean = false,
    val isLoading: Boolean = false,
)