package net.thechance.mena.faith.presentation.extensions

import androidx.compose.runtime.Composable
import mena.faith_presentation.generated.resources.Res
import mena.faith_presentation.generated.resources.time_days_ago
import mena.faith_presentation.generated.resources.time_hours_ago
import mena.faith_presentation.generated.resources.time_minutes_ago
import mena.faith_presentation.generated.resources.time_months_ago
import mena.faith_presentation.generated.resources.time_seconds_ago
import mena.faith_presentation.generated.resources.time_weeks_ago
import mena.faith_presentation.generated.resources.time_years_ago
import org.jetbrains.compose.resources.pluralStringResource
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
@Composable
fun Instant.getTimeAgo(): String {
    val now: Instant = Clock.System.now()
    val duration: Duration = now - this

    return when {
        duration.inWholeSeconds < TimeConstants.ONE_MINUTE_IN_SECONDS -> {
            val seconds = duration.inWholeSeconds.toInt()
            pluralStringResource(Res.plurals.time_seconds_ago, seconds, seconds)
        }

        duration.inWholeMinutes < TimeConstants.ONE_HOUR_IN_MINUTES -> {
            val minutes = duration.inWholeMinutes.toInt()
            pluralStringResource(Res.plurals.time_minutes_ago, minutes, minutes)
        }

        duration.inWholeHours < TimeConstants.ONE_DAY_IN_HOURS -> {
            val hours = duration.inWholeHours.toInt()
            pluralStringResource(Res.plurals.time_hours_ago, hours, hours)
        }

        duration.inWholeDays < TimeConstants.ONE_WEEK_IN_DAYS -> {
            val days = duration.inWholeDays.toInt()
            pluralStringResource(Res.plurals.time_days_ago, days, days)
        }

        duration.inWholeDays < TimeConstants.ONE_MONTH_IN_DAYS -> {
            val weeks = (duration.inWholeDays / TimeConstants.ONE_WEEK_IN_DAYS).toInt()
            pluralStringResource(Res.plurals.time_weeks_ago, weeks, weeks)
        }

        duration.inWholeDays < TimeConstants.ONE_YEAR_IN_DAYS -> {
            val months = (duration.inWholeDays / TimeConstants.ONE_MONTH_IN_DAYS).toInt()
            pluralStringResource(Res.plurals.time_months_ago, months, months)
        }

        else -> {
            val years = (duration.inWholeDays / TimeConstants.ONE_YEAR_IN_DAYS).toInt()
            pluralStringResource(Res.plurals.time_years_ago, years, years)
        }
    }
}


object TimeConstants {
    const val ONE_MINUTE_IN_SECONDS = 60
    const val ONE_HOUR_IN_MINUTES = 60
    const val ONE_DAY_IN_HOURS = 24
    const val ONE_WEEK_IN_DAYS = 7
    const val ONE_MONTH_IN_DAYS = 30
    const val ONE_YEAR_IN_DAYS = 365
}