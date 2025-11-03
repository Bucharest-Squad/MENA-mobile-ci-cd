package net.thechance.mena.admin_panel.presentation.screen.mainContainer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import net.thechance.mena.admin_panel.navigation.AdminPanel
import net.thechance.mena.admin_panel.navigation.AdminPanelNavHost
import net.thechance.mena.admin_panel.navigation.LocalNavController
import net.thechance.mena.admin_panel.navigation.Login
import net.thechance.mena.admin_panel.navigation.MainContainer
import net.thechance.mena.admin_panel.presentation.utils.ObserveAsEffect
import net.thechance.mena.designsystem.presentation.component.indicator.DotsProgressIndicator
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainContainerScreen(
    navController: NavController,
    viewModel: MainContainerViewmodel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(state.isLoading) {
        if (!state.isLoading) {
            if (state.authenticationStatus == MainContainerScreenState.AuthenticationStatus.Authenticated) {
                navController.navigate(AdminPanel) {
                    popUpTo(MainContainer) { inclusive = true }
                }
            } else {
                navController.navigate(Login) {
                    popUpTo(MainContainer) { inclusive = true }
                }
            }
        }
    }

    if (state.isLoading) {
        DotsProgressIndicator()
    }
}

private fun onMainContainerEffect(
    navController: NavController,
    effect: MainContainerEffect
) {
    when (effect) {
        is MainContainerEffect.NavigateToLogInScreen -> {
            navController.navigate(Login) {
                popUpTo(MainContainer) { inclusive = true }
            }
        }

        is MainContainerEffect.NavigateToAdminPanelScreen -> {
            navController.navigate(AdminPanel) {
                popUpTo(MainContainer) { inclusive = true }
            }
        }
    }
}
