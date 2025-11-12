package net.thechance.mena.identity.presentation.screen.privacyAndPolicy

import net.thechance.mena.identity.domain.model.PolicySection
import net.thechance.mena.identity.domain.repository.PolicyRepository
import net.thechance.mena.identity.presentation.base.BaseScreenModel

class PrivacyAndPolicyScreenViewModel(
    private val policyRepository: PolicyRepository
) :
    BaseScreenModel<PrivacyAndPolicyScreenUIState, PrivacyAndPolicyScreenUIEffect>(
        PrivacyAndPolicyScreenUIState()
    ), PrivacyAndPolicyScreenInteractionListener {

    init {
        updateState { copy(policySections = getPolicyAndPolicy()) }
    }

    override fun onClickBack() {
        sendNewEffect(PrivacyAndPolicyScreenUIEffect.NavigateToBack)
    }

    private fun getPolicyAndPolicy(): List<PolicySectionUIState> {
        return policyRepository.getPolicyAndPolicy().map { it.toUIState() }
    }
}