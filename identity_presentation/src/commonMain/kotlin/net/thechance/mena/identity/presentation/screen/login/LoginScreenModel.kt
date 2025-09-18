package net.thechance.mena.identity.presentation.screen.login

import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.CoroutineScope
import net.thechance.mena.identity.presentation.base.BaseScreenModel
import net.thechance.mena.identity.presentation.countryPicker.menaCountries.MenaCountries
import net.thechance.mena.identity.presentation.countryPicker.selectByCountry

class LoginScreenModel :
    BaseScreenModel<LoginScreenUIState, LoginScreenUIEffect>(LoginScreenUIState()),
    LoginScreenInteractionListener {
    override val viewModelScope: CoroutineScope
        get() = screenModelScope

    override fun onClickCountryPicker() {
        updateState {
            it.copy(
                showBottomSheet = true
            )
        }
    }


    override fun onSelectCountryItem(country: MenaCountries) {
        updateState {
            it.copy(
                countryPickerUIState = it.countryPickerUIState.copy(
                    selectedCountry = country,
                    countries = it.countryPickerUIState.countries.selectByCountry(country),
                    isEnabled = true
                )
            )
        }
    }

    override fun onClickConfirmButton() {
        updateState {
            it.copy(
                showBottomSheet = false,
                countryPickerUIState = it.countryPickerUIState.copy(
                    isEnabled = false
                )
            )
        }
    }

    override fun onDismissBottomSheet() {
        updateState {
            it.copy(
                showBottomSheet = false,
                countryPickerUIState = it.countryPickerUIState.copy(
                    selectedCountry = null
                )
            )
        }
    }
}