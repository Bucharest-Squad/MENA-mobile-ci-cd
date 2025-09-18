package net.thechance.mena.identity.presentation.screen.login

import kotlinx.coroutines.CoroutineScope
import net.thechance.mena.identity.presentation.base.BaseScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import net.thechance.mena.identity.domain.useCase.LoginUseCase
import org.koin.core.logger.Logger
import kotlin.math.log

class LoginScreenModel (
   val loginUseCase: LoginUseCase
): BaseScreenModel<LoginScreenUIState, LoginScreenUIEffect>(LoginScreenUIState()),
    LoginScreenInteractionListener {
    override val viewModelScope: CoroutineScope
        get() = screenModelScope


    fun login() {
        updateState { copy(isLoading = true, errorMessage = null) }
        tryToExecute(
            function = {
               loginUseCase.login(
                   state.value.phoneCode,
                   state.value.phoneNumber,
                   state.value.password
               )
            },
            onSuccess = {
                updateState { copy(isLoading = false) }
                sendNewEffect(LoginScreenUIEffect.NavigateToHome)
            },
            onError = { errorState ->
                updateState { copy(isLoading = false, errorMessage = "") }
            }
        )
    }
    override fun onRegisterClicked() {
        sendNewEffect(LoginScreenUIEffect.NavigateToRegister)
    }

    override fun onForgotPasswordClicked() {
        sendNewEffect(LoginScreenUIEffect.NavigateToForgotPassword)
    }

    override fun onLoginClicked() {
        login()
    }

    override fun onPhoneCodeClicked() {
        updateState { copy(showCountryBottomSheet = true) }
    }

    override fun onPhoneCodeChanged(phoneCode: String) {
        updateState { copy(phoneCode = phoneCode) }
    }

    override fun onPhoneChanged(phone: String) {
        updateState { copy(phoneNumber = phone) }
    }

    override fun onPasswordChanged(password: String) {
        updateState { copy(password = password) }
    }

    override fun onPasswordVisibilityToggled() {
        updateState { copy(isPasswordVisible = !isPasswordVisible ) }
    }

}