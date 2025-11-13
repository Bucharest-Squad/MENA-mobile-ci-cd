package net.thechance.mena.admin_panel.presentation.screen.dukan_requests

import net.thechance.mena.admin_panel.domain.entity.dukan.Dukan
import net.thechance.mena.admin_panel.domain.model.SortDukanDirection
import net.thechance.mena.admin_panel.domain.model.SortDukanType
import net.thechance.mena.admin_panel.presentation.utils.format
import kotlin.uuid.ExperimentalUuidApi

fun Dukan.toUIState(): DukanRequestsScreenState.DukanItem {
    return DukanRequestsScreenState.DukanItem(
        id = id,
        name = name,
        imageUrl = imageUrl,
        address = address,
        coordinates = DukanRequestsScreenState.DukanItem.CoordinatesUiState(
            latitude = latitude,
            longitude = longitude
        ),
        date = date.format("dd-MM-yyyy")
    )
}

fun DukanRequestsScreenState.SortType.toEntity(): SortDukanType? = when (this) {
    DukanRequestsScreenState.SortType.DUKAN_NAME -> SortDukanType.DUKAN_NAME
    DukanRequestsScreenState.SortType.DATE -> SortDukanType.DATE
}

fun DukanRequestsScreenState.SortDirection.toEntity(): SortDukanDirection? = when (this) {
    DukanRequestsScreenState.SortDirection.ASC -> SortDukanDirection.ASC
    DukanRequestsScreenState.SortDirection.DESC -> SortDukanDirection.DESC
}
