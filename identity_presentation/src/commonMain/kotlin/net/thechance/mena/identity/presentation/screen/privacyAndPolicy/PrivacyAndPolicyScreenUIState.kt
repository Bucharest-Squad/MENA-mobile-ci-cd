package net.thechance.mena.identity.presentation.screen.privacyAndPolicy

import net.thechance.mena.identity.domain.model.PolicySection

data class PrivacyAndPolicyScreenUIState(
    val lastUpdateDate: String = "",
    val policySections: List<PolicySectionUIState> = emptyList(),
)
data class PolicySectionUIState(
    val title: String,
    val content: String
)

fun PolicySection.toUIState(): PolicySectionUIState{
    return PolicySectionUIState(
        title = this.title,
        content = this.content
    )
}
