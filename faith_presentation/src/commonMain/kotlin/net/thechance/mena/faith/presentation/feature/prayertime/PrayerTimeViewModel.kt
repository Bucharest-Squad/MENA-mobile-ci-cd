package net.thechance.mena.faith.presentation.feature.prayertime

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import net.thechance.mena.faith.domain.entity.PrayerName
import net.thechance.mena.faith.domain.entity.PrayerTime
import net.thechance.mena.faith.domain.repository.PrayerTimeRepository
import net.thechance.mena.faith.domain.usecase.GetNextPrayerTimeUseCase
import net.thechance.mena.faith.presentation.base.BaseViewModel
import net.thechance.mena.faith.presentation.utils.extentions.prayerTime.convertHijriToReadableFormat
import net.thechance.mena.faith.presentation.utils.extentions.prayerTime.formatCountdown
import net.thechance.mena.faith.presentation.utils.extentions.prayerTime.getHijriReadableDate
import net.thechance.mena.identity.domain.entity.Address
import net.thechance.mena.identity.domain.service.LocationService
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
class PrayerTimeViewModel(
    private val prayerTimeRepository: PrayerTimeRepository,
    private val getNextPrayerTimeUseCase: GetNextPrayerTimeUseCase,
    private val locationService: LocationService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : BaseViewModel<PrayerTimeUiState, PrayerTimeEffect>(PrayerTimeUiState()),
    PrayerTimeInteractionListener {
    private var countdownJob: Job? = null
    private var currentAddress: Address? = null
    private var currentHijriDateRaw: String = ""
    private var cachedPrayerTimes: List<PrayerTime> = emptyList()

    init {
        getUserLocation()
    }

    private fun getUserLocation() {
        tryToExecute(
            execute = { locationService.getActiveAddress()!! },
            onSuccess = ::onGetUserLocationSuccess,
            onError = { sendEffect(PrayerTimeEffect.NavigateToAddressesScreen) },
            dispatcher = dispatcher
        )
    }

    private fun onGetUserLocationSuccess(address: Address) {
        currentAddress = address
        updateState { state -> state.copy(address = address.addressLine) }
        getPrayerTimes(address, Clock.System.now())
    }

    private fun getPrayerTimes(address: Address, date: Instant) {
        tryToExecute(
            execute = {
                prayerTimeRepository.getPrayerTimes(date = date, address = address)
            },
            onSuccess = ::onPrayerTimesSuccess,
            dispatcher = dispatcher
        )
    }

    private fun onPrayerTimesSuccess(prayerTimes: List<PrayerTime>) {
        val filteredPrayerTimes = prayerTimes.filter { it.name != PrayerName.SUNRISE }
        val hijriDate = getHijriReadableDate(prayerTimes)
        val hijriDateRaw = prayerTimes.firstOrNull()?.hijriDate ?: ""

        cachedPrayerTimes = filteredPrayerTimes
        currentHijriDateRaw = hijriDateRaw

        updateState {
            it.copy(
                prayerTimes = filteredPrayerTimes,
                currentDate = hijriDate
            )
        }
        startCountdownTimer()
    }

    private fun startCountdownTimer() {
        countdownJob?.cancel()
        countdownJob = viewModelScope.launch(dispatcher) {
            try {
                while (isActive) {
                    updateNextPrayerInfoWithFlow()
                    delay(COUNTDOWN_UPDATE_INTERVAL)
                }
            } finally {
                countdownJob = null
            }
        }
    }

    private suspend fun updateNextPrayerInfoWithFlow() {
        val address = currentAddress ?: return

        if (!viewModelScope.isActive) return

        runCatching {
            getNextPrayerTimeUseCase.getNextPrayer(address).collect { nextPrayer ->
                if (!viewModelScope.isActive) return@collect

                nextPrayer?.let {
                    val remainingMillis = calculateRemainingTime(it.time, Clock.System.now())

                    updateState { state ->
                        state.copy(
                            nextPrayerName = it.name,
                            nextPrayerCountdown = formatCountdown(remainingMillis),
                            nextPrayerTime = it.time
                        )
                    }
                }
            }
        }
    }

    private fun calculateRemainingTime(prayerTime: Instant, currentTime: Instant): Long =
        prayerTime.toEpochMilliseconds() - currentTime.toEpochMilliseconds()

    override fun onBackClick() = sendEffect(PrayerTimeEffect.NavigateBack)

    override fun onPrevDateClick() {
        if (currentHijriDateRaw.isEmpty()) return

        val previousDate = getPreviousHijriDate(currentHijriDateRaw)
        updateState { it.copy(currentDate = convertHijriToReadableFormat(previousDate)) }
        currentHijriDateRaw = previousDate
        getHijriPrayerTimes(previousDate)
    }

    override fun onNextDateClick() {
        if (currentHijriDateRaw.isEmpty()) return

        val nextDate = getNextHijriDate(currentHijriDateRaw)
        updateState { it.copy(currentDate = convertHijriToReadableFormat(nextDate)) }
        currentHijriDateRaw = nextDate
        getHijriPrayerTimes(nextDate)
    }

    private fun getHijriPrayerTimes(hijriDateRaw: String) {
        val address = currentAddress ?: return

        tryToExecute(
            execute = {
                val formattedHijriDate = hijriDateRaw.split("-").let { (day, month, year) ->
                    "$year-$month-$day"
                }
                prayerTimeRepository.getPrayerTimeWithHijriDate(
                    date = formattedHijriDate,
                    isHijri = true,
                    address = address
                )
            },
            onSuccess = ::onHijriPrayerTimesSuccess,
            dispatcher = dispatcher
        )
    }

    private fun onHijriPrayerTimesSuccess(prayerTimes: List<PrayerTime>) {
        val filteredPrayerTimes = prayerTimes.filter { it.name != PrayerName.SUNRISE }
        val hijriDate = getHijriReadableDate(prayerTimes)

        cachedPrayerTimes = filteredPrayerTimes

        updateState {
            it.copy(
                prayerTimes = filteredPrayerTimes,
                currentDate = hijriDate
            )
        }
    }

    override fun onDateDropdownClick() = sendEffect(PrayerTimeEffect.NavigateCalenderDialog)

    override fun onLocationClick() = sendEffect(PrayerTimeEffect.NavigateToAddressesScreen)

    override fun onDateSelected(day: Int, month: Int, year: Int) {}

    override fun onDatePickerDismiss() {}

    override fun onCleared() {
        try {
            countdownJob?.cancel()
            countdownJob = null
            currentAddress = null
            cachedPrayerTimes = emptyList()
        } catch (e: Exception) {
            println("Error in onCleared: ${e.message}")
        } finally {
            super.onCleared()
        }
    }

    private companion object {
        const val COUNTDOWN_UPDATE_INTERVAL = 1000L
    }

    private fun getPreviousHijriDate(hijriDateRaw: String): String {
        val parts = hijriDateRaw.split("-")
        if (parts.size != 3) return hijriDateRaw

        val (day, month, year) = parts.map { it.toIntOrNull() ?: 0 }
        var newDay = day - 1
        var newMonth = month
        var newYear = year

        when {
            newDay < 1 -> {
                newMonth--
                newDay = when (newMonth) {
                    in 1..11 -> 30
                    12 -> 29
                    else -> {
                        newYear--
                        12
                    }
                }
            }
        }

        return "$newDay-$newMonth-$newYear"
    }

    private fun getNextHijriDate(hijriDateRaw: String): String {
        val parts = hijriDateRaw.split("-")
        if (parts.size != 3) return hijriDateRaw

        val (day, month, year) = parts.map { it.toIntOrNull() ?: 0 }
        var newDay = day + 1
        var newMonth = month
        var newYear = year

        when {
            newDay > 30 -> {
                newDay = 1
                newMonth++
                if (newMonth > 12) {
                    newMonth = 1
                    newYear++
                }
            }
        }

        return "$newDay-$newMonth-$newYear"
    }
}