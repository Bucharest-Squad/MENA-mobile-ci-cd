package net.thechance.mena.faith.presentation.feature.mosque

internal sealed interface NearbyMosquesEffect {
    data object NavigateToUserLocation : NearbyMosquesEffect
    data object NavigateToAddMosque : NearbyMosquesEffect
    data object NavigateBack : NearbyMosquesEffect
    data class NavigateToGoogleMaps(val coordinate: Coordinate) : NearbyMosquesEffect
}
