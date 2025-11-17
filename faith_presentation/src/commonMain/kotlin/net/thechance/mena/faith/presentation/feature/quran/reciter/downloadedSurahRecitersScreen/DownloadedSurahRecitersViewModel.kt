package net.thechance.mena.faith.presentation.feature.quran.reciter.downloadedSurahRecitersScreen

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.first
import net.thechance.mena.faith.domain.model.Reciter
import net.thechance.mena.faith.domain.repository.QuranRepository
import net.thechance.mena.faith.domain.service.DownloadSurahManager
import net.thechance.mena.faith.presentation.base.BaseViewModel
import net.thechance.mena.faith.presentation.base.ErrorState
import net.thechance.mena.faith.presentation.feature.quran.reciter.downloadedSurahRecitersScreen.args.TilawahSurahArgs

class DownloadedSurahRecitersViewModel(
    private val quranRepository: QuranRepository,
    private val surahArgs: TilawahSurahArgs,
    private val downloadManager: DownloadSurahManager,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel<DownloadedSurahRecitersUiState, DownloadedSurahRecitersScreenEffect>(
    initialState = DownloadedSurahRecitersUiState(
        surahId = surahArgs.surahId,
        isSwipeable = surahArgs.isSwipeToDeleteEnabled,
    ),
), DownloadedSurahRecitersListener {

    init {
        getAllReciters()
        updateDefaultReciter()
    }

    private fun updateDefaultReciter() {
        tryToExecute(
            execute = { quranRepository.getDefaultReciter() },
            onSuccess = { reciterId ->
                updateSelectedReciter(reciterId.first())
            },
            onError = ::handleError
        )
    }

    override fun onQueryChange(newQuery: String) {
        updateState { state ->
            val filtered = if (newQuery.isBlank()) {
                state.allReciters
            } else {
                state.allReciters.filter {
                    it.name.contains(newQuery, ignoreCase = true)
                }
            }

            state.copy(
                query = newQuery,
                reciters = filtered
            )
        }
    }

    override fun onClearQueryClick() {
        updateState { state ->
            state.copy(
                query = "",
                reciters = state.allReciters
            )
        }
    }

    override fun onBackClick() =
        sendEffect(DownloadedSurahRecitersScreenEffect.NavigateBack)

    override fun onSearchClick() =
        sendEffect(DownloadedSurahRecitersScreenEffect.NavigateToSearch)

    override fun onDownloadClick(reciterId: Int) {
        tryToExecute(
            execute = {
                surahArgs.surahId?.let {
                    downloadAndCacheSurah(it, reciterId)
                }
            },
            onSuccess = { onDownloadComplete(reciterId) },
            onError = ::handleError,
            dispatcher = dispatcher
        )
    }

    private suspend fun downloadAndCacheSurah(surahId: Int, reciterId: Int) {
        val remoteUrl = quranRepository.getRemoteSurahSoundUrl(
            surahId = surahId,
            reciterId = reciterId
        )

        val localPath = downloadManager.downloadSurahFile(
            url = remoteUrl,
            surahId = surahId,
            reciterId = reciterId
        )

        quranRepository.saveSurahAudioToCache(
            surahId = surahId,
            reciterId = reciterId,
            localPath = localPath
        )
    }

    override fun onSelectReciterClick(reciterId: Int) {
        tryToExecute(
            execute = { quranRepository.saveDefaultReciter(reciterId) },
            onSuccess = { updateSelectedReciter(reciterId) },
            onError = ::handleError
        )
    }

    private fun getAllReciters() {
        tryToExecute(
            execute = { quranRepository.getReciters() },
            onSuccess = ::getAllRecitersSuccessfully,
            dispatcher = dispatcher
        )
    }

    private suspend fun getAllRecitersSuccessfully(reciters: List<Reciter>) {
        val surahId = surahArgs.surahId ?: return

        val recitersUi = reciters.map { reciter ->
            reciter.toUi(
                isDownloaded = quranRepository.isSurahAudioCached(
                    surahId,
                    reciter.id
                )
            )
        }

        updateState { state ->
            state.copy(
                allReciters = recitersUi,
                reciters = recitersUi
            )
        }
    }

    private fun updateSelectedReciter(reciterId: Int) {
        updateState { state ->
            state.copy(selectedReciterId = reciterId)
        }
    }

    private suspend fun onDownloadComplete(reciterId: Int) {
        val surahId = surahArgs.surahId ?: return

        val isDownloaded = quranRepository.isSurahAudioCached(surahId, reciterId)

        updateState { state ->
            state.copy(
                reciters = state.reciters.map {
                    if (it.id == reciterId) it.copy(isDownloaded = isDownloaded)
                    else it
                },
                allReciters = state.allReciters.map {
                    if (it.id == reciterId) it.copy(isDownloaded = isDownloaded)
                    else it
                }
            )
        }
    }

    private fun handleError(errorState: ErrorState) {
        println("Error: $errorState")
    }
}

