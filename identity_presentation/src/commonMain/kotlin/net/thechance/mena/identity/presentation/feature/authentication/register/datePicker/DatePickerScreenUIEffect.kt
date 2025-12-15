package net.thechance.mena.identity.presentation.feature.authentication.register.datePicker

import net.thechance.mena.identity.presentation.feature.authentication.register.shared.RegisterUIState

sealed interface DatePickerScreenUIEffect {
    data class NavigateToSelectGender(val registerUIState: RegisterUIState) : DatePickerScreenUIEffect
}