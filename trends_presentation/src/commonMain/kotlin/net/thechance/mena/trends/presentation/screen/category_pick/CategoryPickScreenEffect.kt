package net.thechance.mena.trends.presentation.screen.category_pick

sealed interface CategoryPickScreenEffect {
    data object NavigateBack : CategoryPickScreenEffect
    data object NavigateToTrends : CategoryPickScreenEffect
}