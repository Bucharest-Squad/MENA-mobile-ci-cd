package net.thechance.mena.faith.presentation.utils

actual class IslamicDateCalculatorImpl :IslamicDateCalculator {
    actual override fun gregorianToHijri(day: Int, month: Int, year: Int): IslamicDate {
        return IslamicDate(day, month, year)
    }
}