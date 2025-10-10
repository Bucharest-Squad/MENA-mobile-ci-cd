package net.thechance.mena.wallet.presentation.screen.payment_result

import net.thechance.mena.wallet.presentation.base.BaseViewModel

class PaymentResultViewModel() : BaseViewModel<PaymentResultScreenState, PaymentResultEffect>(
    PaymentResultScreenState()
), PaymentResultInteractionListener {
}