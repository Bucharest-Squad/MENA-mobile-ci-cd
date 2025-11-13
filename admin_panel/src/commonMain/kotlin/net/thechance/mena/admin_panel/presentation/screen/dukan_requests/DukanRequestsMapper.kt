package net.thechance.mena.admin_panel.presentation.screen.dukan_requests

import net.thechance.mena.admin_panel.domain.entity.dukan.Dukan
import net.thechance.mena.admin_panel.domain.model.DukansSortType
import net.thechance.mena.admin_panel.domain.model.SortDirection
import net.thechance.mena.admin_panel.presentation.utils.format
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
fun Dukan.toUIState(): DukanRequestsScreenState.DukanItem {
    return DukanRequestsScreenState.DukanItem(
        id = id.toString(),
        name = name,
        imageUrl = imageUrl,
        address = address,
        coordinates = DukanRequestsScreenState.DukanItem.CoordinatesUiState(
            latitude = latitude,
            longitude = longitude
        ),
        date = createdAt.format("dd-MM-yyyy"),
    )
}

fun DukanRequestsScreenState.SortType.toEntity(): DukansSortType? = when (this) {
    DukanRequestsScreenState.SortType.DUKAN_NAME -> DukansSortType.NAME
    DukanRequestsScreenState.SortType.DATE -> DukansSortType.CREATED_AT
}

fun DukanRequestsScreenState.SortDirection.toEntity(): SortDirection? = when (this) {
    DukanRequestsScreenState.SortDirection.ASC -> SortDirection.ASC
    DukanRequestsScreenState.SortDirection.DESC -> SortDirection.DESC
}
