package net.thechance.mena.trends.presentation.screen.interestpick

sealed interface CategoryUiEffect {
    data object NavigateBack : CategoryUiEffect
    data object NavigateToTrends : CategoryUiEffect
}