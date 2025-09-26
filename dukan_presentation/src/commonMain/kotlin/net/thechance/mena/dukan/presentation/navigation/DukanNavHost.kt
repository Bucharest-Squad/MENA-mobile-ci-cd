package net.thechance.mena.dukan.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import net.thechance.mena.dukan.presentation.screen.createDukan.CreateDukanScreen
import net.thechance.mena.dukan.presentation.screen.main.MainScreen
import net.thechance.mena.dukan.presentation.screen.pendingDukan.PendingDukanScreen
import net.thechance.mena.dukan.presentation.viewModel.mainScreen.MainScreenUiState

@Composable
fun DukanNavHost() {
    val navController = rememberNavController()
    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
        NavHost(
            navController = navController,
            startDestination = DukanRoute.MainScreenRoute,
        ) {
            composable<DukanRoute.MainScreenRoute> {
                MainScreen()
            }

            composable<DukanRoute.CreateDukanScreenRoute> {
                CreateDukanScreen()
            }
            composable<DukanRoute.MyDukanScreenRoute> {
                // MyDukanScreen()
            }
            composable<DukanRoute.PendingScreenRoute> { backStackEntry ->
                val route: DukanRoute.PendingScreenRoute =
                    backStackEntry.toRoute()
                val status = MainScreenUiState.DukanStatusUi.valueOf(route.dukanStatus)
                PendingDukanScreen(
                    dukanName = route.dukanName,
                    onBackClick = { navController.popBackStack() },
                    dukanStatus = status
                )
            }
        }
    }
}