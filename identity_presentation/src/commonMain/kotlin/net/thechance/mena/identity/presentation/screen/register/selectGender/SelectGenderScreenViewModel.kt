package net.thechance.mena.identity.presentation.screen.register.selectGender

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.datetime.LocalDate
import net.thechance.mena.identity.domain.entity.Gender
import net.thechance.mena.identity.domain.entity.PhoneNumber
import net.thechance.mena.identity.domain.exception.AuthenticationException
import net.thechance.mena.identity.domain.model.RegisterRequest
import net.thechance.mena.identity.domain.repository.RegisterRepository
import net.thechance.mena.identity.presentation.base.BaseScreenModel
import net.thechance.mena.identity.presentation.base.error.ErrorState
import net.thechance.mena.identity.presentation.base.error.handleAuthenticationException
import net.thechance.mena.identity.presentation.mapper.mapAuthenticationErrorToMessage
import net.thechance.mena.identity.presentation.mapper.mapErrorToMessage
import org.jetbrains.compose.resources.StringResource

class SelectGenderScreenViewModel(
    private val registerRepository: RegisterRepository,
    private val phoneNumber: PhoneNumber,
    private val firstName: String,
    private val lastName: String,
    private val username: String,
    private val password: String,
    private val birthDate: LocalDate,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) :
    BaseScreenModel<SelectGenderScreenUIState, SelectGenderScreenUIEffect>(
        SelectGenderScreenUIState()
    ), SelectGenderScreenInteractionListener {

    override fun onClickRegister() {
        val gender = state.value.gender ?: return
        updateState { copy(isRegisterLoading = true, errorMessage = null) }
        tryToExecute(
            function = { register(gender) },
            onSuccess = { _ -> onRegisterSuccess() },
            onError = ::onRegisterError,
            dispatcher = dispatcher
        )
    }

    private suspend fun register(gender: Gender) {
        registerRepository.register(
            RegisterRequest(
                phoneNumber = phoneNumber,
                username = username,
                firstName = firstName,
                lastName = lastName,
                birthDate = birthDate,
                gender = gender,
                password = password
            )
        )
    }

    private fun onRegisterSuccess() {
        updateState { copy(isRegisterLoading = false) }
        sendNewEffect(SelectGenderScreenUIEffect.NavigateToUploadProfileImage)
    }

    private fun onRegisterError(throwable: Throwable) {
        updateState {
            copy(
                isRegisterLoading = false,
                errorMessage = mapErrorMessage(throwable)
            )
        }
    }

    override fun onChangeGender(gender: Gender) {
        updateState { copy(gender = gender, isRegisterEnabled = true) }
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