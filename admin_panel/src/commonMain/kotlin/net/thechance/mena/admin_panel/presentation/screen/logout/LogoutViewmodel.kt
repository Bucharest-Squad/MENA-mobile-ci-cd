package net.thechance.mena.admin_panel.presentation.screen.logout

import net.thechance.mena.admin_panel.presentation.base.BaseViewModel
import org.koin.android.annotation.KoinViewModel

@KoinViewModel
class LogoutViewModel() : BaseViewModel<LogoutScreenState, LogoutEffect>(
    LogoutScreenState()
), LogoutInteractionListener {
    override fun onDismissLogoutDialog() {
        updateState { it.copy(isLogoutDialogHidden = true) }
    }

    override fun onConfirmLogout() {
        updateState { it.copy(isLogoutDialogHidden = true) }
        sendEffect(LogoutEffect.NavigateToLogInScreen)
    }
}