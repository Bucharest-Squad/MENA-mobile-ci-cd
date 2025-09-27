package net.thechance.mena.wallet.presentation.screen.export

import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.dialogs.openFileSaver
import io.github.vinceglb.filekit.write
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import mena.wallet_presentation.generated.resources.Res
import mena.wallet_presentation.generated.resources.download_complete
import mena.wallet_presentation.generated.resources.download_failed
import mena.wallet_presentation.generated.resources.download_success
import mena.wallet_presentation.generated.resources.downloading_started
import mena.wallet_presentation.generated.resources.something_went_wrong
import net.thechance.mena.wallet.domain.exceptions.NoInternetException
import net.thechance.mena.wallet.domain.repository.ExportTransactionsRepository
import net.thechance.mena.wallet.presentation.base.BaseViewModel
import net.thechance.mena.wallet.presentation.base.SnackBarState
import net.thechance.mena.wallet.presentation.model.FilterStatus
import net.thechance.mena.wallet.presentation.model.FilterType
import net.thechance.mena.wallet.presentation.screen.export.component.CustomToastState
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
                isAllTransactionsCardSelected = true,
                isCustomFilterCardSelected = false
            )
        }
    }

    override fun onCustomFilteringClicked() {
        updateState { oldState ->
            oldState.copy(
                isCustomFilterCardSelected = true,
                isAllTransactionsCardSelected = false

            )
        }
    }

    override fun onTypeSelected(type: FilterType) {
        updateState { oldState ->
            val current = oldState.selectedTransactionsTypes ?: emptySet()
            val newSet = if (current.contains(type)) current - type else current + type
            oldState.copy(selectedTransactionsTypes = newSet)
        }
        println("type $type")
    }

    override fun onStatusSelected(status: FilterStatus) {
        updateState { oldState ->
            oldState.copy(
                selectedTransactionsStatus = status
            )
        }
        println("status $status")
    }

    override fun onFromDateClicked() {
        //TODO Here the DatePicker opens and stores the result in state.startDate
        updateState { oldState ->
            oldState.copy(startDate = "2025/09/01")
        }
    }

    override fun onToDateClicked() {
        //TODO Here the DatePicker opens and stores the result in state.endDate

        updateState { oldState ->
            oldState.copy(startDate = "2025/09/27")
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
                        isDownloadLoading = true,
                        isViewAndShearEnabled = false
                    )
                }
                showToast(messageRes = Res.string.downloading_started)
            },
            callee = {
                if (currentState.isCustomFilterCardSelected) {
                    exportTransactionsRepository.getAllTransactionsFile()
                } else {
                    exportTransactionsRepository.getAllTransactionsFile()
                }
            },
            onSuccess = { pdfBytes ->
                //TODO save file into device
                try {
                    val file = FileKit.openFileSaver(
                        suggestedName = "transaction",
                        extension = "pdf"
                    )
                    if (file != null) {
                        file.write(pdfBytes)
                    }

                    updateState { oldState ->
                        oldState.copy(
                            isDownloadLoading = false,
                            isViewAndShearEnabled = true
                        )
                    }
                    showSnackBar(
                        titleRes = Res.string.download_complete,
                        messageRes = Res.string.download_success,
                        isSuccess = true
                    )

                } catch (error: Exception) {
                    println(error)
                    updateState { oldState ->
                        oldState.copy(
                            isDownloadLoading = false,
                            isViewAndShearEnabled = true
                        )
                    }
                    when (error) {
                        is NoInternetException -> {

                        }

                        else -> {
                            showSnackBar(
                                titleRes = Res.string.download_failed,
                                messageRes = Res.string.something_went_wrong,
                                isSuccess = false
                            )
                        }
                    }


                }
            },
            onError = { error ->
                updateState { oldState ->
                    oldState.copy(
                        isDownloadLoading = false,
                        isViewAndShearEnabled = true
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

                    else -> {
                        showSnackBar(
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

    private suspend fun showSnackBar(
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

    private suspend fun showToast(
        messageRes: StringResource,
        durationMillis: Long = 2000L
    ) {
        updateState { oldState ->
            oldState.copy(
                toast = CustomToastState(
                    isVisible = true,
                    messageRes = messageRes
                )
            )
        }
        delay(durationMillis)
        hideToast()
    }

    private fun hideToast() {
        updateState { oldState ->
            oldState.copy(
                toast = oldState.toast.copy(
                    isVisible = false
                )
            )
        }
    }
}