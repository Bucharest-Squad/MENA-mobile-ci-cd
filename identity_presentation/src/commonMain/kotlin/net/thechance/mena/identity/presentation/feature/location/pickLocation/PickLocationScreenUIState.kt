package net.thechance.mena.identity.presentation.feature.location.pickLocation

import net.thechance.mena.identity.presentation.feature.location.shared.CoordinatesUiState

data class PickLocationScreenUIState(
    val currentLocation: CoordinatesUiState = CoordinatesUiState(),
    val animateToCurrentLocation: Boolean = false,
    val showAnchor: Boolean = false,
    val address: String = "",
    val isLoading: Boolean = false,
    val isConfirmEnabled: Boolean = false,
    val isGpsButtonLoading: Boolean = false,
    val isMainAddress: Boolean = false
)
