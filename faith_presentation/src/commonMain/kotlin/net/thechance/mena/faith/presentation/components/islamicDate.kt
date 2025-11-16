package net.thechance.mena.faith.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import mena.faith_presentation.generated.resources.Res
import mena.faith_presentation.generated.resources.hijri_months
import net.thechance.mena.designsystem.presentation.component.datePicker.WheelDatePicker
import net.thechance.mena.faith.presentation.utils.IslamicDate
import org.jetbrains.compose.resources.stringArrayResource
import org.koin.compose.getKoin

@Composable
fun IslamicDatePicker(
    selectedDate: IslamicDate,
    onDateChange: (day: Int, month: Int, year: Int) -> Unit,
    modifier: Modifier = Modifier,
    minYear: Int = 1350,
    maxYear: Int = IslamicDate.now(getKoin().get()).year.plus(10)
) {
    val islamicMonthNames = getIslamicMonthNames()
    val yearList = createYearList(minYear, maxYear)

    val (currentMonthState, currentYearState) = rememberIslamicMonthYearState(selectedDate)
    val currentMonth = currentMonthState.value
    val currentYear = currentYearState.value

    val daysList = createIslamicDaysList(currentMonth, currentYear)
    val selectedIndices = calculateIslamicSelectionIndices(
        date = selectedDate,
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
                dayIndex = dayIndex,
                monthIndex = monthIndex,
                yearIndex = yearIndex,
                minYear = minYear,
                onMonthYearUpdate = { month, year ->
                    currentMonthState.value = month
                    currentYearState.value = year
                },
                onDateChange = onDateChange
            )
        }
    )
}

@Composable
private fun rememberIslamicMonthYearState(
    initialDate: IslamicDate
): Pair<MutableState<Int>, MutableState<Int>> {
    val currentMonthState = remember { mutableStateOf(initialDate.month) }
    val currentYearState = remember { mutableStateOf(initialDate.year) }

    LaunchedEffect(initialDate.month, initialDate.year) {
        currentMonthState.value = initialDate.month
        currentYearState.value = initialDate.year
    }

    return currentMonthState to currentYearState
}

@Composable
private fun createIslamicDaysList(month: Int, year: Int): List<String> {
    val daysCount = remember(month, year) {
        getIslamicMonthDays(month, year)
    }

    return remember(daysCount) {
        (1..daysCount).map { day ->
            day.toString().padStart(2, '0')
        }
    }
}

private fun getIslamicMonthDays(month: Int, year: Int): Int {
    return when (month) {
        1, 3, 5, 7, 9, 11 -> 30
        2, 4, 6, 8, 10 -> 29
        12 -> if (isIslamicLeapYear(year)) 30 else 29
        else -> 30
    }
}

private fun isIslamicLeapYear(year: Int): Boolean {
    return (year * 11 + 14) % 30 < 11
}

@Composable
private fun getIslamicMonthNames(): List<String> = stringArrayResource(Res.array.hijri_months)

@Composable
private fun createYearList(minYear: Int, maxYear: Int): List<String> {
    return remember(minYear, maxYear) {
        (minYear..maxYear).map { it.toString() }
    }
}

private data class SelectionIndices(
    val dayIndex: Int,
    val monthIndex: Int,
    val yearIndex: Int
)

private fun calculateIslamicSelectionIndices(
    date: IslamicDate,
    daysCount: Int,
    minYear: Int
): SelectionIndices {
    val dayIndex = (date.day.coerceIn(1, daysCount) - 1)
    val monthIndex = date.month - 1
    val yearIndex = date.year - minYear

    return SelectionIndices(
        dayIndex = dayIndex,
        monthIndex = monthIndex,
        yearIndex = yearIndex
    )
}

private fun convertMonthIndexToValue(monthIndex: Int): Int = monthIndex + 1

private fun convertYearIndexToValue(yearIndex: Int, minYear: Int): Int = minYear + yearIndex

private fun convertDayIndexToValue(dayIndex: Int): Int = dayIndex + 1

private fun handleIslamicWheelDateChange(
    dayIndex: Int,
    monthIndex: Int,
    yearIndex: Int,
    minYear: Int,
    onMonthYearUpdate: (month: Int, year: Int) -> Unit,
    onDateChange: (day: Int, month: Int, year: Int) -> Unit
) {
    val month = convertMonthIndexToValue(monthIndex)
    val year = convertYearIndexToValue(yearIndex, minYear)

    onMonthYearUpdate(month, year)

    val daysInMonth = getIslamicMonthDays(month, year)
    val day = convertDayIndexToValue(dayIndex).coerceIn(1, daysInMonth)

    onDateChange(day, month, year)
}