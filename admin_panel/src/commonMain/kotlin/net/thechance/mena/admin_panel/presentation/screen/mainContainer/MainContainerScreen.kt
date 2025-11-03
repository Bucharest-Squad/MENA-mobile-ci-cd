package net.thechance.mena.admin_panel.presentation.screen.mainContainer

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import net.thechance.mena.admin_panel.navigation.AdminPanelNavHost
import net.thechance.mena.admin_panel.navigation.Deposit
import net.thechance.mena.admin_panel.navigation.DukanManagement
import net.thechance.mena.admin_panel.navigation.DukanRequests
import net.thechance.mena.admin_panel.navigation.LocalNavController
import net.thechance.mena.admin_panel.navigation.Login
import net.thechance.mena.admin_panel.navigation.UsersManagement
import net.thechance.mena.admin_panel.presentation.component.AdminConfirmationDialog
import net.thechance.mena.admin_panel.presentation.screen.admin_panel.component.AdminPanelSideBar
import net.thechance.mena.admin_panel.presentation.utils.ObserveAsEffect
import net.thechance.mena.admin_panel.resources.Res
import net.thechance.mena.admin_panel.resources.confirm_logout_icon
import net.thechance.mena.admin_panel.resources.logout
import net.thechance.mena.admin_panel.resources.logout_dialog_icon
import net.thechance.mena.admin_panel.resources.logout_disc
import net.thechance.mena.designsystem.presentation.component.scaffold.Scaffold
import net.thechance.mena.designsystem.presentation.component.text.Text
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun MainContainerScreen(
    navController: NavController,
    viewModel: MainContainerViewmodel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEffect(
        effect = viewModel.uiEffect,
        onEffect = { effect ->
            onMainContainerEffect(
                navController,
                effect
            )
        }
    )
    MainContainerContent(
        state = state,
        interactionListener = viewModel
    )
}

@Composable
private fun MainContainerContent(
    state: MainContainerScreenState,
    interactionListener: MainContainerInteractionListener
) {
    val navController = LocalNavController.current
    val showBars = remember { navController.currentBackStackEntry?.destination?.hasRoute(Login.toString(), null) != true }

    Scaffold(
        overlays = {
            dialog(isVisible = state.isLogOutDialogShown) {
                AdminConfirmationDialog(
                    dialogIcon = painterResource(Res.drawable.logout_dialog_icon),
                    confirmationIcon = painterResource(Res.drawable.confirm_logout_icon),
                    title = stringResource(Res.string.logout),
                    description = stringResource(Res.string.logout_disc),
                    confirmationButtonText = stringResource(Res.string.logout),
                    onDismiss = interactionListener::onDismissLogoutDialog,
                    onConfirm = interactionListener::onConfirmLogout,
                    isVisible = state.isLogOutDialogShown
                )
            }
        },
        content = {
            AnimatedVisibility(
                visible = showBars,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Box {
                    Row(
                        modifier = Modifier.padding(start = 114.dp)
                            .fillMaxWidth()
                            .background(Theme.colorScheme.background.surfaceLow)
                            .padding(16.dp)
                    ) {
                        Text(
                            text = stringResource(state.currentTab.title),
                            style = Theme.typography.title.medium,
                            color = Theme.colorScheme.shadePrimary
                        )
                    }
                    Row {
                        AdminPanelSideBar(
                            modifier = Modifier.padding(bottom = 34.dp),
                            selectedTab = state.currentTab,
                            interactionListener = interactionListener
                        )
                    }
                }
            }
            AdminPanelNavHost(navController)
        }
    )
}

private fun onMainContainerEffect(
    navController: NavController,
    effect: MainContainerEffect
) {

    when (effect) {
        is MainContainerEffect.NavigateToAdminPanelScreen -> {

            navController.navigate(UsersManagement) {
                popUpTo(UsersManagement) { inclusive = true }
            }
        }

        is MainContainerEffect.NavigateToLogInScreen -> {
            navController.navigate(Login) {
                popUpTo(UsersManagement) { inclusive = true }
            }
        }

        is MainContainerEffect.NavigateToUsersManagementScreen -> {
            navController.navigate(UsersManagement)
        }

        is MainContainerEffect.NavigateToDukanManagementScreen -> {
            navController.navigate(DukanManagement)
        }

        is MainContainerEffect.NavigateToDukanRequestsScreen -> {
            navController.navigate(DukanRequests)
        }

        is MainContainerEffect.NavigateToDepositScreen -> {
            navController.navigate(Deposit)
        }
    }
}
