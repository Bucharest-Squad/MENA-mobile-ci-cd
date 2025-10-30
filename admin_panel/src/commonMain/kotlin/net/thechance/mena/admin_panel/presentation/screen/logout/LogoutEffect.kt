package net.thechance.mena.admin_panel.presentation.screen.logout

sealed interface LogoutEffect {
    object NavigateToLogInScreen : LogoutEffect
}