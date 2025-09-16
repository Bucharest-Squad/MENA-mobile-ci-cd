package net.thechance.mena.trends.presentation.screen.interestpick

import net.thechance.mena.trends.presentation.screen.interestpick.CategoryScreenUiState.CategoryUiState
import net.thechance.mena.trends.presentation.shared.util.Selectable

data class CategoryScreenUiState(
    val isLoading: Boolean = true,
    val categories: List<Selectable<CategoryUiState>> = emptyList(),
    val isSaveButtonLoading: Boolean = false
) {
    data class CategoryUiState(
        val id: Int? = null,
        val name: String = "",
        val emoji: String = "",
    )
}

fun CategoryScreenUiState.isSavingEnabled() = categories.any(Selectable<CategoryUiState>::isSelected)