package net.thechance.mena.trends.presentation.screen.interestpick

import net.thechance.mena.trends.presentation.shared.model.CategoryUiState
import net.thechance.mena.trends.presentation.shared.util.Selectable

data class CategoryScreenUiState(
    val isLoading: Boolean = true,
    val categories: List<Selectable<CategoryUiState>> = emptyList(),
    val isSaveButtonLoading: Boolean = false
)

fun CategoryScreenUiState.isSavingEnabled() = categories.any(Selectable<CategoryUiState>::isSelected)