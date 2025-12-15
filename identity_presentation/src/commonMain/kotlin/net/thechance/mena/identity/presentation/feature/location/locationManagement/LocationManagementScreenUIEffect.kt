package net.thechance.mena.identity.presentation.feature.location.locationManagement

import net.thechance.mena.identity.presentation.feature.location.shared.AddressUIState
import org.jetbrains.compose.resources.StringResource

sealed interface LocationManagementScreenUIEffect {
    data class NavigateBack(
        val errorStringResource: StringResource? = null,
        val successStringResource: StringResource? = null
    ) : LocationManagementScreenUIEffect

    data class NavigateToMap(
        val addressModel: AddressUIState? = null,
        val onUpdateLocation: (AddressUIState) -> Unit
    ) : LocationManagementScreenUIEffect
}