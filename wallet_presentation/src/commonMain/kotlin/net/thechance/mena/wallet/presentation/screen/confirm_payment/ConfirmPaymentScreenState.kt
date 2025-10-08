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
        val receiverImage: String? = "https://media.istockphoto.com/id/469738422/photo/large-boulders-on-lake-shore-at-sunset-minnesota-usa.jpg?s=612x612&w=0&k=20&c=4FzViDygZ8CgixTqt3VOudLJUP8uoSeh2UlD_qHYkAw=",
        val status: TransactionStatus = TransactionStatus.SUCCESS,
        val balance: String = "1,230.25"
    )
}