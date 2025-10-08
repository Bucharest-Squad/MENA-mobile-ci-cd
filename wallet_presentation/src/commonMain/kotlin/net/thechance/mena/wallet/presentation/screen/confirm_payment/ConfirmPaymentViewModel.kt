package net.thechance.mena.wallet.presentation.screen.confirm_payment

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import net.thechance.mena.wallet.domain.model.PaymentConfirmation
import net.thechance.mena.wallet.domain.repository.PaymentRepository
import net.thechance.mena.wallet.presentation.base.BaseViewModel
import net.thechance.mena.wallet.presentation.base.ErrorState
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Provided

@KoinViewModel
class ConfirmPaymentViewModel(
    @Provided private val receiverId: String,
    @Provided private val amount: Double,
    @Provided private val paymentRepository: PaymentRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) : BaseViewModel<ConfirmPaymentScreenState, ConfirmPaymentEffect>(
    ConfirmPaymentScreenState()
), ConfirmPaymentInteractionListener {

    init {
        getPaymentConfirmation()
    }

    private fun getPaymentConfirmation(){
        tryToExecute(
            callee = {
                paymentRepository.getPaymentConfirmation(
                    receiverId = receiverId,
                    amount = amount
                )
            },
            onSuccess = ::onGetPaymentConfirmationSuccess,
            onError = ::onGetPaymentConfirmationError,
            onStart = ::onGetPaymentConfirmationStart,
            dispatcher = ioDispatcher
        )
    }

    private fun onGetPaymentConfirmationSuccess(paymentConfirmation: PaymentConfirmation){
        updateState {
            it.copy(isLoading = false, paymentUiState = paymentConfirmation.toUi(amount))
        }
    }

    private fun onGetPaymentConfirmationError(errorState: ErrorState){
        updateState { it.copy(isLoading = false, errorState = errorState) }
    }

    private fun onGetPaymentConfirmationStart(){
        updateState { it.copy(isLoading = true) }
    }

    override fun onBackButtonClicked() {
        sendEffect(ConfirmPaymentEffect.NavigateBack)
    }

    override fun onPayButtonClicked() {
        updateState { it.copy(isPayBtnLoading = true) }
    }

    override fun onRefresh() {
        updateState { it.copy(isLoading = true, errorState = null) }
        getPaymentConfirmation()
    }
}