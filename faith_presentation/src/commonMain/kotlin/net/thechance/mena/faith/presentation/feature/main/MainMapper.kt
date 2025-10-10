package net.thechance.mena.faith.presentation.feature.main.mapper

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import net.thechance.mena.faith.domain.entity.Ayah
import net.thechance.mena.faith.domain.entity.PrayerName
import net.thechance.mena.faith.domain.entity.PrayerTime
import net.thechance.mena.faith.domain.entity.Surah
import net.thechance.mena.faith.presentation.feature.main.PrayerTimesUiState
import net.thechance.mena.faith.presentation.feature.main.PrayerUiModel
import net.thechance.mena.faith.presentation.feature.main.TilawahUiState
import kotlin.time.ExperimentalTime


private val PRAYER_ORDER = listOf(
    PrayerName.FAJR,
    PrayerName.DHUHR,
    PrayerName.ASR,
    PrayerName.MAGHRIB,
    PrayerName.ISHA
)

@OptIn(ExperimentalTime::class)
fun formatTime(instant: kotlin.time.Instant): String {
    val kotlinxInstant = Instant.fromEpochMilliseconds(instant.toEpochMilliseconds())
    val localDateTime = kotlinxInstant.toLocalDateTime(TimeZone.currentSystemDefault())

    val hour = localDateTime.hour.toString().padStart(2, '0')
    val minute = localDateTime.minute.toString().padStart(2, '0')

    return "$hour:$minute"
}

fun getDisplayName(prayerName: PrayerName): String {
    return when (prayerName) {
        PrayerName.FAJR -> "Fajr"
        PrayerName.DHUHR -> "Dhuhr"
        PrayerName.ASR -> "Asr"
        PrayerName.MAGHRIB -> "Maghrib"
        PrayerName.ISHA -> "Isha"
        PrayerName.SUNRISE -> "Sunrise"
    }
}

@OptIn(ExperimentalTime::class)
fun isAM(instant: kotlin.time.Instant): Boolean {
    val kotlinxInstant = Instant.fromEpochMilliseconds(instant.toEpochMilliseconds())
    val localDateTime = kotlinxInstant.toLocalDateTime(TimeZone.currentSystemDefault())
    return localDateTime.hour < 12
}

@OptIn(ExperimentalTime::class)
fun mapToPrayerTimesUiState(
    prayerTimes: List<PrayerTime>,
    now: kotlin.time.Instant
): PrayerTimesUiState {
    val prayerMap = prayerTimes.associateBy { it.name }

    val prayers = PRAYER_ORDER.mapNotNull { prayerName ->
        prayerMap[prayerName]?.let { prayer ->
            PrayerUiModel(
                name = prayer.name,
                displayName = getDisplayName(prayer.name),
                time = formatTime(prayer.time),
                isAM = isAM(prayer.time)
            )
        }
    }

    val currentPrayerIndex = getCurrentPrayerIndex(prayerTimes, now)

    return PrayerTimesUiState(
        prayers = prayers,
        currentPrayerIndex = currentPrayerIndex
    )
}

@OptIn(ExperimentalTime::class)
private fun getCurrentPrayerIndex(prayerTimes: List<PrayerTime>, now: kotlin.time.Instant): Int {
    val sortedPrayers = prayerTimes
        .filter { it.name != PrayerName.SUNRISE }
        .sortedBy { it.time }

    for (i in sortedPrayers.indices) {
        val current = sortedPrayers[i]
        val next = sortedPrayers.getOrNull(i + 1)

        if (now < current.time) {
            return PRAYER_ORDER.indexOf(current.name)
        } else if (next == null || now < next.time) {
            return PRAYER_ORDER.indexOf(current.name)
        }
    }

    return 0
}

@OptIn(ExperimentalTime::class)
fun getSunriseTime(prayerTimes: List<PrayerTime>): String {
    return prayerTimes
        .firstOrNull { it.name == PrayerName.SUNRISE }
        ?.let { formatTime(it.time) }
        ?: ""
}

fun getHijriDate(prayerTimes: List<PrayerTime>): String {
    return prayerTimes.firstOrNull()?.hijriDate ?: ""
}

fun Ayah.toTilawahUiState(): TilawahUiState {
    val surahName = Surah.SurahOrder.entries
        .find { it.order == this.surahId }
        ?.name ?: "Unknown Surah"

    return TilawahUiState(
        surahName = surahName,
        ayahNumber = "Aya No $number",
        surahId = this.surahId

    )
}

