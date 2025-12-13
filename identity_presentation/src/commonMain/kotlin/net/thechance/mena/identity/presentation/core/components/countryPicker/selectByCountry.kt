package net.thechance.mena.identity.presentation.core.components.countryPicker

import net.thechance.mena.identity.presentation.components.countryPicker.Selectable
import net.thechance.mena.identity.presentation.components.countryPicker.SelectableCountryItemUiState
import net.thechance.mena.identity.presentation.components.countryPicker.menaCountries.MenaCountry

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
