package net.thechance.mena.identity.presentation.screen.privacyAndPolicy

import net.thechance.mena.identity.domain.model.PolicySection

data class PrivacyAndPolicyScreenUIState(
    val lastUpdateDate: String = "",
    val policySections: List<PolicySection> = emptyList(),
)

