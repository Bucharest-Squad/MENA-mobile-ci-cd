package net.thechance.mena.wallet.presentation.screen.confirm_payment

sealed interface ConfirmPaymentEffect {
    data object NavigateBack : ConfirmPaymentEffect
}