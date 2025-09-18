package net.thechance.mena.identity.presentation.countryPicker

import net.thechance.mena.identity.presentation.countryPicker.menaCountries.MenaCountry

fun List<SelectableCountryItemUiState>.selectByCountry(country: MenaCountry): List<SelectableCountryItemUiState> {
    return this.map { selectableCountry ->
        selectableCountry.copy(
            selectableCountry = Selectable(
                item = selectableCountry.selectableCountry.item,
                isSelected = selectableCountry.selectableCountry.item == country
            )
        )
    }
}
