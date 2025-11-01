package net.thechance.mena.admin_panel.presentation.screen.admin_panel

interface AdminPanelInteractionListener {
    fun onUsersManagementClicked()
    fun onDukanManagementClicked()
    fun onDukanRequestClicked()
    fun onDepositClicked()
    fun onLogOutClicked()
    fun onDismissLogoutDialog()
    fun onConfirmLogout()
}