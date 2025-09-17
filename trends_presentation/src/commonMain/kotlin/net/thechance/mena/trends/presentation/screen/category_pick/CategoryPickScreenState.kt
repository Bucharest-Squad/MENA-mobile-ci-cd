package net.thechance.mena.trends.presentation.screen.category_pick

import net.thechance.mena.trends.presentation.shared.model.CategoryUiState
import net.thechance.mena.trends.presentation.shared.model.Selectable

data class CategoryPickScreenState(
    val isLoading: Boolean = true,
    val categories: List<Selectable<CategoryUiState>> = emptyList(),
    val isSaveButtonLoading: Boolean = false
)

fun CategoryPickScreenState.isSavingEnabled() = categories.any(Selectable<CategoryUiState>::isSelected)