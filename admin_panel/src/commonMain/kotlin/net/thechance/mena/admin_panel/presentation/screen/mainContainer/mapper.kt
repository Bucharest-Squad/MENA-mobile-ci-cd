package net.thechance.mena.admin_panel.presentation.screen.mainContainer

fun determineEffect(currentTab: MainContainerScreenState.CurrentTab): MainContainerEffect =
    when (currentTab) {
        MainContainerScreenState.CurrentTab.USERS_MANAGEMENT ->
            MainContainerEffect.NavigateToUsersManagementScreen

        MainContainerScreenState.CurrentTab.DUKAN_MANAGEMENT ->
            MainContainerEffect.NavigateToDukanManagementScreen

        MainContainerScreenState.CurrentTab.DUKAN_REQUEST ->
            MainContainerEffect.NavigateToDukanRequestsScreen

        MainContainerScreenState.CurrentTab.DEPOSIT ->
            MainContainerEffect.NavigateToDepositScreen
    }