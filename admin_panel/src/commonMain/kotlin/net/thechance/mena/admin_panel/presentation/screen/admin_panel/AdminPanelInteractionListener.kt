package net.thechance.mena.admin_panel.presentation.screen.admin_panel

interface AdminPanelInteractionListener {
    fun onTabSelected(tab: AdminPanelScreenState.CurrentTab)
    fun onLogOutClicked()
    fun onDismissLogoutDialog()
    fun onConfirmLogout()
}