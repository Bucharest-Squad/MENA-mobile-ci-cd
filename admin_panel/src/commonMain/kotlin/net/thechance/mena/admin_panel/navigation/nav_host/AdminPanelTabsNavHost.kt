package net.thechance.mena.admin_panel.navigation.nav_host

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import net.thechance.mena.admin_panel.navigation.routes.UsersManagement
import net.thechance.mena.admin_panel.navigation.routes.Deposit
import net.thechance.mena.admin_panel.navigation.routes.DukanRequests
import net.thechance.mena.admin_panel.navigation.routes.DukanManagement
import net.thechance.mena.admin_panel.presentation.screen.deposit.DepositScreen
import net.thechance.mena.admin_panel.presentation.screen.dukan_managements.DukanManagementsScreen
import net.thechance.mena.admin_panel.presentation.screen.dukan_requests.DukanRequestsScreen
import net.thechance.mena.admin_panel.presentation.screen.user_managements.UserManagementScreen

val LocalNavController = staticCompositionLocalOf<NavHostController> {
    error("Admin NavController not provided")
}

@Composable
fun AdminPanelTabsNavHost(
    navController: NavHostController
) {
    CompositionLocalProvider(LocalNavController provides navController) {
        NavHost(
            navController = navController,
            startDestination = UsersManagement,
            modifier = Modifier.fillMaxSize()
        ) {
            composable<UsersManagement> {
                UserManagementScreen()
            }
            composable<Deposit> {
                DepositScreen()
            }
            composable<DukanRequests> {
                DukanRequestsScreen()
            }
            composable<DukanManagement> {
                DukanManagementsScreen()
            }
        }
    }
}
