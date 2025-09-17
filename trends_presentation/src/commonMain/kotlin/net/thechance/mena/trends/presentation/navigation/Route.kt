package net.thechance.mena.trends.presentation.navigation

import kotlinx.serialization.Serializable

sealed interface Route {
    @Serializable
    data object Categories: Route

    @Serializable
    data object Trends: Route
}