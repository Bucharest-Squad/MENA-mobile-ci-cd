package net.thechance.mena.admin_panel.data.mapper.dukan

import net.thechance.mena.admin_panel.domain.model.DukanQueryParams
import net.thechance.mena.admin_panel.domain.model.SortDirection

fun buildSortQueries(
    property: DukanQueryParams.DukansSortType?,
    direction: SortDirection?
): List<String> {
    val directionStr = convertDirectionToString(direction)
    return when (property) {
        DukanQueryParams.DukansSortType.NAME -> listOf("name,$directionStr")
        DukanQueryParams.DukansSortType.CREATED_AT -> listOf("createdAt,$directionStr")
        DukanQueryParams.DukansSortType.ACTIVATION_STATUS -> listOf("activationStatus,$directionStr")
        else -> listOf("name,$directionStr")
    }
}

private fun convertDirectionToString(direction: SortDirection?): String {
    return when (direction) {
        SortDirection.ASC -> "asc"
        SortDirection.DESC -> "desc"
        null -> "asc"
    }
}