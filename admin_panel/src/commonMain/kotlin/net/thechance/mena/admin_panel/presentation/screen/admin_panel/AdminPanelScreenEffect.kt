package net.thechance.mena.admin_panel.presentation.screen.admin_panel

interface AdminPanelScreenEffect {
    data object NavigateToLogInScreen : AdminPanelScreenEffect
    data object NavigateToUsersManagementScreen : AdminPanelScreenEffect
    data object NavigateToDepositScreen : AdminPanelScreenEffect
    data object NavigateToDukanRequestsScreen : AdminPanelScreenEffect
    data object NavigateToDukanManagementScreen : AdminPanelScreenEffect
}