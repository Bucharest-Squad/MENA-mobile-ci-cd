package net.thechance.mena.identity.presentation.countryPicker

import net.thechance.mena.identity.presentation.countryPicker.menaCountries.MenaCountry

data class CountryPickerUIState(
    val selectedCountry: MenaCountry? = null,
    val isEnabled: Boolean = false,
    val countries: List<SelectableCountryItemUiState> = defaultCountries
) {
    private companion object {
        val defaultCountries = MenaCountry.entries.map { country ->
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
    val selectableCountry: Selectable<MenaCountry> = Selectable(
        item = MenaCountry.EGYPT,
        isSelected = false
    )
)

data class Selectable<T>(
    val item: T,
    val isSelected: Boolean
)


