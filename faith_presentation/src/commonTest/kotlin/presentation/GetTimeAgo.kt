package presentation

import kotlin.time.ExperimentalTime
import kotlin.time.toDuration
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Clock
import kotlin.time.DurationUnit
import kotlin.time.Instant

@OptIn(ExperimentalTime::class)
class InstantGetTimeAgoTest {

    private lateinit var fixedNow: Instant
    private lateinit var mockClock: Clock

    @BeforeTest
    fun setUp() {
        // Mock the Clock.System
        mockkStatic(Clock.System::class)
        fixedNow = Instant.fromEpochSeconds(1704067200) // Jan 1, 2024 00:00:00 UTC
        mockClock = mockk()
        every { Clock.System.now() } returns fixedNow
    }

    @AfterTest
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun getTimeAgo_shouldReturnSecondsAgo_whenDurationIsLessThanOneMinute() {
        // Given
        val testInstant = fixedNow.minus(30.toDuration(DurationUnit.SECONDS))

        // When
        val result = testInstant.getTimeAgo()

        // Then
        assertEquals("30 seconds ago", result)
    }

    @Test
    fun getTimeAgo_shouldReturnMinutesAgo_whenDurationIsLessThanOneHour() {
        // Given
        val testInstant = fixedNow.minus(45.toDuration(DurationUnit.MINUTES))

        // When
        val result = testInstant.getTimeAgo()

        // Then
        assertEquals("45 minutes ago", result)
    }

    @Test
    fun getTimeAgo_shouldReturnHoursAgo_whenDurationIsLessThanOneDay() {
        // Given
        val testInstant = fixedNow.minus(12.toDuration(DurationUnit.HOURS))

        // When
        val result = testInstant.getTimeAgo()

        // Then
        assertEquals("12 hours ago", result)
    }

    @Test
    fun getTimeAgo_shouldReturnDaysAgo_whenDurationIsLessThanOneWeek() {
        // Given
        val testInstant = fixedNow.minus(5.toDuration(DurationUnit.DAYS))

        // When
        val result = testInstant.getTimeAgo()

        // Then
        assertEquals("5 days ago", result)
    }

    @Test
    fun getTimeAgo_shouldReturnWeeksAgo_whenDurationIsLessThanOneMonth() {
        // Given
        val testInstant = fixedNow.minus(21.toDuration(DurationUnit.DAYS)) // 3 weeks

        // When
        val result = testInstant.getTimeAgo()

        // Then
        assertEquals("3 weeks ago", result)
    }

    @Test
    fun getTimeAgo_shouldReturnMonthsAgo_whenDurationIsLessThanOneYear() {
        // Given
        val testInstant = fixedNow.minus(180.toDuration(DurationUnit.DAYS)) // ~6 months

        // When
        val result = testInstant.getTimeAgo()

        // Then
        assertEquals("6 months ago", result)
    }

    @Test
    fun getTimeAgo_shouldReturnYearsAgo_whenDurationIsOneYearOrMore() {
        // Given
        val testInstant = fixedNow.minus(400.toDuration(DurationUnit.DAYS)) // ~1.1 years

        // When
        val result = testInstant.getTimeAgo()

        // Then
        assertEquals("1 years ago", result)
    }

    @Test
    fun getTimeAgo_shouldReturnJustNow_whenDurationIsZeroSeconds() {
        // Given
        val testInstant = fixedNow

        val result = testInstant.getTimeAgo()

        assertEquals("0 seconds ago", result)
    }

    @Test
    fun getTimeAgo_shouldReturnSingularForm_whenDurationIsExactlyOneUnit() {
        val testInstant = fixedNow.minus(1.toDuration(DurationUnit.MINUTES))

        val result = testInstant.getTimeAgo()

        assertEquals("1 minutes ago", result)
    }

    @Test
    fun getTimeAgo_shouldHandleBoundaryConditionsCorrectly() {
        val justBeforeMinute = fixedNow.minus(59.toDuration(DurationUnit.SECONDS))
        assertEquals("59 seconds ago", justBeforeMinute.getTimeAgo())

        val exactlyOneMinute = fixedNow.minus(60.toDuration(DurationUnit.SECONDS))
        assertEquals("1 minutes ago", exactlyOneMinute.getTimeAgo())
    }
}