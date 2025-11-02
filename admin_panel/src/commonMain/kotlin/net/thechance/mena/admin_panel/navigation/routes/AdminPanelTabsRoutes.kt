package net.thechance.mena.admin_panel.navigation.routes

import kotlinx.serialization.Serializable

@Serializable
sealed class AdminPanelTabsRoutes

@Serializable
data object UsersManagement : AdminPanelTabsRoutes()

@Serializable
data object Deposit : AdminPanelTabsRoutes()

@Serializable
data object DukanRequests : AdminPanelTabsRoutes()

@Serializable
data object DukanManagement : AdminPanelTabsRoutes()