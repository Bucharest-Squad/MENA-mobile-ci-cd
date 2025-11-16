package net.thechance.mena.faith.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import mena.faith_presentation.generated.resources.Res
import mena.faith_presentation.generated.resources.hijri_months
import mena.faith_presentation.generated.resources.hijri_months_shortened
import net.thechance.mena.designsystem.presentation.component.datePicker.WheelDatePicker
import net.thechance.mena.faith.presentation.utils.IslamicDate
import org.jetbrains.compose.resources.stringArrayResource
import org.koin.compose.getKoin

@Composable
fun IslamicDatePicker(
    selectedDate: IslamicDate,
    onDateChange: (day: Int, month: Int, year: Int) -> Unit,
    modifier: Modifier = Modifier,
    minYear: Int = IslamicDate.now(getKoin().get()).year.minus(10),
    maxYear: Int = IslamicDate.now(getKoin().get()).year.plus(10),
) {
    val containerWidth by remember { mutableIntStateOf(0) }
    val density = LocalDensity.current

    val shouldUseShortNames = (with(density) { containerWidth.toDp() } < 340.dp)

    val islamicMonthNames = getHijriMonthNames(shouldUseShortNames)
    val yearList = remember(minYear, maxYear) { createYearList(minYear, maxYear) }
    val daysList = remember(selectedDate.month, selectedDate.year) {
        createIslamicDaysList(
            selectedDate.month,
            selectedDate.year
        )
    }
    val selectedIndices = calculateIslamicSelectionIndices(
        selectedDate = selectedDate,
        daysCount = daysList.size,
        minYear = minYear
    )

    WheelDatePicker(
        selectedDayIndex = selectedIndices.dayIndex,
        selectedMonthIndex = selectedIndices.monthIndex,
        selectedYearIndex = selectedIndices.yearIndex,
        days = daysList,
        months = islamicMonthNames,
        years = yearList,
        modifier = modifier,
        onDateChange = { dayIndex, monthIndex, yearIndex ->
            handleIslamicWheelDateChange(
                dayIndex,
                monthIndex,
                yearIndex,
                minYear = minYear,
                onDateChanged = onDateChange
            )
        }
    )
}

private fun getIslamicMonthDays(month: Int, year: Int): Int {
    return when (month) {
        1, 3, 5, 7, 9, 11 -> 30
        2, 4, 6, 8, 10 -> 29
        12 -> if (isIslamicLeapYear(year)) 30 else 29
        else -> 30
    }
}

private fun createIslamicDaysList(month: Int, year: Int): List<String> {
    val daysInMonth = getIslamicMonthDays(month, year)
    return (1..daysInMonth).map { day ->
        day.toString().padStart(2, '0')
    }
}

private fun isIslamicLeapYear(year: Int): Boolean {
    return (year * 11 + 14) % 30 < 11
}

@Composable
private fun getHijriMonthNames(isShortened: Boolean): List<String> =
    if (isShortened) stringArrayResource(Res.array.hijri_months_shortened)
    else stringArrayResource(Res.array.hijri_months)

private fun createYearList(minYear: Int, maxYear: Int): List<String> {
    return (minYear..maxYear).map { it.toString() }
}

private data class SelectionIndices(
    val dayIndex: Int,
    val monthIndex: Int,
    val yearIndex: Int
)

private fun calculateIslamicSelectionIndices(
    selectedDate: IslamicDate,
    daysCount: Int,
    minYear: Int
): SelectionIndices {
    val dayIndex = (selectedDate.day.coerceIn(1, daysCount) - 1)
    val monthIndex = selectedDate.month - 1
    val yearIndex = selectedDate.year - minYear

    return SelectionIndices(
        dayIndex = dayIndex,
        monthIndex = monthIndex,
        yearIndex = yearIndex
    )
}

private fun handleIslamicWheelDateChange(
    dayIndex: Int,
    monthIndex: Int,
    yearIndex: Int,
    minYear: Int,
    onDateChanged: (day: Int, month: Int, year: Int) -> Unit
) {
    val month = monthIndex + 1
    val year = yearIndex + minYear
    val dayFromIndex = dayIndex + 1

    val daysInMonth = getIslamicMonthDays(month, year)
    val day = dayFromIndex.coerceIn(1, daysInMonth)
    onDateChanged(day, month, year)
}