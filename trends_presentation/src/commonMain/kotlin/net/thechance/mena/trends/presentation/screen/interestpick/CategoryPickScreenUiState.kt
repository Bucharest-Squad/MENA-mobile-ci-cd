package net.thechance.mena.trends.presentation.screen.interestpick

import net.thechance.mena.trends.presentation.screen.interestpick.CategoryPickScreenUiState.CategoryUiState
import net.thechance.mena.trends.presentation.shared.util.Selectable
import org.jetbrains.compose.resources.StringResource

data class CategoryPickScreenUiState(
    val isLoading: Boolean = true,
    val categories: List<Selectable<CategoryUiState>> = emptyList(),
    val errorMessage: StringResource? = null,
    val isSaveButtonLoading: Boolean = false
) {
    data class CategoryUiState(
        val id: Int? = null,
        val name: String = "",
        val emoji: String = "",
    )
}

fun CategoryPickScreenUiState.isSavingEnabled() = categories.any(Selectable<CategoryUiState>::isSelected)