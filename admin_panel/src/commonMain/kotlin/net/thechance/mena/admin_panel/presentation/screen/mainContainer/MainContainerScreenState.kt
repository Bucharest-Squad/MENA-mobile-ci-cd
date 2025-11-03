package net.thechance.mena.admin_panel.presentation.screen.mainContainer

import net.thechance.mena.admin_panel.navigation.AdminPanelRoute
import net.thechance.mena.admin_panel.navigation.Login

data class MainContainerScreenState(
    val authenticationStatus: AuthenticationStatus = AuthenticationStatus.NotAuthenticated,
    val startDestination: AdminPanelRoute = Login,
    val isLoading: Boolean = true
) {
    enum class AuthenticationStatus {
        Authenticated,
        NotAuthenticated,
    }
}
