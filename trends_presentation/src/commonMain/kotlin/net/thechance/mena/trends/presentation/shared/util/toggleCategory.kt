package net.thechance.mena.trends.presentation.shared.util

import net.thechance.mena.trends.presentation.shared.model.CategoryUiState
import net.thechance.mena.trends.presentation.shared.model.Selectable

fun List<Selectable<CategoryUiState>>.toggleCategory(id: Int) =
    map { if (it.value.id == id) it.copy(isSelected = !it.isSelected) else it }