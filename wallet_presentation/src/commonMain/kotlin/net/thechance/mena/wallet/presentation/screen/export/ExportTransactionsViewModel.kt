package net.thechance.mena.wallet.presentation.screen.export

import io.github.vinceglb.filekit.FileKit
import io.github.vinceglb.filekit.dialogs.openFileSaver
import io.github.vinceglb.filekit.write
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import mena.wallet_presentation.generated.resources.Res
import mena.wallet_presentation.generated.resources.download_complete
import mena.wallet_presentation.generated.resources.download_failed
import mena.wallet_presentation.generated.resources.download_success
import mena.wallet_presentation.generated.resources.downloading_started
import mena.wallet_presentation.generated.resources.error
import mena.wallet_presentation.generated.resources.error_failed_view
import mena.wallet_presentation.generated.resources.error_no_transactions
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
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

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
    }

    override fun onStatusSelected(status: FilterStatus) {
        updateState { oldState ->
            oldState.copy(
                selectedTransactionsStatus = status
            )
        }
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
            oldState.copy(endDate = "2025/09/27")
        }
    }


    override fun onViewAndShareClicked() {
        tryToExecute(
            onStart = ::onViewAndShareStart,
            callee = ::generateTransactionsFile,
            onSuccess = { pdfBytes ->
                onViewAndShareSuccess(pdfBytes)
            },
            onError = { error ->
                onViewAndShareError(error)
            },
            dispatcher = ioDispatcher
        )
    }

    @OptIn(ExperimentalTime::class)
    override fun onDownloadClicked() {
        tryToExecute(
            onStart = ::onDownloadStart,
            callee = ::generateTransactionsFile,
            onSuccess = { pdfBytes ->
                saveFile(pdfBytes)
            },
            onError = { error ->
                handleDownloadError(error)
            },
            dispatcher = ioDispatcher
        )
    }

    private fun onViewAndShareStart() {
        updateState { oldState ->
            oldState.copy(
                isViewAndShareLoading = true,
                isDownloadButtonEnabled = false
            )
        }
    }

    private suspend fun onViewAndShareSuccess(pdfBytes: ByteArray) {
        updateState { oldState ->
            oldState.copy(
                isViewAndShareLoading = false,
                isDownloadButtonEnabled = true
            )
        }

        if (pdfBytes.isEmptyFile()) {
            showToast(
                messageRes = Res.string.error_no_transactions
            )
            updateState { oldState ->
                oldState.copy(
                    isViewAndShareLoading = false,
                    isDownloadButtonEnabled = true
                )
            }
        } else {
            sendEffect(ExportTransactionsEffect.NavigateToViewFileScreen)
        }
    }

    private suspend fun onViewAndShareError(error: Throwable) {
        updateState { oldState ->
            oldState.copy(
                isViewAndShareLoading = false,
                isDownloadButtonEnabled = true
            )
        }
        when (error) {
            is NoInternetException -> {
                updateState { oldState ->
                    oldState.copy(
                        noInternetConnection = true
                    )
                }
            }

            else -> {
                showSnackBar(
                    titleRes = Res.string.error,
                    messageRes = Res.string.error_failed_view,
                    isSuccess = false
                )
            }
        }
    }

    private suspend fun onDownloadStart() {
        updateState { oldState ->
            oldState.copy(
                isDownloadLoading = true,
                isViewAndShareButtonEnabled = false
            )
        }
        showToast(messageRes = Res.string.downloading_started)
    }

    @OptIn(ExperimentalTime::class)
    private suspend fun generateTransactionsFile(): ByteArray {
        return if (currentState.isCustomFilterCardSelected) {
            val formatter = LocalDate.Format {
                year(); char('/'); monthNumber(); char('/'); dayOfMonth()
            }
            val startDateTime: LocalDateTime? =
                currentState.startDate.toStartOfDayLocalDateTime(formatter)

            val endDateTime: LocalDateTime = currentState.endDate
                .toStartOfDayLocalDateTime(formatter)
                ?: Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

            exportTransactionsRepository.getFilteredTransactionsFile(
                type = currentState.selectedTransactionsTypes?.map { it.toDomain() }
                    ?.toSet(),
                status = currentState.selectedTransactionsStatus.toDomain(),
                startDate = startDateTime,
                endDate = endDateTime
            )
        } else {
            exportTransactionsRepository.getFilteredTransactionsFile()
        }
    }

    private suspend fun handleDownloadError(error: Throwable) {
        updateState { oldState ->
            oldState.copy(
                isDownloadLoading = false,
                isViewAndShareButtonEnabled = true
            )
        }
        when (error) {
            is NoInternetException -> {
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
    }

    private suspend fun saveFile(pdfBytes: ByteArray) {
        if (pdfBytes.isEmptyFile()) {
            showToast(
                messageRes = Res.string.error_no_transactions
            )
            resetDownloadState()
        } else {
            try {
                val file = FileKit.openFileSaver(
                    suggestedName = "transaction",
                    extension = "pdf"
                )
                file?.write(pdfBytes)

                resetDownloadState()
                showSnackBar(
                    titleRes = Res.string.download_complete,
                    messageRes = Res.string.download_success,
                    isSuccess = true
                )

            } catch (error: Exception) {
                resetDownloadState()
                when (error) {
                    is NoInternetException -> {
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


            }
        }

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
        autoHideSnackBar(durationMillis)
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
        autoHideToast(durationMillis)
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

    private fun resetDownloadState() {
        updateState { oldState ->
            oldState.copy(
                isDownloadLoading = false,
                isViewAndShareButtonEnabled = true
            )
        }
    }

    private suspend fun autoHideSnackBar(durationMillis: Long) {
        delay(durationMillis)
        hideSnackBar()
    }

    private suspend fun autoHideToast(durationMillis: Long) {
        delay(durationMillis)
        hideToast()
    }

    @OptIn(ExperimentalTime::class)
    private fun String?.toStartOfDayLocalDateTime(formatter: DateTimeFormat<LocalDate>): LocalDateTime? {
        return this
            ?.takeIf { it.isNotEmpty() }
            ?.let { LocalDate.parse(it, formatter) }
            ?.atStartOfDayIn(TimeZone.currentSystemDefault())
            ?.toLocalDateTime(TimeZone.currentSystemDefault())
    }

    private fun ByteArray.isEmptyFile(): Boolean = this.isEmpty()

}