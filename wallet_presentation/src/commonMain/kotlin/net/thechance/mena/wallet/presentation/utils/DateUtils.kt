package net.thechance.mena.wallet.presentation.utils

import androidx.compose.runtime.Composable
import mena.wallet_presentation.generated.resources.Res
import mena.wallet_presentation.generated.resources.month_april
import mena.wallet_presentation.generated.resources.month_august
import mena.wallet_presentation.generated.resources.month_december
import mena.wallet_presentation.generated.resources.month_february
import mena.wallet_presentation.generated.resources.month_january
import mena.wallet_presentation.generated.resources.month_july
import mena.wallet_presentation.generated.resources.month_june
import mena.wallet_presentation.generated.resources.month_march
import mena.wallet_presentation.generated.resources.month_may
import mena.wallet_presentation.generated.resources.month_november
import mena.wallet_presentation.generated.resources.month_october
import mena.wallet_presentation.generated.resources.month_september
import org.jetbrains.compose.resources.stringResource

fun getNumberOfDaysInMonth(year: Int, month: Int): Int {
    return when (month) {
        1, 3, 5, 7, 8, 10, 12 -> 31
        4, 6, 9, 11 -> 30
        2 -> if (isLeapYear(year)) 29 else 28
        else -> throw IllegalArgumentException("Invalid month number: $month")
    }
}

private fun isLeapYear(year: Int): Boolean {
    return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
}

@Composable
fun getMonthName(month: Int): String {
    return when (month) {
        1 -> stringResource(Res.string.month_january)
        2 -> stringResource(Res.string.month_february)
        3 -> stringResource(Res.string.month_march)
        4 -> stringResource(Res.string.month_april)
        5 -> stringResource(Res.string.month_may)
        6 -> stringResource(Res.string.month_june)
        7 -> stringResource(Res.string.month_july)
        8 -> stringResource(Res.string.month_august)
        9 -> stringResource(Res.string.month_september)
        10 -> stringResource(Res.string.month_october)
        11 -> stringResource(Res.string.month_november)
        12 -> stringResource(Res.string.month_december)
        else -> throw IllegalArgumentException("Invalid month number: $month")
    }
}