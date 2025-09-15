package net.thechance.mena.trends.presentation.screen.interestpick

sealed interface CategoryPickUiEffect {
    data object NavigateBack : CategoryPickUiEffect
    data object NavigateToSave : CategoryPickUiEffect
}