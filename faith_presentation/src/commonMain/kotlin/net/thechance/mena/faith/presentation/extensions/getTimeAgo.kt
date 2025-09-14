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
        duration.inWholeSeconds < 60 -> {
            val seconds = duration.inWholeSeconds.toInt()
            pluralStringResource(Res.plurals.time_seconds_ago, seconds, seconds)
        }

        duration.inWholeMinutes < 60 -> {
            val minutes = duration.inWholeMinutes.toInt()
            pluralStringResource(Res.plurals.time_minutes_ago, minutes, minutes)
        }

        duration.inWholeHours < 24 -> {
            val hours = duration.inWholeHours.toInt()
            pluralStringResource(Res.plurals.time_hours_ago, hours, hours)
        }

        duration.inWholeDays < 7 -> {
            val days = duration.inWholeDays.toInt()
            pluralStringResource(Res.plurals.time_days_ago, days, days)
        }

        duration.inWholeDays < 30 -> {
            val weeks = (duration.inWholeDays / 7).toInt()
            pluralStringResource(Res.plurals.time_weeks_ago, weeks, weeks)
        }

        duration.inWholeDays < 365 -> {
            val months = (duration.inWholeDays / 30).toInt()
            pluralStringResource(Res.plurals.time_months_ago, months, months)
        }

        else -> {
            val years = (duration.inWholeDays / 365).toInt()
            pluralStringResource(Res.plurals.time_years_ago, years, years)
        }
    }
}