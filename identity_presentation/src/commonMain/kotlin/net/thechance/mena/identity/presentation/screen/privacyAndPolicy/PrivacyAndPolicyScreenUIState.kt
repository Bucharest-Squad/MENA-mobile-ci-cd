package net.thechance.mena.identity.presentation.screen.privacyAndPolicy

data class PrivacyAndPolicyScreenUIState(
    val lastUpdateDate: String = "29/11/2025",
    val policySections: List<PolicySectionUIState> = emptyList(),
) {
    data class PolicySectionUIState(
        val title: String,
        val content: String
    )
}

