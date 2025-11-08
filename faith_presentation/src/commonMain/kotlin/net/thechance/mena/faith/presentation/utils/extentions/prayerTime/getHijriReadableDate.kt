package net.thechance.mena.faith.presentation.utils.extentions.prayerTime

import net.thechance.mena.faith.domain.entity.PrayerTime

fun getHijriReadableDate(prayerTimes: List<PrayerTime>): String {
    val hijriDate = prayerTimes.firstOrNull()?.hijriDate ?: return ""
    val parts = hijriDate.split("-")
    if (parts.size != 3) return hijriDate
    val day = parts[DAY_INDEX].toInt()
    val month = parts[MONTH_INDEX].toInt()
    val year = parts[YEAR_INDEX].toInt()
    val monthName = hijriMonths[month] ?: return hijriDate
    return "$day $monthName $year"
}

fun getNextHijriDate(currentDate: String): String {
    val parts = currentDate.split("-")
    if (parts.size != 3) return currentDate

    var day = parts[DAY_INDEX].toInt()
    var month = parts[MONTH_INDEX].toInt()
    var year = parts[YEAR_INDEX].toInt()

    day++

    val daysInMonth = if (month % 2 == 1) 30 else 29

    if (day > daysInMonth) {
        day = 1
        month++
        if (month > 12) {
            month = 1
            year++
        }
    }

    return "${day.toString().padStart(2, '0')}-${month.toString().padStart(2, '0')}-$year"
}

fun getPreviousHijriDate(currentDate: String): String {
    val parts = currentDate.split("-")
    if (parts.size != 3) return currentDate

    var day = parts[DAY_INDEX].toInt()
    var month = parts[MONTH_INDEX].toInt()
    var year = parts[YEAR_INDEX].toInt()

    day--

    if (day < 1) {
        month--
        if (month < 1) {
            month = 12
            year--
        }
        val daysInPreviousMonth = if (month % 2 == 1) 30 else 29
        day = daysInPreviousMonth
    }

    return "${day.toString().padStart(2, '0')}-${month.toString().padStart(2, '0')}-$year"
}

fun convertHijriToReadableFormat(hijriDateWithDashes: String): String {
    val parts = hijriDateWithDashes.split("-")
    if (parts.size != 3) return hijriDateWithDashes
    val day = parts[DAY_INDEX].toInt()
    val month = parts[MONTH_INDEX].toInt()
    val year = parts[YEAR_INDEX].toInt()
    val monthName = hijriMonths[month] ?: return hijriDateWithDashes
    return "$day $monthName $year"
}

private val hijriMonths: Map<Int, String> = mapOf(
    1 to "Muharram",
    2 to "Safar",
    3 to "Rabi Al-Awwal",
    4 to "Rabi Al-Akhar",
    5 to "Jumada Al-Awwal",
    6 to "Jumada Al-Akhirah",
    7 to "Rajab",
    8 to "Shaban",
    9 to "Ramadan",
    10 to "Shawwal",
    11 to "Dhul Qadah",
    12 to "Dhul Hijjah"
)

private const val DAY_INDEX = 0
private const val MONTH_INDEX = 1
private const val YEAR_INDEX = 2