package net.thechance.mena.admin_panel.navigation.nav_host

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.thechance.mena.admin_panel.navigation.routes.AdminPanel
import net.thechance.mena.admin_panel.navigation.routes.Login
import net.thechance.mena.admin_panel.navigation.routes.AdminPanelRoute
import net.thechance.mena.admin_panel.presentation.screen.admin_panel.AdminPanelScreen
import net.thechance.mena.admin_panel.presentation.screen.login.LoginScreen

val LocalAdminPanelNavController = staticCompositionLocalOf<NavHostController> {
    error("Admin NavController not provided")
}

@Composable
fun AdminPanelNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: AdminPanelRoute = Login,
) {
    CompositionLocalProvider(LocalAdminPanelNavController provides navController) {
        NavHost(
            navController = navController,
            startDestination = startDestination
        ) {
            composable<Login> {
                LoginScreen()
            }

            composable<AdminPanel> {
                AdminPanelScreen()
            }
        }
    }
}
