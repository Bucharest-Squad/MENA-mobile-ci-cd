package net.thechance.mena.faith.presentation.feature.main

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import mena.faith_presentation.generated.resources.Res
import mena.faith_presentation.generated.resources.error_unknown
import net.thechance.mena.faith.domain.entity.Location
import net.thechance.mena.faith.domain.repository.PrayerTimeRepository
import net.thechance.mena.faith.domain.repository.QuranRepository
import net.thechance.mena.faith.presentation.base.BaseViewModel
import net.thechance.mena.faith.presentation.base.SnackBarState
import net.thechance.mena.faith.presentation.feature.main.mapper.getHijriDate
import net.thechance.mena.faith.presentation.feature.main.mapper.getSunriseTime
import net.thechance.mena.faith.presentation.feature.main.mapper.mapToPrayerTimesUiState
import net.thechance.mena.faith.presentation.feature.main.mapper.toTilawahUiState
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class MainViewModel(
    private val quranRepository: QuranRepository,
    private val prayerTimeRepository: PrayerTimeRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : BaseViewModel<MainScreenState, MainScreenEffect>(
    initialState = MainScreenState()
), MainInteractionListener {

    init {
        loadPrayerTimes()
        loadLastAyahForTilawah()
    }

    @OptIn(ExperimentalTime::class)
    private fun loadPrayerTimes() {
        tryToExecute(
            execute = {
                val defaultLocation = Location(latitude = 30.0444, longitude = 31.2357)
                val now = Clock.System.now()
                prayerTimeRepository.getPrayerTimes(
                    date = now,
                    location = defaultLocation
                )
            },
            onStart = { updateState { it.copy(isLoading = true) } },
            onSuccess = { prayerTimes ->
                val now = Clock.System.now()
                updateState {
                    it.copy(
                        prayerTimes = prayerTimes,
                        prayerTimesUiState = mapToPrayerTimesUiState(prayerTimes, now),
                        hijriDate = getHijriDate(prayerTimes),
                        sunriseTime = getSunriseTime(prayerTimes)
                    )
                }
            },
            onError = { showErrorSnackBar() },
            onFinally = { updateState { it.copy(isLoading = false) } },
            dispatcher = dispatcher
        )
    }

    private fun loadLastAyahForTilawah() {
        tryToExecute(
            execute = { quranRepository.getLastAyahForTilawah() },
            onSuccess = { ayah ->
                updateState {
                    it.copy(tilawahUiState = ayah.toTilawahUiState())
                }
            },
            onError = { showErrorSnackBar() },
            dispatcher = dispatcher
        )
    }

    override fun onContinueTilawahClick(surahId: Int, surahName: String) =
        sendEffect(MainScreenEffect.NavigateToSurah(surahId, surahName))

    override fun onQuranClick() = sendEffect(MainScreenEffect.NavigateToQuran)

    override fun onQiblahClick() = sendEffect(MainScreenEffect.NavigateToQiblah)

    override fun onMosquesClick() = sendEffect(MainScreenEffect.NavigateToMosques)

    private fun showErrorSnackBar() {
        showSnackBar(
            message = Res.string.error_unknown,
            status = SnackBarState.Status.Error
        )
    }
}