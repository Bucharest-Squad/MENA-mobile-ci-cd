package net.thechance.mena.wallet.presentation.screen.confirm_payment

import net.thechance.mena.wallet.domain.model.PaymentConfirmation

fun PaymentConfirmation.toUi(amount: Double) = ConfirmPaymentScreenState.PaymentUiState(
    amount = formatNumber(amount),
    receiverName = receiverName,
    receiverImage = receiverImg,
    status = status,
    balance = formatNumber(balance)
)
fun formatNumber(number: Double): String {
    val parts = number.toString().split(".")
    val integerPart = parts[0].reversed().chunked(3).joinToString(",").reversed()
    val decimalPart = if (parts.size > 1 && parts[1].toInt() > 1) parts[1] else null

    return if (decimalPart != null && decimalPart.isNotEmpty()) {
        "$integerPart.${decimalPart.trimEnd('0').ifEmpty { "0" }}"
    } else {
        integerPart
    }
}