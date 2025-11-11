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
    val currentDate: String = "",
    val nextPrayerTime: Instant = Instant.fromEpochMilliseconds(0),
    val address: String = "",
    val showDatePicker: Boolean = false,
    val selectedIslamicDate: IslamicDate? = null,
    val isToday: Boolean = true
)