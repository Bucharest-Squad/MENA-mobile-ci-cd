package net.thechance.mena.faith.presentation.feature.quran.downloadedSur

import androidx.lifecycle.viewModelScope
import mena.faith_presentation.generated.resources.Res
import mena.faith_presentation.generated.resources.ic_al_kahf
import mena.faith_presentation.generated.resources.surah_deleted_successfully
import net.thechance.mena.faith.domain.repository.QuranRepository
import net.thechance.mena.faith.presentation.base.BaseViewModel
import net.thechance.mena.faith.presentation.base.snackbar.SnackBarState
import net.thechance.mena.faith.presentation.base.snackbar.SnackbarHandler

class DownloadedSurViewModel(
    private val quranRepository: QuranRepository,
    snackBarHandler: SnackbarHandler,
) : BaseViewModel<DownloadedSurUiState, DownloadedSurEffect>(
    initialState = DownloadedSurUiState(),
    snackbarHandler = snackBarHandler
), DownloadedSurInteractionListener {

    init {
        loadDownloadedSur()
    }

    private fun loadDownloadedSur() {
        tryToExecute(
            execute = {
                val surahs = quranRepository.getSur()
                surahs.map { surah ->
                    DownloadedSurUiState.SurahDetailsUiState(
                        id = surah.id,
                        arabicNameImg =  Res.drawable.ic_al_kahf,
                        surahName = surah.name,
                        downloadedReciters = getDownloadedRecitersNames(surah.id)
                    )
                }
            },
            onSuccess = { surahUiList ->
                updateState { it.copy(surDetails = surahUiList) }
            }
        )
    }

    private suspend fun getDownloadedRecitersNames(surahId: Int): List<String> {
        val reciters = quranRepository.getReciters()
        return reciters.filter { reciter ->
            quranRepository.isSurahAudioCached(surahId, reciter.id)
        }.map { it.name }
    }

    override fun onReciterSettingsClick() {
        val surahId = uiState.value.selectedSurahForDelete
            ?: uiState.value.surDetails.firstOrNull()?.id
            ?: 1

        sendEffect(DownloadedSurEffect.NavigateToRecitersScreen(surahId))
    }

    override fun onDownloadedSurahClick(surahId: Int) =
        sendEffect(DownloadedSurEffect.NavigateToDownloadedSurahReciterScreen(surahId))

    override fun onBackClick() {
        sendEffect(DownloadedSurEffect.NavigateBack)
    }

    override fun onDeleteSurahClick(surahId: Int) {
        updateState {
            it.copy(
                selectedSurahForDelete = surahId,
                showDeleteConfirmationDialog = true,
            )
        }
    }

    override fun onDismissDeleteConfirmationDialog() {
        updateState { it.copy(selectedSurahForDelete = null, showDeleteConfirmationDialog = false) }
    }

    override fun onConfirmDeleteDownloadedSurahClick() {
        val surahId = uiState.value.selectedSurahForDelete
        tryToExecute(
            execute = {
                surahId?.let {
                    quranRepository.deleteSurahWithSpecificReciter(surahId)
                }
            },
            onSuccess = {
                onDeleteSurahSuccess()
            }
        )
    }


    private fun onDeleteSurahSuccess() {
        updateState { state ->
            val newSurDetails =
                state.surDetails - state.surDetails.first { it.id == state.selectedSurahForDelete }

            state.copy(
                surDetails = newSurDetails,
                selectedSurahForDelete = null,
                showDeleteConfirmationDialog = false
            )
        }

        showSuccessSnackBar()
    }


    private fun showSuccessSnackBar() = snackbarHandler.showSnackBar(
        message = Res.string.surah_deleted_successfully,
        status = SnackBarState.Status.Success,
        scope = viewModelScope,
    )
}
