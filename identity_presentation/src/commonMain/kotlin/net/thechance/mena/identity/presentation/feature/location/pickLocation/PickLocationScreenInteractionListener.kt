package net.thechance.mena.identity.presentation.feature.location.pickLocation

import net.thechance.mena.identity.presentation.core.base.BaseInteractionListener
import net.thechance.mena.identity.presentation.feature.location.shared.CoordinatesUiState

interface PickLocationScreenInteractionListener : BaseInteractionListener {
    fun onClickMap(coordinates: CoordinatesUiState)
    fun onMoveCamera(coordinates: CoordinatesUiState)
    fun onClickGps()
    fun onClickConfirm()
    fun onClickBack()
}


