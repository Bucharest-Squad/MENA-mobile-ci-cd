package net.thechance.mena.faith.presentation.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import net.thechance.mena.designsystem.presentation.component.datePicker.WheelDatePicker
import kotlin.time.ExperimentalTime

data class IslamicDate(
    val day: Int,
    val month: Int,
    val year: Int
)

@Composable
fun IslamicDatePicker(
    selectedDate: IslamicDate?,
    modifier: Modifier = Modifier,
    minYear: Int = 1300,
    maxYear: Int = 1450,
    onDateChange: (day: Int, month: Int, year: Int) -> Unit,
) {
    val dateToUse = getEffectiveIslamicDate(selectedDate)
    val islamicMonthNames = getIslamicMonthNames()
    val yearList = createYearList(minYear, maxYear)

    val (currentMonthState, currentYearState) = rememberIslamicMonthYearState(dateToUse)
    val currentMonth = currentMonthState.value
    val currentYear = currentYearState.value

    val daysList = createIslamicDaysList(currentMonth, currentYear)
    val selectedIndices = calculateIslamicSelectionIndices(
        date = dateToUse,
        month = currentMonth,
        year = currentYear,
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

@OptIn(ExperimentalTime::class)
@Composable
private fun getEffectiveIslamicDate(selectedDate: IslamicDate?): IslamicDate {
    return remember {
        selectedDate ?: run {
            val today =
                kotlin.time.Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
            convertGregorianToIslamic(today)
        }
    }
}

@Composable
private fun createYearList(minYear: Int, maxYear: Int): List<String> {
    return remember(minYear, maxYear) {
        (minYear..maxYear).map { it.toString() }
    }
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
    return if (month in listOf(1, 3, 5, 7, 9, 11)) {
        30
    } else if (month in listOf(2, 4, 6, 8, 10)) {
        29
    } else {
        if (isIslamicLeapYear(year)) 30 else 29
    }
}

private fun isIslamicLeapYear(year: Int): Boolean {
    return (year * 11 + 3) % 30 < 11
}

private fun getIslamicMonthNames(): List<String> {
    return listOf(
        "محرم",
        "صفر",
        "ربيع الأول",
        "ربيع الثاني",
        "جمادى الأولى",
        "جمادى الثانية",
        "رجب",
        "شعبان",
        "رمضان",
        "شوال",
        "ذو القعدة",
        "ذو الحجة"
    )
}

private fun convertGregorianToIslamic(gregorianDate: LocalDate): IslamicDate {
    val jd = toJulianDay(gregorianDate.year, gregorianDate.monthNumber, gregorianDate.dayOfMonth)
    return fromJulianDay(jd)
}

private fun toJulianDay(year: Int, month: Int, day: Int): Int {
    val a = (14 - month) / 12
    val y = year + 4800 - a
    val m = month + 12 * a - 3
    return day + (153 * m + 2) / 5 + 365 * y + y / 4 - y / 100 + y / 400 - 32045
}

private fun fromJulianDay(jd: Int): IslamicDate {
    val l = jd + 1948440 - 385
    val n = (4 * l) / 146097
    val l2 = l - (146097 * n) / 4
    val i = (4000 * (l2 + 1)) / 1461001
    val l3 = l2 - (1461 * i) / 4 + 300
    val j = (30 * l3) / 10646
    val day = l3 - (10646 * j) / 30
    val month = j + 1
    val year = 100 * n + i - 4800 + j / 11

    return IslamicDate(day, month, year)
}

private data class SelectionIndices(
    val dayIndex: Int,
    val monthIndex: Int,
    val yearIndex: Int
)

private fun calculateIslamicSelectionIndices(
    date: IslamicDate,
    month: Int,
    year: Int,
    daysCount: Int,
    minYear: Int
): SelectionIndices {
    val dayIndex = (date.day.coerceIn(1, daysCount) - 1)
    val monthIndex = month - 1
    val yearIndex = year - minYear

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