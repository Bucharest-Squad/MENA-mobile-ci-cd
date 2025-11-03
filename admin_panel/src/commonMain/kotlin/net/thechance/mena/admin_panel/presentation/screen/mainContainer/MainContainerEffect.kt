package net.thechance.mena.admin_panel.presentation.screen.mainContainer

sealed interface MainContainerEffect {
    data object NavigateToLogInScreen : MainContainerEffect
    data object NavigateToAdminPanelScreen : MainContainerEffect
}