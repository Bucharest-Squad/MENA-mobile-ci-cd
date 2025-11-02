package net.thechance.mena.admin_panel.navigation.routes

import kotlinx.serialization.Serializable

@Serializable
sealed class AdminPanelRoute

@Serializable
data object Login : AdminPanelRoute()

@Serializable
data object AdminPanel : AdminPanelRoute()
