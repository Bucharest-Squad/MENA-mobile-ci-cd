package net.thechance.mena.faith.presentation.feature.quran.reciter.manageDownloadsReciters

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.first
import net.thechance.mena.faith.domain.model.Reciter
import net.thechance.mena.faith.domain.repository.QuranRepository
import net.thechance.mena.faith.presentation.base.BaseViewModel
import net.thechance.mena.faith.presentation.base.ErrorState
import net.thechance.mena.faith.presentation.base.snackbar.SnackBarState
import net.thechance.mena.faith.presentation.base.snackbar.SnackbarHandler
import net.thechance.mena.faith.presentation.feature.quran.reciter.args.ReciterArgs

class ManageDownloadsRecitersViewModel(
    private val quranRepository: QuranRepository,
    private val surahArgs: ReciterArgs,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    snackBarHandler: SnackbarHandler,
) : BaseViewModel<ManageDownloadsRecitersUiState, ManageDownloadsRecitersEffect>(
    initialState = ManageDownloadsRecitersUiState(
        surahId = surahArgs.surahId,
        isSwipeable = surahArgs.isSwipeToDeleteEnabled,
    ),
    snackbarHandler = snackBarHandler
), ManageDownloadsRecitersListener {

    init {
        getAllReciters()
        updateDefaultReciter()
    }

    override fun onBackClick() = sendEffect(ManageDownloadsRecitersEffect.NavigateBack)

    override fun onSearchClick() = sendEffect(ManageDownloadsRecitersEffect.NavigateToSearch)

    override fun onQueryChange(query: String) {
        updateState { it.copy(query = query) }
        applyLocalSearch(query)
    }

    override fun onClearQueryClick() {
        updateState { it.copy(query = "", reciters = uiState.value.allReciters) }
    }

    private fun applyLocalSearch(query: String) {
        if (query.isBlank()) {
            updateState { it.copy(reciters = it.allReciters) }
            return
        }

        val filtered = uiState.value.allReciters.filter {
            it.name.contains(query, ignoreCase = true)
        }

        updateState { it.copy(reciters = filtered) }
    }

    override fun onSelectReciterClick(reciterId: Int) {
        tryToExecute(
            execute = { quranRepository.saveDefaultReciter(reciterId) },
            onSuccess = { updateSelectedReciter(reciterId) },
            onError = ::handleError
        )
    }

    private fun updateDefaultReciter() {
        tryToExecute(
            execute = { quranRepository.getDefaultReciter() },
            onSuccess = { id -> updateSelectedReciter(id.first()) },
            onError = ::handleError
        )
    }

    private fun updateSelectedReciter(reciterId: Int) {
        updateState { it.copy(selectedReciterId = reciterId) }
    }

    private fun getAllReciters() {
        tryToExecute(
            execute = { quranRepository.getReciters() },
            onSuccess = ::onGetAllRecitersSuccess,
            dispatcher = dispatcher
        )
    }

    private suspend fun onGetAllRecitersSuccess(reciters: List<Reciter>) {
        val surahId = surahArgs.surahId ?: return

        val mapped = reciters.map { reciter ->
            reciter.toUi(
                isDownloaded = quranRepository.isSurahAudioCached(surahId, reciter.id)
            )
        }

        updateState {
            it.copy(
                allReciters = mapped,
                reciters = mapped
            )
        }
    }

    private fun handleError(errorState: ErrorState) =
        snackbarHandler.showSnackBar(
            message = errorState.message,
            status = SnackBarState.Status.Error,
            scope = viewModelScope,
        )


}
