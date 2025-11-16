package net.thechance.mena.faith.presentation.feature.prayertime

import net.thechance.mena.faith.domain.entity.PrayerName
import net.thechance.mena.faith.domain.entity.PrayerTime
import net.thechance.mena.faith.presentation.utils.IslamicDate
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
data class PrayerTimeUiState(
    val prayerTimes: List<PrayerTime> = emptyList(),
    val nextPrayerCountdown: String = "",
    val nextPrayerName: PrayerName = PrayerName.DHUHR,
    val currentDate: IslamicDate = IslamicDate(1,1,1447),
    val nextPrayerTime: Instant = Instant.fromEpochMilliseconds(0),
    val address: String = "",
    val showDatePicker: Boolean = false,
    val islamicDatePickerUiState: IslamicDatePickerUiState = IslamicDatePickerUiState(),
) {
    data class IslamicDatePickerUiState(
        val selectedIslamicDate: IslamicDate = IslamicDate(1,1,1447),
        val isClearDateActive: Boolean = false,
    )
}