package net.thechance.mena.admin_panel.presentation.screen.mainContainer

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.thechance.mena.admin_panel.domain.exceptions.NoInternetException
import net.thechance.mena.admin_panel.domain.repository.authentication.AdminAuthenticationRepository
import net.thechance.mena.admin_panel.navigation.AdminPanel
import net.thechance.mena.admin_panel.navigation.Login
import net.thechance.mena.admin_panel.presentation.base.BaseViewModel
import net.thechance.mena.admin_panel.presentation.base.ErrorState
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Provided

@KoinViewModel
class MainContainerViewmodel(
    @Provided
    private val authenticationRepository: AdminAuthenticationRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) :
    BaseViewModel<MainContainerScreenState, MainContainerEffect>(
        MainContainerScreenState()
    ), MainContainerInteractionListener {
    init {
        println("MainContainerViewmodel created")
        viewModelScope.launch {
            checkAuthenticationStatus()
        }
    }

    override fun onTabSelected(tab: MainContainerScreenState.CurrentTab) {
        updateState { it.copy(currentTab = tab) }
        sendEffect(determineEffect(tab))
    }

    override fun onLogOutClicked() {
        updateState { it.copy(isLogOutDialogShown = true) }
    }

    override fun onDismissLogoutDialog() {
        updateState { it.copy(isLogOutDialogShown = false) }
    }

    override fun onConfirmLogout() {
        tryToExecute(
            callee = ::confirmLogout,
            onSuccess = { onSuccessLoggedOut() },
            onError = { onFailureLoggedOut() },
            dispatcher = dispatcher
        )

    }

    override fun mapError(throwable: Throwable): ErrorState {
        return when (throwable) {
            is NoInternetException -> ErrorState.NoInternet
            else -> ErrorState.UnknownError
        }
    }

    private fun determineEffect(currentTab: MainContainerScreenState.CurrentTab): MainContainerEffect =
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

    private fun onSuccessLoggedOut() {
        updateState { it.copy(isLogOutDialogShown = false) }
        sendEffect(MainContainerEffect.NavigateToLogInScreen)
    }

    private fun confirmLogout() {
        viewModelScope.launch {
            authenticationRepository.logout()
        }
    }

    private fun onFailureLoggedOut() {
        TODO()
    }

    private suspend fun checkAuthenticationStatus() {
        updateState { it.copy(isLoading = true) }

        val isUserLoggedIn = authenticationRepository.isUserLoggedIn()

        if (isUserLoggedIn) {
            updateState {
                it.copy(
                    isLoading = false,
                    authenticationStatus = MainContainerScreenState.AuthenticationStatus.NotAuthenticated,
                    startDestination = AdminPanel
                )
            }
            sendEffect(MainContainerEffect.NavigateToAdminPanelScreen)
        } else {
            updateState {
                it.copy(
                    isLoading = false,
                    authenticationStatus = MainContainerScreenState.AuthenticationStatus.NotAuthenticated,
                    startDestination = Login
                )
            }
            sendEffect(MainContainerEffect.NavigateToLogInScreen)
        }
    }
}
