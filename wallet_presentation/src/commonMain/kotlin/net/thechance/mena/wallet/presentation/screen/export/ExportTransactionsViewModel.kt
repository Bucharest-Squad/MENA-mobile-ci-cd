package net.thechance.mena.wallet.presentation.screen.export

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import net.thechance.mena.wallet.domain.repository.ExportTransactionsRepository
import net.thechance.mena.wallet.presentation.base.BaseViewModel
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Provided

@KoinViewModel
class ExportTransactionsViewModel(
    @Provided private val exportTransactionsRepository: ExportTransactionsRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel<ExportTransactionsState, ExportTransactionsEffect>(
    ExportTransactionsState()
), ExportTransactionsListener {
    override fun onBackClicked() {
        sendEffect(ExportTransactionsEffect.NavigateBack)
    }

    override fun onAllTransactionsClicked() {
        tryToExecute(
            onStart = {},
            callee = { exportTransactionsRepository.getAllTransactions() },
            onSuccess = {},
            onError = {},
            onFinish = {},
            dispatcher = ioDispatcher
        )
        updateState { oldState ->
            oldState.copy(
                isAllTransactionsCardSelected = true
            )
        }
    }

    override fun onCustomFilteringClicked() {
        tryToExecute(
            onStart = {},
            callee = { exportTransactionsRepository.getFilteredTransactions() },
            onSuccess = {},
            onError = {},
            onFinish = {},
            dispatcher = ioDispatcher
        )
        updateState { oldState ->
            oldState.copy(
                isCustomFilterCardSelected = true
            )
        }
    }

    override fun onViewAndShareClicked() {
        TODO("Not yet implemented")
    }

    override fun onDownloadClicked() {
        TODO("Not yet implemented")
    }
}