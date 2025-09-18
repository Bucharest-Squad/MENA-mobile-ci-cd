package net.thechance.mena.identity.presentation.countryPicker

import net.thechance.mena.identity.presentation.countryPicker.menaCountries.MenaCountries

fun List<SelectableCountryItemUiState>.selectByCountry(country: MenaCountries): List<SelectableCountryItemUiState> {
    return this.map { selectableCountry ->
        selectableCountry.copy(
            selectableCountry = Selectable(
                item = selectableCountry.selectableCountry.item,
                isSelected = selectableCountry.selectableCountry.item == country
            )
        )
    }
}
