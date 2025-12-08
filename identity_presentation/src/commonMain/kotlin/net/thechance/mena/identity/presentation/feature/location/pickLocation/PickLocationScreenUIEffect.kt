package net.thechance.mena.identity.presentation.feature.location.pickLocation

import net.thechance.mena.identity.presentation.screen.addresses.shared.AddressUIState
import org.jetbrains.compose.resources.StringResource

sealed interface PickLocationScreenUIEffect {
    data object NavigateBack : PickLocationScreenUIEffect
    data class NavigateBackWithLocation(val addressModel: AddressUIState) :
        PickLocationScreenUIEffect

    data object NavigateToEnableLocation : PickLocationScreenUIEffect

    data class ShowSnackBarError(val errorStringResource: StringResource) :
        PickLocationScreenUIEffect
}