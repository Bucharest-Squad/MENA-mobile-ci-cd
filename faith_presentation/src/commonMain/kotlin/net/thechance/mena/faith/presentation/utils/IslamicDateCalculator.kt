package net.thechance.mena.faith.presentation.utils

expect class IslamicDateCalculatorImpl: IslamicDateCalculator {
    override fun gregorianToHijri(day: Int, month: Int, year: Int): IslamicDate
}

interface IslamicDateCalculator {
    fun gregorianToHijri(day: Int, month: Int, year: Int): IslamicDate
}

data class IslamicDate(
    val day: Int,
    val month: Int,
    val year: Int
)