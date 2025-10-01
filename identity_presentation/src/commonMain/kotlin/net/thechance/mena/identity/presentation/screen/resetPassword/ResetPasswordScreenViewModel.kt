package net.thechance.mena.identity.presentation.screen.resetPassword

import cafe.adriel.voyager.core.model.screenModelScope
import kotlinx.coroutines.CoroutineScope
import net.thechance.mena.identity.domain.entity.PhoneNumber
import net.thechance.mena.identity.domain.repository.ResetPasswordRepository
import net.thechance.mena.identity.presentation.base.BaseScreenModel
import net.thechance.mena.identity.presentation.base.ErrorState
import net.thechance.mena.identity.presentation.mapper.mapErrorToMessage
import net.thechance.mena.identity.domain.useCase.validation.mobileNumber.PasswordValidator

class ResetPasswordScreenViewModel(
    private val passwordValidator: PasswordValidator,
    private val resetPasswordRepository: ResetPasswordRepository,
    private val phoneNumber: String,
    private val callingCode:String
) : BaseScreenModel<ResetPasswordScreenUIState, ResetPasswordScreenUIEffect>(
    ResetPasswordScreenUIState()
), ResetPasswordScreenInteractionListener {

    override val viewModelScope: CoroutineScope
        get() = screenModelScope

    override fun onChangeNewPassword(password: String) {
        updateState { copy(newPassword = password) }
        checkResetButtonEnabled()
    }

    override fun onChangeConfirmPassword(password: String) {
        updateState { copy(confirmPassword = password) }
        checkResetButtonEnabled()
    }

    override fun onToggleNewPasswordVisibility() {
        updateState { copy(isNewPasswordVisible = !isNewPasswordVisible) }
    }

    override fun onToggleConfirmPasswordVisibility() {
        updateState { copy(isConfirmPasswordVisible = !isConfirmPasswordVisible) }
    }

    override fun onClickBack() {
        sendNewEffect(ResetPasswordScreenUIEffect.NavigateBackToLogin)
    }

    override fun onClearErrorMessage() {
        updateState { copy(errorMessage = null) }
    }

    override fun onClickResetPassword() {
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

    private suspend fun onResetPassword() {
        resetPasswordRepository.resetPassword(
            state.value.confirmPassword,
            state.value.newPassword,
            phoneNumber = PhoneNumber(phoneNumber, callingCode).toString()
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