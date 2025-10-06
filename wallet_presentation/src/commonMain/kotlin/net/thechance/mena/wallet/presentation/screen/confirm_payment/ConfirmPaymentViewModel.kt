package net.thechance.mena.wallet.presentation.screen.confirm_payment

import net.thechance.mena.wallet.presentation.base.BaseViewModel
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Provided

@KoinViewModel
class ConfirmPaymentViewModel(
    @Provided private val receiverId: String,
    @Provided private val amount: Double,
) : BaseViewModel<ConfirmPaymentScreenState, ConfirmPaymentEffect>(
    ConfirmPaymentScreenState()
), ConfirmPaymentInteractionListener {

    init {
        println("----------------------------")
        println(amount)
        println(receiverId)
        println("----------------------------")
    }

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