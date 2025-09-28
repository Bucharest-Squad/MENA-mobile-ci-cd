package net.thechance.mena.identity.presentation.screen.reset_password

import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.CoroutineScope
import net.thechance.mena.identity.presentation.base.BaseScreenModel
import net.thechance.mena.identity.presentation.base.ErrorState
import net.thechance.mena.identity.presentation.mapper.mapErrorToMessage
import net.thechance.mena.identity.domain.useCase.ResetPasswordUseCase
import net.thechance.mena.identity.domain.useCase.validation.mobileNumber.PasswordValidator

class ResetPasswordScreenViewModel(
    private val resetPasswordUseCase: ResetPasswordUseCase
) : BaseScreenModel<ResetPasswordScreenUIState, ResetPasswordScreenUIEffect>(
    ResetPasswordScreenUIState()
), ResetPasswordScreenInteractionListener {

    override val viewModelScope: CoroutineScope
        get() = screenModelScope

    private val passwordValidator = PasswordValidator()

    override fun onNewPasswordChanged(password: String) {
        updateState { copy(newPassword = password) }
        checkResetButtonEnabled()
    }

    override fun onConfirmPasswordChanged(password: String) {
        updateState { copy(confirmPassword = password) }
        checkResetButtonEnabled()
    }

    override fun onNewPasswordVisibilityToggled() {
        updateState { copy(isNewPasswordVisible = !isNewPasswordVisible) }
    }

    override fun onConfirmPasswordVisibilityToggled() {
        updateState { copy(isConfirmPasswordVisible = !isConfirmPasswordVisible) }
    }

    override fun onBackClicked() {
        sendNewEffect(ResetPasswordScreenUIEffect.NavigateBackToLogin)
    }

    override fun onResetPasswordClicked() {
        if (state.value.newPassword != state.value.confirmPassword) {
            updateState { copy(errorMessage = "New password and confirm password do not match.") }
            return
        }

        updateState { copy(isLoading = true, errorMessage = null) }

        tryToExecute(
            function = ::onResetPassword,
            onSuccess = ::onResetPasswordSuccess,
            onError = ::onErrorAccrue
        )
    }

    private fun onResetPassword() {
        resetPasswordUseCase.resetPassword(
            state.value.newPassword
        )
    }

    private fun onResetPasswordSuccess() {
        updateState { copy(isLoading = false) }
        sendNewEffect(ResetPasswordScreenUIEffect.NavigateBackToLogin)
    }

    private fun onErrorAccrue(errorState: ErrorState) {
        updateState {
            copy(
                isLoading = false,
                errorMessage = mapErrorToMessage(errorState)
            )
        }
    }

    private fun checkResetButtonEnabled() {
        updateState {
            val newPassword = newPassword
            val confirmPassword = confirmPassword

            val isPasswordsMatch = newPassword.isNotBlank() && newPassword == confirmPassword
            val isPasswordSecure = passwordValidator.isValid(newPassword)

            copy(isResetEnabled = isPasswordsMatch && isPasswordSecure)
        }
    }
}