package net.thechance.mena.identity.presentation.screen.login

import net.thechance.mena.identity.presentation.countryPicker.CountryPickerUIState

data class LoginScreenUIState(
    val phoneNumber:String = "",
    val password:String = "",
    val isPasswordVisible:Boolean = false,
    val showCountryBottomSheet:Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage:String? = null,
    val countryPickerUIState: CountryPickerUIState = CountryPickerUIState()
){
    val isLoginEnabled: Boolean
        get() = phoneNumber.isNotBlank() && password.isNotBlank() && password.length >= PASSWORD_MIN_LENGTH

    private companion object {
        const val PASSWORD_MIN_LENGTH = 8
    }
}