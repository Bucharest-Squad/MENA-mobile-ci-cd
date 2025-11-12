package net.thechance.mena.identity.presentation.screen.privacyAndPolicy

import net.thechance.mena.identity.domain.exception.AuthenticationException
import net.thechance.mena.identity.domain.model.PolicySection
import net.thechance.mena.identity.domain.repository.PolicyRepository
import net.thechance.mena.identity.presentation.base.BaseScreenModel
import net.thechance.mena.identity.presentation.base.error.ErrorState
import net.thechance.mena.identity.presentation.base.error.handleAuthenticationException
import net.thechance.mena.identity.presentation.mapper.mapAuthenticationErrorToMessage
import net.thechance.mena.identity.presentation.mapper.mapErrorToMessage
import org.jetbrains.compose.resources.StringResource

class PrivacyAndPolicyScreenViewModel(
    private val policyRepository: PolicyRepository
) :
    BaseScreenModel<PrivacyAndPolicyScreenUIState, PrivacyAndPolicyScreenUIEffect>(
        PrivacyAndPolicyScreenUIState()
    ), PrivacyAndPolicyScreenInteractionListener {

    init {
        updateState { copy(isLoading = true , errorMessage = null) }
        tryToExecute(
            function = { policyRepository.getPrivacyAndPolicy() },
            onSuccess = ::onGetPrivacyAndPolicySuccess,
            onError = ::onGetPrivacyAndPolicyError
        )

    }

    override fun onClickBack() {
        sendNewEffect(PrivacyAndPolicyScreenUIEffect.NavigateToBack)
    }

    override fun onClearErrorMessage() {
        updateState { copy(errorMessage = null) }
    }

    private fun onGetPrivacyAndPolicySuccess(policySections: List<PolicySection>){
        updateState { copy(policySections = policySections.map { it.toUIState() }) }
    }
    private fun onGetPrivacyAndPolicyError(throwable: Throwable) {
        updateState {
            copy(
                errorMessage = mapErrorMessage(throwable),
                isLoading = false,
            )
        }
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