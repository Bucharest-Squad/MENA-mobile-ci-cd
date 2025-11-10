package net.thechance.mena.faith.presentation.feature.quran.tilwah

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.first
import net.thechance.mena.faith.domain.model.Reciter
import net.thechance.mena.faith.domain.repository.QuranRepository
import net.thechance.mena.faith.domain.service.DownloadSurahManager
import net.thechance.mena.faith.presentation.base.BaseViewModel
import net.thechance.mena.faith.presentation.base.ErrorState
import net.thechance.mena.faith.presentation.feature.quran.tilwah.component.args.TilawahSurahArgs

class TilawahViewModel(
    private val quranRepository: QuranRepository,
    private val surahArgs: TilawahSurahArgs,
    private val downloadManager: DownloadSurahManager,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel<TilawahUiState, TilawahEffect>(TilawahUiState()),
    TilawahInteractionListener {

    init {
        getAllReciters()
        updateDefaultReciter()
    }

    private fun updateDefaultReciter() {
        tryToExecute(
            execute = { quranRepository.getDefaultReciter() },
            onSuccess = { reciterId -> updateSelectedReciter(reciterId.first()) },
            onError = ::handleError
        )
    }

    override fun onBackClick() = sendEffect(TilawahEffect.NavigateBack)

    override fun onSearchClick() = sendEffect(TilawahEffect.NavigateToSearch)


    override fun onDownloadClick(surahId: Int, reciterId: Int) {
        tryToExecute(
            execute = {
                downloadAndCacheSurah(surahId, reciterId)
            },
            onSuccess = {
                updateState { it.copy(surahId = surahArgs.surahId) }
                markReciterAsDownloaded(reciterId)
            },
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
        updateState { it.copy(surahId = surahArgs.surahId) }
        tryToExecute(
            execute = { quranRepository.getReciters() },
            onSuccess = ::getAllRecitersSuccessfully,
            dispatcher = dispatcher
        )
    }

    private fun updateSelectedReciter(reciterId: Int) {
        updateState { state ->
            state.copy(
                selectedReciterId = reciterId,
            )
        }
    }

    private fun handleError(errorState: ErrorState) {
        println("Error: $errorState")
    }

    private fun getAllRecitersSuccessfully(reciters: List<Reciter>) {
        val recitersUi = reciters.map {
            ReciterUi(
                id = it.id,
                name = it.name,
                recitingType = it.tilawahType,
                isDownloaded = false //TODO NOT IMPLEMENTED YET
            )
        }
        updateState { it.copy(reciters = recitersUi) }
        checkDownloadedSurahs()
    }

    private fun checkDownloadedSurahs() {
        surahArgs.surahId?.let { surahId ->
            tryToExecute(
                execute = {
                    val currentReciters = uiState.value.reciters
                    currentReciters.map { reciter ->
                        val isDownloaded = quranRepository.isSurahAudioCached(
                            surahId = surahId,
                            reciterId = reciter.id
                        )
                        reciter.copy(isDownloaded = isDownloaded)
                    }
                },
                onSuccess = { updatedReciters ->
                    updateState { it.copy(reciters = updatedReciters) }
                },
                dispatcher = dispatcher
            )
        }
    }


    private fun markReciterAsDownloaded(reciterId: Int) {
        updateState { currentState ->
            currentState.copy(
                reciters = currentState.reciters.map { reciter ->
                    if (reciter.id == reciterId) {
                        reciter.copy(isDownloaded = true)
                    } else {
                        reciter
                    }
                }
            )
        }
    }
}
