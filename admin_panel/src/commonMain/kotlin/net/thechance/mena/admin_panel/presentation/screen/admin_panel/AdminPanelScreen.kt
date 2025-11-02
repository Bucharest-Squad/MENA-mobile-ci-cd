package net.thechance.mena.admin_panel.presentation.screen.admin_panel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import net.thechance.mena.admin_panel.navigation.nav_host.AdminPanelTabsNavHost
import net.thechance.mena.admin_panel.navigation.nav_host.LocalAdminPanelNavController
import net.thechance.mena.admin_panel.navigation.routes.AdminPanel
import net.thechance.mena.admin_panel.navigation.routes.Login
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
fun AdminPanelScreen(
    viewmodel: AdminPanelViewmodel = koinViewModel(),
) {
    val state by viewmodel.state.collectAsStateWithLifecycle()
    val adminPanelNavController = LocalAdminPanelNavController.current
    ObserveAsEffect(
        effect = viewmodel.uiEffect,
        onEffect = { effect ->
            onAdminPanelEffect(
                adminPanelNavController,
                effect
            )
        }
    )

    val adminPanelTabsNavController = rememberNavController()
    LaunchedEffect(state.currentTab) {
        val tabRoute = state.currentTab.route
        adminPanelTabsNavController.navigate(tabRoute) {
            popUpTo(adminPanelTabsNavController.graph.startDestinationId) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    AdminPanelContent(state, viewmodel, adminPanelTabsNavController)
}

@Composable
private fun AdminPanelContent(
    state: AdminPanelScreenState,
    interactionListener: AdminPanelInteractionListener,
    adminPanelTabsNavController: NavHostController
) {
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
                    AdminPanelTabsNavHost(adminPanelTabsNavController)
                }
            }
        }
    )
}

private fun onAdminPanelEffect(
    navController: NavController,
    effect: AdminPanelScreenEffect,
) {
    when (effect) {
        is AdminPanelScreenEffect.NavigateToLogInScreen -> {
            navController.navigate(Login) {
                popUpTo(AdminPanel) { inclusive = true }
            }
        }
    }
}
