package net.thechance.mena.wallet.presentation.screen.confirm_payment

import net.thechance.mena.wallet.domain.model.TransactionStatus
import net.thechance.mena.wallet.presentation.base.ErrorState

data class ConfirmPaymentScreenState(
    val isLoading: Boolean = false,
    val errorState: ErrorState? = null,
    val paymentUiState: PaymentUiState = PaymentUiState(),
    val isPayBtnLoading: Boolean = false
){
    data class PaymentUiState(
        val amount: String = "530,320",
        val receiverName: String = "Ahmed Ali",
        val status: TransactionStatus = TransactionStatus.SUCCESS,
        val balance: String = "1,230.25"
    )
}
