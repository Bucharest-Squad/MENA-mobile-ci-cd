package net.thechance.mena.identity.presentation.screen.register.createPassword

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import mena.identity_presentation.generated.resources.Res
import mena.identity_presentation.generated.resources.error_password_mismatch
import mena.identity_presentation.generated.resources.error_password_validation
import net.thechance.mena.identity.domain.entity.PhoneNumber
import net.thechance.mena.identity.domain.useCase.validation.mobileNumber.PasswordValidator
import net.thechance.mena.identity.presentation.base.BaseScreenModel
import net.thechance.mena.identity.presentation.util.validatePasswordConfirmation

class CreatePasswordViewModel(
    private val passwordValidator: PasswordValidator,
    private val phoneNumber: PhoneNumber,
    private val firstName: String,
    private val lastName: String,
    private val username: String,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseScreenModel<CreatePasswordUIState, CreatePasswordUIEffect>(
    CreatePasswordUIState()
), CreatePasswordInteractionListener {

    override fun onChangeNewPassword(password: String) {
        updateState { copy(newPassword = password) }
        checkCreateButtonEnabled()
    }

    override fun onChangeConfirmPassword(password: String) {
        updateState {
            copy(
                confirmPassword = password,
                confirmPasswordErrorMessage = validatePasswordConfirmation(newPassword, password),
            )
        }
        checkCreateButtonEnabled()
    }

    override fun onToggleNewPasswordVisibility() {
        updateState { copy(isNewPasswordVisible = !isNewPasswordVisible) }
    }

    override fun onToggleConfirmPasswordVisibility() {
        updateState { copy(isConfirmPasswordVisible = !isConfirmPasswordVisible) }
    }

    override fun onClearErrorMessage() {
        updateState { copy(errorMessage = null) }
    }

    override fun onClickCreatePassword() {
        if (state.value.newPassword != state.value.confirmPassword) {
            updateState { copy(errorMessage = Res.string.error_password_mismatch) }
            return
        }

        navigateToDatePicker()
    }

    private fun navigateToDatePicker() {
        sendNewEffect(
            CreatePasswordUIEffect.NavigateToDatePicker(
                phoneNumber = phoneNumber,
                firstName = firstName,
                lastName = lastName,
                username = username,
                password = state.value.newPassword
            )
        )
    }

    private fun checkCreateButtonEnabled() {
        updateState {
            val isPasswordsMatch = newPassword.isNotBlank() && newPassword == confirmPassword
            val isPasswordSecure = passwordValidator.isValid(newPassword)

            copy(
                newPasswordErrorMessage = if (!isPasswordSecure)
                    Res.string.error_password_validation
                else null,
                isCreateEnabled = isPasswordsMatch && isPasswordSecure
            )
        }
    }
}