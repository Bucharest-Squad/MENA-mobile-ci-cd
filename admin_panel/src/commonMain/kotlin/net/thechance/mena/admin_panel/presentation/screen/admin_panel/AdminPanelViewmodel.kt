package net.thechance.mena.admin_panel.presentation.screen.admin_panel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.thechance.mena.admin_panel.domain.exceptions.NoInternetException
import net.thechance.mena.admin_panel.domain.repository.authentication.AdminAuthenticationRepository
import net.thechance.mena.admin_panel.presentation.base.BaseViewModel
import net.thechance.mena.admin_panel.presentation.base.ErrorState
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Provided

@KoinViewModel
class AdminPanelViewmodel(
    @Provided
    private val authenticationRepository: AdminAuthenticationRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO

) : BaseViewModel<AdminPanelScreenState, AdminPanelScreenEffect>(
    AdminPanelScreenState()
), AdminPanelInteractionListener {
    override fun onTabSelected(tab: AdminPanelScreenState.CurrentTab) {
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

    private fun determineEffect(currentTab: AdminPanelScreenState.CurrentTab): AdminPanelScreenEffect =
        when (currentTab) {
            AdminPanelScreenState.CurrentTab.USERS_MANAGEMENT ->
                AdminPanelScreenEffect.NavigateToUsersManagementScreen

            AdminPanelScreenState.CurrentTab.DUKAN_MANAGEMENT ->
                AdminPanelScreenEffect.NavigateToDukanManagementScreen

            AdminPanelScreenState.CurrentTab.DUKAN_REQUEST ->
                AdminPanelScreenEffect.NavigateToDukanRequestsScreen

            AdminPanelScreenState.CurrentTab.DEPOSIT ->
                AdminPanelScreenEffect.NavigateToDepositScreen
        }

    private fun onSuccessLoggedOut() {
        updateState { it.copy(isLogOutDialogShown = false) }
        sendEffect(AdminPanelScreenEffect.NavigateToLogInScreen)
    }

    private fun confirmLogout() {
        viewModelScope.launch {
            authenticationRepository.logout()
        }
    }

    private fun onFailureLoggedOut() {
        TODO()
    }

}