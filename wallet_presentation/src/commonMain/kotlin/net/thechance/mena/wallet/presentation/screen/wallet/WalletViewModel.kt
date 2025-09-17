package net.thechance.mena.wallet.presentation.screen.wallet

import net.thechance.mena.wallet.domain.repository.BalanceRepository
import net.thechance.mena.wallet.presentation.base.BaseViewModel
import net.thechance.mena.wallet.presentation.base.UiState

class WalletViewModel(
    private val balanceRepository: BalanceRepository
): BaseViewModel<WalletScreenState, WalletEffect>(WalletScreenState()), WalletInteractionListener {

    init {
        getBalance()
    }

    private fun getBalance() {
        //TODO: get real user id
        tryToExecute(
            onStart = ::onGetBalanceStart,
            callee = { balanceRepository.getBalance(userId = 1) },
            onSuccess = ::onGetBalanceSuccess,
            onError = ::onGetBalanceError
        )
    }

    private fun onGetBalanceStart() {
        updateState { it.copy(balance = UiState.Loading) }
    }

    private fun onGetBalanceSuccess(balance: Double) {
        updateState { it.copy(balance = UiState.Success(balance)) }
    }

    private fun onGetBalanceError(throwable: Throwable) {
        updateState { it.copy(balance = UiState.Error(throwable)) }
    }

    override fun onBackClicked() {
        sendEffect(WalletEffect.NavigateBack)
    }

    override fun onRetryLoadBalanceClicked() {
        getBalance()
    }
}