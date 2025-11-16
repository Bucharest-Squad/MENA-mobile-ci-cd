package net.thechance.mena.faith.data.database.prayertimes

import kotlinx.datetime.LocalDate
import net.thechance.mena.faith.data.remote.model.prayertime.PrayerTimesDto
import net.thechance.mena.faith.domain.entity.PrayerName
import net.thechance.mena.faith.domain.entity.PrayerTime
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
fun PrayerTimesLocal.toDomain(): List<PrayerTime> {

    val hijriDate = hijriDate
    return listOf(
        PrayerTime(
            name = PrayerName.FAJR,
            time = fajr.toInstant(),
            hijriDate = hijriDate
        ),
        PrayerTime(
            name = PrayerName.SUNRISE,
            time = sunrise.toInstant(),
            hijriDate = hijriDate
        ),
        PrayerTime(
            name = PrayerName.DHUHR,
            time = dhuhr.toInstant(),
            hijriDate = hijriDate
        ),
        PrayerTime(
            name = PrayerName.ASR,
            time = asr.toInstant(),
            hijriDate = hijriDate
        ),
        PrayerTime(
            name = PrayerName.MAGHRIB,
            time = maghrib.toInstant(),
            hijriDate = hijriDate
        ),
        PrayerTime(
            name = PrayerName.ISHA,
            time = isha.toInstant(),
            hijriDate = hijriDate
        )
    )
}

fun PrayerTimesDto.toLocal(
    date: LocalDate,
    latitude: Double,
    longitude: Double
): PrayerTimesLocal =
    PrayerTimesLocal(
        latitude = latitude,
        longitude = longitude,
        date = date,
        hijriDate = hijriDate.orEmpty(),
        fajr = fajr.orEmpty(),
        sunrise = sunrise.orEmpty(),
        dhuhr = dhuhr.orEmpty(),
        asr = asr.orEmpty(),
        maghrib = maghrib.orEmpty(),
        isha = isha.orEmpty()
    )

@OptIn(ExperimentalTime::class)
private fun String?.toInstant(): Instant = runCatching {
    Instant.parse(this.orEmpty())
}.getOrDefault(Instant.DISTANT_PAST)