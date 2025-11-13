package net.thechance.mena.admin_panel.domain.model

data class DukanQueryParams(
    val searchInput: String?,
    val status: DukanStatus,
    val activationStatus: DukanActivationStatus?,
    val sortType: DukansSortType?,
    val sortDirection: SortDirection?,
    val page: Int,
    val size: Int
) {
    enum class DukanStatus {
        APPROVED,
        REJECTED,
        PENDENING
    }

    enum class DukanActivationStatus {
        ACTIVED,
        DEACTIVATED,
    }

    enum class DukansSortType() {
        NAME,
        CREATED_DATE,
        ACTIVATION_STATUS
    }
}