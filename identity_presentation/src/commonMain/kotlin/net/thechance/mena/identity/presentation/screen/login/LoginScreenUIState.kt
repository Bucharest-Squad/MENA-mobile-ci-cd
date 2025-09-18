package net.thechance.mena.identity.presentation.screen.login

data class LoginScreenUIState(
    val phoneNumber:String = "",
    val password:String = "",
    val phoneCode: String = "+964",
    val isPasswordVisible:Boolean = false,
    val showCountryBottomSheet:Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage:String? = null,
    val countryPickerUIState: CountryPickerUIState = CountryPickerUIState()
){
    val isLoginEnabled: Boolean
        get() = phoneNumber.isNotBlank() && password.isNotBlank()
}