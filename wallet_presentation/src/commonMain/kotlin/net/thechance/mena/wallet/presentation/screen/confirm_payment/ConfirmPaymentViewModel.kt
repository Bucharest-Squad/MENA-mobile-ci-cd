package net.thechance.mena.wallet.presentation.screen.confirm_payment

import net.thechance.mena.wallet.presentation.base.BaseViewModel

class ConfirmPaymentViewModel() : BaseViewModel<ConfirmPaymentScreenState, ConfirmPaymentEffect>(
    ConfirmPaymentScreenState()), ConfirmPaymentInteractionListener {
    override fun onBackButtonClicked() {
        sendEffect(ConfirmPaymentEffect.NavigateBack)
    }

    override fun onPayButtonClicked() {
        updateState { it.copy(isPayBtnLoading = true) }
    }

    override fun onRefresh() {
        TODO("Not yet implemented")
    }

}