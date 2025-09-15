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

data class TimeAgoData(val value: Int, val unit: TimeUnit)

enum class TimeUnit {
    SECONDS, MINUTES, HOURS, DAYS, WEEKS, MONTHS, YEARS
}

@OptIn(ExperimentalTime::class)
@Composable
fun Instant.getTimeAgo(): String {
    val timeAgoData = calculateTimeAgo()

    return when (timeAgoData.unit) {
        TimeUnit.SECONDS -> pluralStringResource(Res.plurals.time_seconds_ago, timeAgoData.value, timeAgoData.value)
        TimeUnit.MINUTES -> pluralStringResource(Res.plurals.time_minutes_ago, timeAgoData.value, timeAgoData.value)
        TimeUnit.HOURS -> pluralStringResource(Res.plurals.time_hours_ago, timeAgoData.value, timeAgoData.value)
        TimeUnit.DAYS -> pluralStringResource(Res.plurals.time_days_ago, timeAgoData.value, timeAgoData.value)
        TimeUnit.WEEKS -> pluralStringResource(Res.plurals.time_weeks_ago, timeAgoData.value, timeAgoData.value)
        TimeUnit.MONTHS -> pluralStringResource(Res.plurals.time_months_ago, timeAgoData.value, timeAgoData.value)
        TimeUnit.YEARS -> pluralStringResource(Res.plurals.time_years_ago, timeAgoData.value, timeAgoData.value)
    }
}

@OptIn(ExperimentalTime::class)
fun Instant.calculateTimeAgo(now: Instant = Clock.System.now()): TimeAgoData {
    val duration: Duration = now - this

    return when {
        duration.inWholeSeconds < TimeConstants.ONE_MINUTE_IN_SECONDS -> {
            TimeAgoData(duration.inWholeSeconds.toInt(), TimeUnit.SECONDS)
        }
        duration.inWholeMinutes < TimeConstants.ONE_HOUR_IN_MINUTES -> {
            TimeAgoData(duration.inWholeMinutes.toInt(), TimeUnit.MINUTES)
        }
        duration.inWholeHours < TimeConstants.ONE_DAY_IN_HOURS -> {
            TimeAgoData(duration.inWholeHours.toInt(), TimeUnit.HOURS)
        }
        duration.inWholeDays < TimeConstants.ONE_WEEK_IN_DAYS -> {
            TimeAgoData(duration.inWholeDays.toInt(), TimeUnit.DAYS)
        }
        duration.inWholeDays < TimeConstants.ONE_MONTH_IN_DAYS -> {
            TimeAgoData((duration.inWholeDays / TimeConstants.ONE_WEEK_IN_DAYS).toInt(), TimeUnit.WEEKS)
        }
        duration.inWholeDays < TimeConstants.ONE_YEAR_IN_DAYS -> {
            TimeAgoData((duration.inWholeDays / TimeConstants.ONE_MONTH_IN_DAYS).toInt(), TimeUnit.MONTHS)
        }
        else -> {
            TimeAgoData((duration.inWholeDays / TimeConstants.ONE_YEAR_IN_DAYS).toInt(), TimeUnit.YEARS)
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