package net.thechance.mena.identity.presentation.screen.login

import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.CoroutineScope
import net.thechance.mena.identity.presentation.base.BaseScreenModel
import net.thechance.mena.identity.domain.useCase.LoginUseCase
import net.thechance.mena.identity.presentation.countryPicker.menaCountries.MenaCountry
import net.thechance.mena.identity.presentation.countryPicker.selectByCountry
import net.thechance.mena.identity.presentation.mapper.mapErrorToMessage

class LoginScreenModel(
    val loginUseCase: LoginUseCase
) : BaseScreenModel<LoginScreenUIState, LoginScreenUIEffect>(LoginScreenUIState()),
    LoginScreenInteractionListener {
    override val viewModelScope: CoroutineScope
        get() = screenModelScope


    fun login() {
        updateState { copy(isLoading = true, errorMessage = null) }
        tryToExecute(
            function = {
                loginUseCase.login(
                    state.value.countryPickerUIState.currentCountry.callingCode,
                    state.value.phoneNumber,
                    state.value.password
                )
            },
            onSuccess = {
                updateState { copy(isLoading = false) }
                sendNewEffect(LoginScreenUIEffect.NavigateToHome)
            },
            onError = { errorState ->
                updateState {
                    copy(
                        isLoading = false,
                        errorMessage = mapErrorToMessage(errorState)
                    )
                }
            }
        )
    }

    override fun onRegisterClicked() {
        sendNewEffect(LoginScreenUIEffect.NavigateToRegister)
    }

    override fun onForgotPasswordClicked() {
        sendNewEffect(LoginScreenUIEffect.NavigateToForgotPassword)
    }

    override fun onLoginClicked() {
        login()
    }

    override fun onPhoneCodeClicked() {
        updateState { copy(showCountryBottomSheet = true) }
    }

    override fun onPhoneCodeChanged(phoneCode: String) {
    }

    override fun onPhoneChanged(phone: String) {
        updateState { copy(phoneNumber = phone) }
    }

    override fun onPasswordChanged(password: String) {
        updateState { copy(password = password) }
    }

    override fun onPasswordVisibilityToggled() {
        updateState { copy(isPasswordVisible = !isPasswordVisible) }
    }

    override fun clearErrorMessage() {
        updateState { copy(errorMessage = null) }
    }

    override fun onClickCountryPicker() {
        updateState {
            copy(
                showCountryBottomSheet = true
            )
        }
    }


    override fun onSelectCountryItem(country: MenaCountry) {
        updateState {
            copy(
                countryPickerUIState = countryPickerUIState.copy(
                    selectedCountry = country,
                    countries = countryPickerUIState.countries.selectByCountry(country),
                    isEnabled = countryPickerUIState.currentCountry != country
                )
            )
        }
    }

    override fun onClickConfirmButton() {
        updateState {
            copy(
                showCountryBottomSheet = false,
                countryPickerUIState = countryPickerUIState.copy(
                    currentCountry = countryPickerUIState.selectedCountry!!,
                    isEnabled = false
                )
            )
        }
    }

    override fun onDismissBottomSheet() {
        updateState {
            copy(
                showCountryBottomSheet = false,
                countryPickerUIState = countryPickerUIState.copy(
                    selectedCountry = countryPickerUIState.currentCountry
                )
            )
        }
    }
}