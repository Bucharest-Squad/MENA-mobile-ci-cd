package net.thechance.mena.admin_panel.presentation.screen.mainContainer


interface MainContainerInteractionListener {

    fun onTabSelected(tab: MainContainerScreenState.CurrentTab)
    fun onLogOutClicked()
    fun onDismissLogout()
    fun onConfirmLogout()
}