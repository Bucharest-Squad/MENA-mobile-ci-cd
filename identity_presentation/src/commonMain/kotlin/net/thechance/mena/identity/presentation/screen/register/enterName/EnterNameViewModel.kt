package net.thechance.mena.identity.presentation.screen.register.enterName

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import net.thechance.mena.identity.domain.entity.PhoneNumber
import net.thechance.mena.identity.domain.exception.AuthenticationException
import net.thechance.mena.identity.domain.repository.RegisterRepository
import net.thechance.mena.identity.presentation.base.BaseScreenModel
import net.thechance.mena.identity.presentation.base.error.ErrorState
import net.thechance.mena.identity.presentation.base.error.handleAuthenticationException
import net.thechance.mena.identity.presentation.mapper.mapAuthenticationErrorToMessage
import net.thechance.mena.identity.presentation.mapper.mapErrorToMessage
import org.jetbrains.compose.resources.StringResource

class EnterNameViewModel(
    private val registerRepository: RegisterRepository,
    private val phoneNumber: PhoneNumber,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseScreenModel<EnterNameUIState, EnterNameUIEffect>(EnterNameUIState()),
    EnterNameInteractionListener {

    override fun onChangeFirstName(name: String) {
        updateState { copy(firstName = name) }
        updateNextButtonState()
    }

    override fun onLastNameChange(name: String) {
        updateState { copy(lastName = name) }
        updateNextButtonState()
    }

    override fun onUsernameChange(username: String) {
        updateState {
            copy(
                username = username,
                usernameError = null
            )
        }
        updateNextButtonState()
    }

    private fun updateNextButtonState() {
        updateState { copy(isNextEnabled = state.value.isValidInput()) }
    }

    override fun onClickNext() {
        if (!state.value.isValidInput()) return

        updateState { copy(isLoading = true, isCheckingUsername = true, errorMessage = null) }
        tryToExecute(
            function = { registerRepository.checkUserExistence(state.value.username) },
            onSuccess = { onUsernameCheckSuccess() },
            onError = ::onUsernameCheckError,
            dispatcher = dispatcher
        )
    }

    private fun onUsernameCheckSuccess() {
        updateState { copy(isLoading = false, isCheckingUsername = false) }
        sendNewEffect(createNavigateToPasswordEffect())
    }

    private fun createNavigateToPasswordEffect(): EnterNameUIEffect.NavigateToPassword {
        return EnterNameUIEffect.NavigateToPassword(
            phoneNumber = phoneNumber,
            firstName = state.value.firstName,
            lastName = state.value.lastName,
            username = state.value.username
        )
    }

    private fun onUsernameCheckError(throwable: Throwable) {
        updateState {
            copy(
                isLoading = false,
                isCheckingUsername = false,
                errorMessage = mapErrorMessage(throwable)
            )
        }
    }

    override fun onClearErrorMessage() {
        updateState { copy(errorMessage = null) }
    }

    private fun mapErrorMessage(throwable: Throwable): StringResource {
        return when (throwable) {
            is AuthenticationException -> mapAuthenticationErrorToMessage(
                handleAuthenticationException(throwable)
            )

            else -> mapErrorToMessage(ErrorState.GenericError(throwable))
        }
    }
}