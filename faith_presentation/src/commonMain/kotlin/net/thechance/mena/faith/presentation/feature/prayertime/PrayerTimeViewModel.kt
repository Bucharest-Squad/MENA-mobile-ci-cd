package net.thechance.mena.faith.presentation.feature.prayertime

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toLocalDateTime
import net.thechance.mena.faith.domain.entity.PrayerName
import net.thechance.mena.faith.domain.entity.PrayerTime
import net.thechance.mena.faith.domain.repository.PrayerTimeRepository
import net.thechance.mena.faith.domain.usecase.GetNextPrayerTimeUseCase
import net.thechance.mena.faith.presentation.base.BaseViewModel
import net.thechance.mena.faith.presentation.utils.IslamicDate
import net.thechance.mena.faith.presentation.utils.IslamicDateCalculator
import net.thechance.mena.faith.presentation.utils.extentions.prayerTime.calculateNextIslamicDate
import net.thechance.mena.faith.presentation.utils.extentions.prayerTime.calculatePreviousIslamicDate
import net.thechance.mena.faith.presentation.utils.extentions.prayerTime.formatCountdown
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
    private val islamicDateCalculator: IslamicDateCalculator,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : BaseViewModel<PrayerTimeUiState, PrayerTimeEffect>(PrayerTimeUiState()),
    PrayerTimeInteractionListener {

    private var countdownJob: Job? = null
    private var currentAddress: Address? = null

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
        updateState { it.copy(address = address.addressLine) }
        getPrayerTimes(address, Clock.System.now())
    }

    private fun getPrayerTimes(address: Address, date: Instant) {
        tryToExecute(
            execute = { prayerTimeRepository.getPrayerTimes(date = date, address = address) },
            onSuccess = ::onPrayerTimesSuccess,
            dispatcher = dispatcher
        )
    }

    private fun onPrayerTimesSuccess(prayerTimes: List<PrayerTime>) {
        val filteredPrayerTimes = prayerTimes.filter { it.name != PrayerName.SUNRISE }

        val currentIslamicDate = IslamicDate.now(islamicDateCalculator)

        updateState {
            it.copy(
                prayerTimes = filteredPrayerTimes,
                currentDate = currentIslamicDate,
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
        val currentDate = uiState.value.currentDate
        val previousIslamicDate = calculatePreviousIslamicDate(currentDate)

        updateState { it.copy(currentDate = previousIslamicDate) }

        getPrayerTimesForIslamicDate(previousIslamicDate)
    }

    override fun onNextDateClick() {
        val currentDate = uiState.value.currentDate
        val nextIslamicDate = calculateNextIslamicDate(currentDate)

        updateState { it.copy(currentDate = nextIslamicDate) }

        getPrayerTimesForIslamicDate(nextIslamicDate)
    }

    override fun onDateDropdownClick() =
        updateState {
            it.copy(
                showDatePicker = true,
                islamicDatePickerUiState = PrayerTimeUiState.IslamicDatePickerUiState(
                    selectedIslamicDate = uiState.value.currentDate,
                )
            )
        }

    override fun onLocationClick() = sendEffect(PrayerTimeEffect.NavigateToAddressesScreen)

    override fun onSelectedDateChange(day: Int, month: Int, year: Int) {
        val selectedIslamicDate = IslamicDate(day, month, year)

        updateState {
            it.copy(
                islamicDatePickerUiState = it.islamicDatePickerUiState.copy(
                    selectedIslamicDate = selectedIslamicDate,
                    isClearDateActive = selectedIslamicDate == uiState.value.currentDate,
                )
            )
        }
    }

    override fun onDateSelected() {
        updateState {
            it.copy(
                showDatePicker = false,
                currentDate = it.islamicDatePickerUiState.selectedIslamicDate,
                islamicDatePickerUiState = PrayerTimeUiState.IslamicDatePickerUiState()
            )
        }

        getPrayerTimesForIslamicDate(uiState.value.currentDate)
    }

    override fun onClearSelectedDate() =
        updateState {
            it.copy(
                islamicDatePickerUiState = it.islamicDatePickerUiState.copy(
                    selectedIslamicDate = it.currentDate,
                    isClearDateActive = false,
                )
            )
        }

    override fun onDatePickerDismiss() =
        updateState {
            it.copy(
                showDatePicker = false,
                islamicDatePickerUiState = PrayerTimeUiState.IslamicDatePickerUiState(),
            )
        }

    private fun getPrayerTimesForIslamicDate(islamicDate: IslamicDate) {
        val address = currentAddress ?: return

        tryToExecute(
            execute = {
                val formattedHijriDate =
                    "${islamicDate.year}-${islamicDate.month}-${islamicDate.day}"
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
        updateState { it.copy(prayerTimes = filteredPrayerTimes) }
    }

    private companion object {
        const val COUNTDOWN_UPDATE_INTERVAL = 1000L
    }
}
