package net.thechance.mena.admin_panel.presentation.screen.mainContainer

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.thechance.mena.admin_panel.domain.repository.authentication.AdminAuthenticationRepository
import net.thechance.mena.admin_panel.presentation.base.BaseViewModel
import net.thechance.mena.admin_panel.presentation.base.ErrorState
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Provided

@KoinViewModel
class MainContainerViewmodel(
    @Provided
    private val authenticationRepository: AdminAuthenticationRepository
) :
    BaseViewModel<MainContainerScreenState, MainContainerEffect>(
        MainContainerScreenState(isLoading = true)
    ), MainContainerInteractionListener {

    init {
        println("MainContainerViewmodel created")
        checkAuthenticationStatus()
    }

    override fun onLogInRequested() {

    }

    override fun onAdminPanelRequested() {

    }

    override fun mapError(throwable: Throwable): ErrorState {
        TODO()
    }

    private fun checkAuthenticationStatus() {
        viewModelScope.launch {
            updateState { it.copy(isLoading = true) }

            delay(1000)

            val isUserLoggedIn =true

            if (isUserLoggedIn) {
                updateState {
                    it.copy(
                        isLoading = false,
                        authenticationStatus = MainContainerScreenState.AuthenticationStatus.NotAuthenticated
                    )
                }
                sendEffect(MainContainerEffect.NavigateToAdminPanelScreen)
            } else {
                updateState {
                    it.copy(
                        isLoading = false,
                        authenticationStatus = MainContainerScreenState.AuthenticationStatus.NotAuthenticated
                    )
                }
                sendEffect(MainContainerEffect.NavigateToLogInScreen)
            }

            updateState { it.copy(isLoading = false) }
        }
    }
}