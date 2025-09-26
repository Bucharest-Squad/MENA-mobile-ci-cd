package net.thechance.mena.wallet.presentation.screen.export

import net.thechance.mena.wallet.presentation.base.SnackBarState
import net.thechance.mena.wallet.presentation.screen.export.component.CustomToast
import net.thechance.mena.wallet.presentation.screen.export.component.CustomToastState

data class ExportTransactionsState(
    val snackBar: SnackBarState = SnackBarState(),
    val toast: CustomToastState = CustomToastState(),
    val isCustomFilterCardSelected: Boolean = false,
    val isAllTransactionsCardSelected: Boolean = false,
    val isDownloadLoading: Boolean = false,
    val isViewAndShearDisabled: Boolean = true,
    val noInternetConnection: Boolean = false,
)