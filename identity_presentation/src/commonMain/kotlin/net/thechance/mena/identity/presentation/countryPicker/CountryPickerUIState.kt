package net.thechance.mena.identity.presentation.countryPicker

import net.thechance.mena.identity.presentation.countryPicker.menaCountries.MenaCountries

data class CountryPickerUIState(
    val selectedCountry: MenaCountries? = null,
    val isEnabled: Boolean = false,
    val countries: List<SelectableCountryItemUiState> = defaultCountries
) {
    private companion object {
        val defaultCountries = MenaCountries.entries.map { country ->
            SelectableCountryItemUiState(
                selectableCountry = Selectable(
                    item = country,
                    isSelected = false
                )
            )
        }
    }
}

data class SelectableCountryItemUiState(
    val selectableCountry: Selectable<MenaCountries> = Selectable(
        item = MenaCountries.EGYPT,
        isSelected = false
    )
)

data class Selectable<T>(
    val item: T,
    val isSelected: Boolean
)


