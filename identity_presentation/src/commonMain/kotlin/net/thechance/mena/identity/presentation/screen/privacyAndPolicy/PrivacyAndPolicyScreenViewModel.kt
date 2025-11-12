package net.thechance.mena.identity.presentation.screen.privacyAndPolicy

import net.thechance.mena.identity.domain.exception.AuthenticationException
import net.thechance.mena.identity.domain.model.PrivacyAndPolicySection
import net.thechance.mena.identity.domain.repository.PrivacyAndPolicyRepository
import net.thechance.mena.identity.presentation.base.BaseScreenModel
import net.thechance.mena.identity.presentation.base.error.ErrorState
import net.thechance.mena.identity.presentation.base.error.handleAuthenticationException
import net.thechance.mena.identity.presentation.mapper.mapAuthenticationErrorToMessage
import net.thechance.mena.identity.presentation.mapper.mapErrorToMessage
import org.jetbrains.compose.resources.StringResource

class PrivacyAndPolicyScreenViewModel(
    private val policyRepository: PrivacyAndPolicyRepository
) :
    BaseScreenModel<PrivacyAndPolicyScreenUIState, PrivacyAndPolicyScreenUIEffect>(
        PrivacyAndPolicyScreenUIState()
    ), PrivacyAndPolicyScreenInteractionListener {

    init {
        getPrivacyAndPolicy()
    }

    override fun onClickBack() {
        sendNewEffect(PrivacyAndPolicyScreenUIEffect.NavigateBack)
    }

    override fun onClearErrorMessage() {
        updateState { copy(errorMessage = null) }
    }

    private fun getPrivacyAndPolicy(){
        updateState { copy(isLoading = true , errorMessage = null) }
        tryToExecute(
            function = { policyRepository.getPrivacyAndPolicy() },
            onSuccess = ::onGetPrivacyAndPolicySuccess,
            onError = ::onGetPrivacyAndPolicyError
        )
    }
    private fun onGetPrivacyAndPolicySuccess(privacyAndPolicySections: List<PrivacyAndPolicySection>){
        updateState { copy(privacyAndPolicySections = privacyAndPolicySections.map { it.toUIState() }) }
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