package net.thechance.mena.admin_panel.presentation.screen.admin_panel

data class AdminPanelScreenState(
    val isLogOutDialogShown: Boolean = false,
    val currentTab: CurrentTab = CurrentTab.USERS,
) {
    enum class CurrentTab(val title: String) {
        USERS("Users Management"),
        DUKAN_MANAGEMENT("Dukan Management"),
        DUKAN_REQUEST("Dukan Requests"),
        DEPOSIT("Deposit")
    }
}