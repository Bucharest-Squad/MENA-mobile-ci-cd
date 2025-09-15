package net.thechance.mena.trends.presentation.screen.interestpick

import net.thechance.mena.trends.domain.entity.Category
import net.thechance.mena.trends.presentation.screen.interestpick.CategoryPickScreenUiState.CategoryUiState
import net.thechance.mena.trends.presentation.shared.util.Selectable

internal fun Category.toUiState(): CategoryUiState {
    return CategoryUiState(
        id = id,
        name = name,
        emoji = emoji,
    )
}

fun List<Category>.toUiStates(): List<Selectable<CategoryUiState>> {
    return map { category ->
        Selectable(
            uiState = category.toUiState(),
            isSelected = false
        )
    }
}
