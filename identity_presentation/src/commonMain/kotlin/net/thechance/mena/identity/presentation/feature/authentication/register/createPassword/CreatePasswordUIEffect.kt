package net.thechance.mena.identity.presentation.feature.authentication.register.createPassword

import net.thechance.mena.identity.presentation.feature.authentication.register.shared.RegisterUIState
import org.jetbrains.compose.resources.StringResource

sealed interface CreatePasswordUIEffect {
    data class NavigateToDatePicker(val registerUIState: RegisterUIState) : CreatePasswordUIEffect

    data class ShowSnackBarError(val errorStringResource: StringResource) : CreatePasswordUIEffect
}