package net.thechance.mena.admin_panel.presentation.screen.admin_panel

import net.thechance.mena.admin_panel.domain.exceptions.NoInternetException
import net.thechance.mena.admin_panel.presentation.base.BaseViewModel
import net.thechance.mena.admin_panel.presentation.base.ErrorState
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class AdminPanelViewmodel() : BaseViewModel<AdminPanelScreenState, AdminPanelScreenEffect>(
    AdminPanelScreenState()
), AdminPanelInteractionListener {
    override fun onTabSelected(tab: AdminPanelScreenState.CurrentTab) {
        updateState { it.copy(currentTab = tab) }
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

    override fun mapError(throwable: Throwable): ErrorState {
        return when (throwable) {
            is NoInternetException -> ErrorState.NoInternet
            else -> ErrorState.UnknownError
        }
    }
}