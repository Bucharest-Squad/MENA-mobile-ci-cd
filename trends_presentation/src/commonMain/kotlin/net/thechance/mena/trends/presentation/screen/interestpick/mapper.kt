package net.thechance.mena.trends.presentation.screen.interestpick

import net.thechance.mena.trends.domain.entity.Category
import net.thechance.mena.trends.presentation.screen.interestpick.InterestsUiState.CategoryUiModel

internal fun Category.toUiModel(): CategoryUiModel {
    return CategoryUiModel(
        id = id,
        name = name,
        emoji = emoji,
        isSelected = false
    )
}