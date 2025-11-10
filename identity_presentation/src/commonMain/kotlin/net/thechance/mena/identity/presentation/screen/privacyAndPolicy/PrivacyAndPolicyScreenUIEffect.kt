package net.thechance.mena.identity.presentation.screen.privacyAndPolicy

sealed interface PrivacyAndPolicyScreenUIEffect {

    data object NavigateToBack : PrivacyAndPolicyScreenUIEffect
}