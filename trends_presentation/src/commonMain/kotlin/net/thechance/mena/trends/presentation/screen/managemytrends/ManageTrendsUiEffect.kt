package net.thechance.mena.trends.presentation.screen.managemytrends

sealed class ManageTrendsUiEffect {
    object NavigateBack : ManageTrendsUiEffect()
    data class NavigateToTrend(val reelId: Int) : ManageTrendsUiEffect()
}