package net.thechance.mena.trends.presentation.screen.managemytrendscreen

sealed interface ManageTrendsUiEffect {
    object NavigateBack : ManageTrendsUiEffect
    data class NavigateToTrend(val reelId: Int) : ManageTrendsUiEffect
}