package net.thechance.mena.trends.presentation.screen.interestpick

import org.jetbrains.compose.resources.StringResource

data class InterestsUiState(
    val isLoading: Boolean = true,
    val categories: List<CategoryUiModel> = emptyList(),
    val errorMessage: StringResource? = null,
    val isSaving: Boolean = false
){
    data class CategoryUiModel(
        val id: Int,
        val name: String,
        val emoji: String,
        val isSelected: Boolean = false
    )
}

