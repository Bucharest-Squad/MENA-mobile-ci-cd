package net.thechance.mena.identity.presentation.feature.authentication.register.phoneEntry

import net.thechance.mena.identity.presentation.screen.register.shared.uiState.RegisterUIState
import org.jetbrains.compose.resources.StringResource

sealed interface RegisterPhoneEntryUIEffect {
    data class NavigateToOTP(val registerUIState: RegisterUIState) : RegisterPhoneEntryUIEffect
    data object NavigateToLogin : RegisterPhoneEntryUIEffect

    data class ShowSnackBarError(val errorStringResource: StringResource) :
        RegisterPhoneEntryUIEffect
}