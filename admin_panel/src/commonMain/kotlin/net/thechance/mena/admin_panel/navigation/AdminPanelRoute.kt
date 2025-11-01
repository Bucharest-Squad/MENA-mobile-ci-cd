package net.thechance.mena.admin_panel.navigation

sealed class AdminPanelRoute(val route: String) {
    object Login : AdminPanelRoute("login")
    object AdminRoot : AdminPanelRoute("admin_root")
    object Users : AdminPanelRoute("users")
    object Deposit : AdminPanelRoute("deposit")
    object DukanRequest : AdminPanelRoute("dukan_request")
    object DukanManagement : AdminPanelRoute("dukan_management")
}
