package net.thechance.mena.admin_panel.presentation.screen.admin_panel

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.thechance.mena.admin_panel.presentation.component.AdminConfirmationDialog
import net.thechance.mena.admin_panel.presentation.screen.admin_panel.component.AdminPanelSideBar
import net.thechance.mena.admin_panel.presentation.utils.ObserveAsEffect
import net.thechance.mena.admin_panel.resources.Res
import net.thechance.mena.admin_panel.resources.confirm_logout_icon
import net.thechance.mena.admin_panel.resources.deposit
import net.thechance.mena.admin_panel.resources.dukan_management
import net.thechance.mena.admin_panel.resources.dukan_requests
import net.thechance.mena.admin_panel.resources.logout
import net.thechance.mena.admin_panel.resources.logout_dialog_icon
import net.thechance.mena.admin_panel.resources.logout_disc
import net.thechance.mena.admin_panel.resources.users_management
import net.thechance.mena.designsystem.presentation.component.scaffold.Scaffold
import net.thechance.mena.designsystem.presentation.component.text.Text
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AdminPanelScreen(
    viewmodel: AdminPanelViewmodel = koinViewModel(),
    onLogoutConfirmed: () -> Unit
) {
    val state by viewmodel.state.collectAsStateWithLifecycle()

    ObserveAsEffect(
        effect = viewmodel.uiEffect,
        onEffect = { effect ->
            onAdminPanelEffect(
                onLogoutConfirmed,
                effect,
            )
        }
    )

    AdminPanelContent(state, viewmodel)
}

@Composable
private fun AdminPanelContent(
    state: AdminPanelScreenState,
    interactionListener: AdminPanelInteractionListener
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
                        text = tabTitle(state.currentTab),
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
                    CurrentTabContent(state.currentTab)
                }
            }
        }
    )
}

@Composable
private fun CurrentTabContent(
    currentTab: AdminPanelScreenState.CurrentTab
) {
    Crossfade(targetState = currentTab) { tab ->
        when (tab) {
            AdminPanelScreenState.CurrentTab.USERS -> {
                Placeholder(tabTitle(tab))
            }

            AdminPanelScreenState.CurrentTab.DEPOSIT -> {
                Placeholder(tabTitle(tab))
            }

            AdminPanelScreenState.CurrentTab.DUKAN_REQUEST -> {
                Placeholder(tabTitle(tab))
            }

            AdminPanelScreenState.CurrentTab.DUKAN_MANAGEMENT -> {
                Placeholder(tabTitle(tab))
            }
        }
    }
}


@Composable
private fun Placeholder(title: String) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            style = Theme.typography.title.large
        )
    }
}

@Composable
private fun tabTitle(tab: AdminPanelScreenState.CurrentTab): String {
    return when (tab) {
        AdminPanelScreenState.CurrentTab.USERS ->
            stringResource(Res.string.users_management)

        AdminPanelScreenState.CurrentTab.DUKAN_MANAGEMENT ->
            stringResource(Res.string.dukan_management)

        AdminPanelScreenState.CurrentTab.DUKAN_REQUEST ->
            stringResource(Res.string.dukan_requests)

        AdminPanelScreenState.CurrentTab.DEPOSIT ->
            stringResource(Res.string.deposit)
    }
}

private fun onAdminPanelEffect(
    onLogoutConfirmed: () -> Unit,
    effect: AdminPanelScreenEffect,
) {
    when (effect) {
        is AdminPanelScreenEffect.NavigateToLogInScreen -> {
            onLogoutConfirmed()
        }
    }
}
