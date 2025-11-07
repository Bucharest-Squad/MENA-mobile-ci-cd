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
import net.thechance.mena.faith.presentation.feature.quran.surah.args.SurahArgs

class TilawahViewModel(
    private val quranRepository: QuranRepository,
    private val surahArgs: SurahArgs,
    private val downloadManager: DownloadSurahManager,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel<TilawahUiState, TilawahEffect>(TilawahUiState(surahArgs.surahId)),
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
                val remoteUrl = quranRepository.getRemoteSurahSoundUrl(surahId, reciterId)

                val localPath = downloadManager.downloadSurahFile(remoteUrl, surahId, reciterId)

                quranRepository.saveSurahAudioToCache(
                    surahId = surahId,
                    reciterId = reciterId,
                    localPath = localPath
                )
            },
            onSuccess = {
                markReciterAsDownloaded(reciterId)
            },
            dispatcher = dispatcher
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
        tryToExecute(
            execute = {
                val currentReciters = uiState.value.reciters
                currentReciters.map { reciter ->
                    val isDownloaded = quranRepository.isSurahAudioCached(
                        surahId = surahArgs.surahId,
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
