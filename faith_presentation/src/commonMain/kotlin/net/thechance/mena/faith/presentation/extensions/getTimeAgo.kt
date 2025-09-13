package net.thechance.mena.faith.presentation.extensions

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
fun LocalDateTime.getTimeAgo(): String {
    val now: Instant = Clock.System.now()
    val createdAtInstant: Instant = this.toInstant(TimeZone.currentSystemDefault())
    val duration: Duration = now - createdAtInstant

    return when {
        duration.inWholeSeconds < 60 -> {
            val seconds = duration.inWholeSeconds
            "$seconds second${if (seconds != 1L) "s" else ""} ago"
        }

        duration.inWholeMinutes < 60 -> {
            val minutes = duration.inWholeMinutes
            "$minutes minute${if (minutes != 1L) "s" else ""} ago"
        }

        duration.inWholeHours < 24 -> {
            val hours = duration.inWholeHours
            "$hours hour${if (hours != 1L) "s" else ""} ago"
        }

        duration.inWholeDays < 7 -> {
            val days = duration.inWholeDays
            "$days day${if (days != 1L) "s" else ""} ago"
        }

        duration.inWholeDays < 30 -> {
            val weeks = duration.inWholeDays / 7
            "$weeks week${if (weeks != 1L) "s" else ""} ago"
        }

        duration.inWholeDays < 365 -> {
            val months = duration.inWholeDays / 30
            "$months month${if (months != 1L) "s" else ""} ago"
        }

        else -> {
            val years = duration.inWholeDays / 365
            "$years year${if (years != 1L) "s" else ""} ago"
        }
    }
}
