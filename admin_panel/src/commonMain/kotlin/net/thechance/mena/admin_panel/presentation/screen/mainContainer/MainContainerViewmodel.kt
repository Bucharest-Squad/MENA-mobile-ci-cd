package net.thechance.mena.admin_panel.presentation.screen.mainContainer

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.thechance.mena.admin_panel.domain.exceptions.NoInternetException
import net.thechance.mena.admin_panel.domain.repository.authentication.AdminAuthenticationRepository
import net.thechance.mena.admin_panel.presentation.base.BaseViewModel
import net.thechance.mena.admin_panel.presentation.base.ErrorState
import net.thechance.mena.admin_panel.presentation.model.SnackBarState
import net.thechance.mena.admin_panel.presentation.utils.StringProvider
import net.thechance.mena.admin_panel.presentation.utils.getErrorSnackBarMsg
import net.thechance.mena.admin_panel.presentation.utils.getErrorSnackBarTitle
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Provided

@KoinViewModel
class MainContainerViewmodel(
    @Provided
    private val authenticationRepository: AdminAuthenticationRepository,
    @Provided
    private val stringProvider: StringProvider,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) :
    BaseViewModel<MainContainerScreenState, MainContainerEffect>(
        MainContainerScreenState()
    ), MainContainerInteractionListener {
    init {
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

    override fun onDismissLogout() {
        updateState { it.copy(isLogOutDialogShown = false) }
    }

    override fun onConfirmLogout() {
        tryToExecute(
            callee = ::confirmLogout,
            onSuccess = { onSuccessLoggedOut() },
            onError = ::onFailureLoggedOut,
            dispatcher = dispatcher
        )

    }

    override fun mapError(throwable: Throwable): ErrorState {
        return when (throwable) {
            is NoInternetException -> ErrorState.NoInternet
            else -> ErrorState.UnknownError
        }
    }

    private fun onSuccessLoggedOut() {
        updateState { it.copy(isLogOutDialogShown = false) }
        sendEffect(MainContainerEffect.NavigateToLogInScreen)
    }

    private suspend fun confirmLogout() {
        authenticationRepository.logout()
    }

    private suspend fun onFailureLoggedOut(errorState: ErrorState) {
        showSnackBar(
            title = stringProvider.getString(errorState.getErrorSnackBarTitle()),
            message = stringProvider.getString(errorState.getErrorSnackBarMsg()),
            isSuccess = false
        )
    }

    private fun showSnackBar(
        title: String,
        message: String,
        isSuccess: Boolean,
        durationMillis: Long = 3000L
    ) {
        viewModelScope.launch {
            updateState { oldState ->
                oldState.copy(
                    snackBar = SnackBarState(
                        isVisible = true,
                        title = title,
                        message = message,
                        isSuccess = isSuccess
                    )
                )
            }

            delay(durationMillis)

            hideSnackBar()
        }
    }

    private fun hideSnackBar() {
        updateState { oldState ->
            oldState.copy(snackBar = oldState.snackBar.copy(isVisible = false))
        }
    }

    private suspend fun checkAuthenticationStatus() {
        val isUserLoggedIn = authenticationRepository.isUserLoggedIn()

        if (isUserLoggedIn) {
            updateState {
                it.copy(
                    authenticationStatus = MainContainerScreenState.AuthenticationStatus.Authenticated,
                )
            }
            sendEffect(MainContainerEffect.NavigateToAdminPanelScreen)
        } else {
            updateState {
                it.copy(
                    authenticationStatus = MainContainerScreenState.AuthenticationStatus.NotAuthenticated,
                )
            }
            sendEffect(MainContainerEffect.NavigateToLogInScreen)
        }
    }
}
