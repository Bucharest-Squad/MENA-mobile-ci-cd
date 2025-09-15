package net.thechance.mena.wallet.presentation.screen.wallet

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import net.thechance.mena.wallet.domain.repository.BalanceRepository
import net.thechance.mena.wallet.presentation.base.BaseViewModel

class WalletViewModel(
    private val balanceRepository: BalanceRepository
): BaseViewModel<WalletUiState, WalletUiEvent>(WalletUiState()), WalletInteractionListener {

    init {
        getBalance()
    }

    private fun getBalance() {
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

    override fun onBackClick() {
        sendEffect(WalletUiEvent.NavigateBack)
    }
}