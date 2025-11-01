package net.thechance.mena.admin_panel.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.navigation.compose.composable
import net.thechance.mena.admin_panel.presentation.screen.admin_panel.AdminPanelScreen
import net.thechance.mena.admin_panel.presentation.screen.login.LoginScreen

val LocalAdminNavController = staticCompositionLocalOf<NavHostController> {
    error("Admin NavController not provided")
}

@Composable
fun AdminPanelNavHost(
    navController: NavHostController = rememberNavController(),
    startDestination: AdminPanelRoute = AdminPanelRoute.Login,
) {
    CompositionLocalProvider(LocalAdminNavController provides navController) {
        NavHost(
            navController = navController,
            startDestination = startDestination.route
        ) {
            composable(AdminPanelRoute.Login.route) {
                LoginScreen(
                    onLoginSuccess = {
                        navController.navigate(AdminPanelRoute.AdminRoot.route) {
                            popUpTo(AdminPanelRoute.Login.route) { inclusive = true }
                        }
                    }
                )
            }

            composable(AdminPanelRoute.AdminRoot.route) {
                AdminPanelScreen(
                    onLogoutConfirmed = {
                        navController.navigate(AdminPanelRoute.Login.route) {
                            popUpTo(AdminPanelRoute.AdminRoot.route) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}
