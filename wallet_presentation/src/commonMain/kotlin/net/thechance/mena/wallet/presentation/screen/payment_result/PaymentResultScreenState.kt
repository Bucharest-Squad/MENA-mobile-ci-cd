package net.thechance.mena.wallet.presentation.screen.payment_result

import net.thechance.mena.wallet.presentation.model.SubmissionStatus

data class PaymentResultScreenState(
    val paymentStatus: SubmissionStatus = SubmissionStatus.SUCCESS,
    val isLoading: Boolean = false,
    val tryAgainAttempts: Int = 0,
    val isTryAgainEnabled: Boolean = true,
    val isCloseEnabled: Boolean = true
)