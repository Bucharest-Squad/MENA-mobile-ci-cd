package net.thechance.mena.faith.presentation.feature.mosque

sealed interface NearbyMosquesEffect {
    data object NavigateToUserLocation : NearbyMosquesEffect
    data object NavigateToAddMosque : NearbyMosquesEffect
    data object NavigateBack : NearbyMosquesEffect
}