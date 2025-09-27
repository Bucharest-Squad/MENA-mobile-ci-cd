package net.thechance.mena.wallet.presentation.screen.export

import net.thechance.mena.wallet.domain.entity.Transaction
import net.thechance.mena.wallet.presentation.base.SnackBarState
import net.thechance.mena.wallet.presentation.model.FilterStatus
import net.thechance.mena.wallet.presentation.model.FilterType
import net.thechance.mena.wallet.presentation.screen.export.component.CustomToastState

data class ExportTransactionsState(
    val snackBar: SnackBarState = SnackBarState(),
    val toast: CustomToastState = CustomToastState(),
    val isCustomFilterCardSelected: Boolean = false,
    val isAllTransactionsCardSelected: Boolean = false,
    val isDownloadLoading: Boolean = false,
    val isViewAndShareLoading: Boolean = false,
    val isViewAndShareButtonEnabled: Boolean = true,
    val isDownloadButtonEnabled: Boolean = true,
    val hasShownEmptyFileToast: Boolean = false,
    val noInternetConnection: Boolean = false,
    val selectedTransactionsTypes: Set<FilterType>? = null,
    val selectedTransactionsStatus: FilterStatus = FilterStatus.ALL,
    val startDate: String? = "",
    val endDate: String? = "",
)

fun FilterType.toDomain(): Transaction.Type {
    return when (this) {
        FilterType.SENT -> Transaction.Type.SENT
        FilterType.RECEIVED -> Transaction.Type.RECEIVED
        FilterType.ONLINE_PURCHASE -> Transaction.Type.ONLINE_PURCHASE
    }
}

fun FilterStatus.toDomain(): Transaction.Status? {
    return when (this) {
        FilterStatus.ALL -> null
        FilterStatus.FAILED -> Transaction.Status.FAIL
        FilterStatus.SUCCESS -> Transaction.Status.SUCCESS
    }
}