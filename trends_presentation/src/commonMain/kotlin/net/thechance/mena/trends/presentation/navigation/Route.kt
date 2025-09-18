package net.thechance.mena.trends.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object Test: Route

    @Serializable
    data class Trend(val reelId: String): Route

    @Serializable
    data object MyTrends: Route
}