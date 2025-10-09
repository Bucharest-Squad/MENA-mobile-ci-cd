package net.thechance.mena.wallet.presentation.screen.confirm_payment

import net.thechance.mena.wallet.domain.model.PaymentConfirmation
import net.thechance.mena.wallet.presentation.utils.formatAmount

fun PaymentConfirmation.toUi(amount: Double) = ConfirmPaymentScreenState.PaymentUiState(
    amount = formatAmount(amount),
    receiverName = receiverName,
    receiverImage = receiverImg,
    status = status,
    balance = formatAmount(balance)
)