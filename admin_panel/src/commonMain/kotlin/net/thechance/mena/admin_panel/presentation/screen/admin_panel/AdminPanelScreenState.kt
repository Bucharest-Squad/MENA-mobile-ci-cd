package net.thechance.mena.admin_panel.presentation.screen.admin_panel

data class AdminPanelScreenState(
    val isLogOutDialogShown: Boolean = false,
    val currentTab: CurrentTab = CurrentTab.USERS,
) {
    enum class CurrentTab {
        USERS,
        DUKAN_MANAGEMENT,
        DUKAN_REQUEST,
        DEPOSIT
    }
}