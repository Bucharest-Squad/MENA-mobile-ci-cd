package net.thechance.mena.wallet.presentation.screen.export

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import mena.wallet_presentation.generated.resources.Res
import mena.wallet_presentation.generated.resources.download_complete
import mena.wallet_presentation.generated.resources.download_failed
import mena.wallet_presentation.generated.resources.download_success
import mena.wallet_presentation.generated.resources.something_went_wrong
import net.thechance.mena.wallet.domain.exceptions.NoInternetException
import net.thechance.mena.wallet.domain.exceptions.UnknownException
import net.thechance.mena.wallet.domain.repository.ExportTransactionsRepository
import net.thechance.mena.wallet.presentation.base.BaseViewModel
import net.thechance.mena.wallet.presentation.base.SnackBarState
import org.jetbrains.compose.resources.StringResource
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
        updateState { oldState ->
            oldState.copy(
                isAllTransactionsCardSelected = true
            )
        }
    }

    override fun onCustomFilteringClicked() {
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
        tryToExecute(
            onStart = {
                updateState { oldState ->
                    oldState.copy(
                        isDownloadLoading = true
                    )
                    //TODO Show Toast for 2 seconds
                }
            },
            callee = {
                if (currentState.isCustomFilterCardSelected) {
                    exportTransactionsRepository.getFilteredTransactions(/*TODO take 4 params to apply filter*/)
                } else {
                    exportTransactionsRepository.getAllTransactions()
                }
            },
            onSuccess = {
                //TODO save file into device
                updateState { oldState ->
                    oldState.copy(
                        isDownloadLoading = false
                    )
                }
                ShowSnackBar(
                    titleRes = Res.string.download_complete,
                    messageRes = Res.string.download_success,
                    isSuccess = true
                )

            },
            onError = { error ->
                updateState { oldState ->
                    oldState.copy(
                        isDownloadLoading = false
                    )
                }
                when (error) {
                    is NoInternetException -> {
                        //TODO Show no internet screen state
                        updateState { oldState ->
                            oldState.copy(
                                noInternetConnection = true
                            )
                        }
                    }
                    is UnknownException -> {
                        ShowSnackBar(
                            titleRes = Res.string.download_failed,
                            messageRes = Res.string.something_went_wrong,
                            isSuccess = false
                        )
                    }
                }
            },
            onFinish = {},
            dispatcher = ioDispatcher
        )
    }

    private suspend fun ShowSnackBar(
        titleRes: StringResource,
        messageRes: StringResource,
        isSuccess: Boolean,
        durationMillis: Long = 3000L
    ) {
        updateState { oldState ->
            oldState.copy(
                snackBar = SnackBarState(
                    isVisible = true,
                    titleRes = titleRes,
                    messageRes = messageRes,
                    isSuccess = isSuccess
                )
            )
        }
        delay(durationMillis)
        hideSnackBar()
    }

    private fun hideSnackBar() {
        updateState { oldState ->
            oldState.copy(
                snackBar = oldState.snackBar.copy(
                    isVisible = false
                )
            )
        }
    }
}