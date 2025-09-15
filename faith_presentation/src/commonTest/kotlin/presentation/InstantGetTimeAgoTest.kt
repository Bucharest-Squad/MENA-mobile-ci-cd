package presentation

import net.thechance.mena.faith.presentation.extensions.TimeUnit
import net.thechance.mena.faith.presentation.extensions.calculateTimeAgo
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.DurationUnit
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
class InstantGetTimeAgoTest {
    private lateinit var fixedNow: Instant

    @BeforeTest
    fun setUp() {
        fixedNow = Instant.fromEpochSeconds(1704067200) // Jan 1, 2024 00:00:00 UTC
    }

    @Test
    fun calculateTimeAgo_shouldReturnSecondsValue_whenDurationIsLessThanOneMinute() {
        val testInstant = fixedNow.minus(30.toDuration(DurationUnit.SECONDS))
        val result = testInstant.calculateTimeAgo(fixedNow)
        assertEquals(30, result.value)
    }

    @Test
    fun calculateTimeAgo_shouldReturnSecondsUnit_whenDurationIsLessThanOneMinute() {
        val testInstant = fixedNow.minus(30.toDuration(DurationUnit.SECONDS))
        val result = testInstant.calculateTimeAgo(fixedNow)
        assertEquals(TimeUnit.SECONDS, result.unit)
    }

    @Test
    fun calculateTimeAgo_shouldReturnMinutesValue_whenDurationIsLessThanOneHour() {
        val testInstant = fixedNow.minus(45.toDuration(DurationUnit.MINUTES))
        val result = testInstant.calculateTimeAgo(fixedNow)
        assertEquals(45, result.value)
    }

    @Test
    fun calculateTimeAgo_shouldReturnMinutesUnit_whenDurationIsLessThanOneHour() {
        val testInstant = fixedNow.minus(45.toDuration(DurationUnit.MINUTES))
        val result = testInstant.calculateTimeAgo(fixedNow)
        assertEquals(TimeUnit.MINUTES, result.unit)
    }

    @Test
    fun calculateTimeAgo_shouldReturnHoursValue_whenDurationIsLessThanOneDay() {
        val testInstant = fixedNow.minus(12.toDuration(DurationUnit.HOURS))
        val result = testInstant.calculateTimeAgo(fixedNow)
        assertEquals(12, result.value)
    }

    @Test
    fun calculateTimeAgo_shouldReturnHoursUnit_whenDurationIsLessThanOneDay() {
        val testInstant = fixedNow.minus(12.toDuration(DurationUnit.HOURS))
        val result = testInstant.calculateTimeAgo(fixedNow)
        assertEquals(TimeUnit.HOURS, result.unit)
    }

    @Test
    fun calculateTimeAgo_shouldReturnDaysValue_whenDurationIsLessThanOneWeek() {
        val testInstant = fixedNow.minus(5.toDuration(DurationUnit.DAYS))
        val result = testInstant.calculateTimeAgo(fixedNow)
        assertEquals(5, result.value)
    }

    @Test
    fun calculateTimeAgo_shouldReturnDaysUnit_whenDurationIsLessThanOneWeek() {
        val testInstant = fixedNow.minus(5.toDuration(DurationUnit.DAYS))
        val result = testInstant.calculateTimeAgo(fixedNow)
        assertEquals(TimeUnit.DAYS, result.unit)
    }

    @Test
    fun calculateTimeAgo_shouldReturnWeeksValue_whenDurationIsLessThanOneMonth() {
        val testInstant = fixedNow.minus(21.toDuration(DurationUnit.DAYS))
        val result = testInstant.calculateTimeAgo(fixedNow)
        assertEquals(3, result.value)
    }

    @Test
    fun calculateTimeAgo_shouldReturnWeeksUnit_whenDurationIsLessThanOneMonth() {
        val testInstant = fixedNow.minus(21.toDuration(DurationUnit.DAYS))
        val result = testInstant.calculateTimeAgo(fixedNow)
        assertEquals(TimeUnit.WEEKS, result.unit)
    }

    @Test
    fun calculateTimeAgo_shouldReturnMonthsValue_whenDurationIsLessThanOneYear() {
        val testInstant = fixedNow.minus(180.toDuration(DurationUnit.DAYS))
        val result = testInstant.calculateTimeAgo(fixedNow)
        assertEquals(6, result.value)
    }

    @Test
    fun calculateTimeAgo_shouldReturnMonthsUnit_whenDurationIsLessThanOneYear() {
        val testInstant = fixedNow.minus(180.toDuration(DurationUnit.DAYS))
        val result = testInstant.calculateTimeAgo(fixedNow)
        assertEquals(TimeUnit.MONTHS, result.unit)
    }

    @Test
    fun calculateTimeAgo_shouldReturnYearsValue_whenDurationIsOneYearOrMore() {
        val testInstant = fixedNow.minus(400.toDuration(DurationUnit.DAYS))
        val result = testInstant.calculateTimeAgo(fixedNow)
        assertEquals(1, result.value)
    }

    @Test
    fun calculateTimeAgo_shouldReturnYearsUnit_whenDurationIsOneYearOrMore() {
        val testInstant = fixedNow.minus(400.toDuration(DurationUnit.DAYS))
        val result = testInstant.calculateTimeAgo(fixedNow)
        assertEquals(TimeUnit.YEARS, result.unit)
    }

    @Test
    fun calculateTimeAgo_shouldReturnZeroValue_whenDurationIsZero() {
        val testInstant = fixedNow
        val result = testInstant.calculateTimeAgo(fixedNow)
        assertEquals(0, result.value)
    }

    @Test
    fun calculateTimeAgo_shouldReturnSecondsUnit_whenDurationIsZero() {
        val testInstant = fixedNow
        val result = testInstant.calculateTimeAgo(fixedNow)
        assertEquals(TimeUnit.SECONDS, result.unit)
    }

    @Test
    fun calculateTimeAgo_shouldReturnSecondsValue_forBoundaryJustBeforeMinute() {
        val justBeforeMinute = fixedNow.minus(59.toDuration(DurationUnit.SECONDS))
        val result = justBeforeMinute.calculateTimeAgo(fixedNow)
        assertEquals(59, result.value)
    }

    @Test
    fun calculateTimeAgo_shouldReturnSecondsUnit_forBoundaryJustBeforeMinute() {
        val justBeforeMinute = fixedNow.minus(59.toDuration(DurationUnit.SECONDS))
        val result = justBeforeMinute.calculateTimeAgo(fixedNow)
        assertEquals(TimeUnit.SECONDS, result.unit)
    }

    @Test
    fun calculateTimeAgo_shouldReturnMinutesValue_forBoundaryExactlyOneMinute() {
        val exactlyOneMinute = fixedNow.minus(60.toDuration(DurationUnit.SECONDS))
        val result = exactlyOneMinute.calculateTimeAgo(fixedNow)
        assertEquals(1, result.value)
    }

    @Test
    fun calculateTimeAgo_shouldReturnMinutesUnit_forBoundaryExactlyOneMinute() {
        val exactlyOneMinute = fixedNow.minus(60.toDuration(DurationUnit.SECONDS))
        val result = exactlyOneMinute.calculateTimeAgo(fixedNow)
        assertEquals(TimeUnit.MINUTES, result.unit)
    }
}