package net.thechance.mena.wallet.presentation.screen.confirm_payment

import net.thechance.mena.wallet.domain.model.PaymentConfirmation

fun PaymentConfirmation.toUi(amount: Double) = ConfirmPaymentScreenState.PaymentUiState(
    amount = amount.toString(),
    receiverName = receiverName,
    receiverImage = receiverImg,
    status = status,
    balance = balance.toString()
)