package net.thechance.mena.wallet.presentation.screen.wallet

import net.thechance.mena.wallet.domain.repository.BalanceRepository
import net.thechance.mena.wallet.presentation.base.BaseViewModel

class WalletViewModel(
    private val balanceRepository: BalanceRepository
): BaseViewModel<WalletUiState, WalletEffect>(WalletUiState()), WalletInteractionListener {

    init {
        getBalance()
    }

    private fun getBalance() {
        //TODO: get real user id
        tryToExecute(
            onStart = ::onGetBalanceStart,
            callee = { balanceRepository.getBalance(userId = 1) },
            onSuccess = ::onGetBalanceSuccess,
            onFinish = ::onGetBalanceFinish
        )
    }

    private fun onGetBalanceStart() {
        updateState { it.copy(isLoading = true) }
    }

    private fun onGetBalanceSuccess(balance: Double) {
        updateState { it.copy(balance = balance) }
    }

    private fun onGetBalanceFinish() {
        updateState { it.copy(isLoading = false) }
    }

    override fun onBackClicked() {
        sendEffect(WalletEffect.NavigateBack)
    }
}