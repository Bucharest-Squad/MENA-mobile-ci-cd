package net.thechance.mena.core_chat.presentation.utils

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format
import kotlinx.datetime.format.char

val timeFormat = LocalDateTime.Format {
    amPmHour()
    char(':')
    minute()
    amPmMarker("AM", "PM") // adds AM/PM
}

fun LocalDateTime.formatAsTime(): String = this.format(timeFormat)

