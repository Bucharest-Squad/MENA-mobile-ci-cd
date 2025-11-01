package net.thechance.mena.admin_panel.presentation.screen.admin_panel

import net.thechance.mena.admin_panel.presentation.base.BaseViewModel
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class AdminPanelViewmodel() : BaseViewModel<AdminPanelScreenState, AdminPanelScreenEffect>(
    AdminPanelScreenState()
), AdminPanelInteractionListener {
    override fun onUsersManagementClicked() {
        updateState { it.copy(currentTab = AdminPanelScreenState.CurrentTab.USERS) }
    }

    override fun onDukanManagementClicked() {
        updateState { it.copy(currentTab = AdminPanelScreenState.CurrentTab.DUKAN_MANAGEMENT) }
    }

    override fun onDukanRequestClicked() {
        updateState { it.copy(currentTab = AdminPanelScreenState.CurrentTab.DUKAN_REQUEST) }
    }

    override fun onDepositClicked() {
        updateState { it.copy(currentTab = AdminPanelScreenState.CurrentTab.DEPOSIT) }
    }

    override fun onLogOutClicked() {
        updateState { it.copy(isLogOutDialogShown = true) }
    }

    override fun onDismissLogoutDialog() {
        updateState { it.copy(isLogOutDialogShown = false) }
    }

    override fun onConfirmLogout() {
        updateState { it.copy(isLogOutDialogShown = false) }
        sendEffect(AdminPanelScreenEffect.NavigateToLogInScreen)
    }
}