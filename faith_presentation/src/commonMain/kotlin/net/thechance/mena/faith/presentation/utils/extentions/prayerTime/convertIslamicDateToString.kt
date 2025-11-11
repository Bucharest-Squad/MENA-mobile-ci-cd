package net.thechance.mena.faith.presentation.utils.extentions.prayerTime

import net.thechance.mena.faith.presentation.utils.IslamicDate

fun convertIslamicDateToString(date: IslamicDate): String {
        val months = listOf(
            "Muharram", "Safar", "Rabi' al-awwal", "Rabi' al-thani",
            "Jumada al-awwal", "Jumada al-thani", "Rajab", "Sha'ban",
            "Ramadan", "Shawwal", "Dhu al-Qi'dah", "Dhu al-Hijjah"
        )
        val monthName = months.getOrNull(date.month - 1) ?: "Unknown"
        return "${date.day} $monthName ${date.year}"
    }