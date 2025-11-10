package net.thechance.mena.identity.presentation.screen.privacyAndPolicy

import net.thechance.mena.identity.presentation.base.BaseScreenModel

class PrivacyAndPolicyScreenViewModel() :
    BaseScreenModel<PrivacyAndPolicyScreenUIState, PrivacyAndPolicyScreenUIEffect>(
        PrivacyAndPolicyScreenUIState()
    ), PrivacyAndPolicyScreenInteractionListener {

    init {
        updateState { copy(policySections = getPolicyAndPolicy()) }
    }

    override fun onClickBack() {
        sendNewEffect(PrivacyAndPolicyScreenUIEffect.NavigateToBack)
    }

    private fun getPolicyAndPolicy(): List<PrivacyAndPolicyScreenUIState.PolicySectionUIState> {

        val title = "What is Lorem Ipsum?"
        val loremContent = "is simply dummy text of the printing and typesetting industry. " +
                    "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, " +
                    "when an unknown printer took a galley of type and scrambled it to make a type specimen book. " +
                    "It has survived not only five centuries"

        val sections = listOf(
            PrivacyAndPolicyScreenUIState.PolicySectionUIState(
                title = title,
                content = loremContent
            ),
            PrivacyAndPolicyScreenUIState.PolicySectionUIState(
                title = title,
                content = loremContent
            )
        )

        return sections
    }
}