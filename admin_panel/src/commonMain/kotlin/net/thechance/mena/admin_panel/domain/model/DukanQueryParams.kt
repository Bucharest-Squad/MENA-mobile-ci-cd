package net.thechance.mena.admin_panel.domain.model

data class DukanQueryParams(
    val sortType: SortDukanType?,
    val sortDirection: SortDukanDirection?,
    val page: Int,
    val size: Int
)

enum class SortDukanDirection() {
    ASC,
    DESC
}

enum class SortDukanType() {
    DUKAN_NAME,
    DATE
}
