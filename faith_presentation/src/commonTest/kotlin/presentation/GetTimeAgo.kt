package presentation

import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant
import kotlin.time.Duration
import kotlin.test.assertEquals

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
        // Clear all mocks
        unmockkAll()
    }

    @Test
    fun getTimeAgo_shouldReturnSecondsAgo_whenDurationIsLessThanOneMinute() {
        // Given
        val testInstant = fixedNow.minus(Duration.seconds(30))

        // When
        val result = testInstant.getTimeAgo()

        // Then
        assertEquals("30 seconds ago", result)
    }

    @Test
    fun getTimeAgo_shouldReturnMinutesAgo_whenDurationIsLessThanOneHour() {
        // Given
        val testInstant = fixedNow.minus(Duration.minutes(45))

        // When
        val result = testInstant.getTimeAgo()

        // Then
        assertEquals("45 minutes ago", result)
    }

    @Test
    fun getTimeAgo_shouldReturnHoursAgo_whenDurationIsLessThanOneDay() {
        // Given
        val testInstant = fixedNow.minus(Duration.hours(12))

        // When
        val result = testInstant.getTimeAgo()

        // Then
        assertEquals("12 hours ago", result)
    }

    @Test
    fun getTimeAgo_shouldReturnDaysAgo_whenDurationIsLessThanOneWeek() {
        // Given
        val testInstant = fixedNow.minus(Duration.days(5))

        // When
        val result = testInstant.getTimeAgo()

        // Then
        assertEquals("5 days ago", result)
    }

    @Test
    fun getTimeAgo_shouldReturnWeeksAgo_whenDurationIsLessThanOneMonth() {
        // Given
        val testInstant = fixedNow.minus(Duration.days(21)) // 3 weeks

        // When
        val result = testInstant.getTimeAgo()

        // Then
        assertEquals("3 weeks ago", result)
    }

    @Test
    fun getTimeAgo_shouldReturnMonthsAgo_whenDurationIsLessThanOneYear() {
        // Given
        val testInstant = fixedNow.minus(Duration.days(180)) // ~6 months

        // When
        val result = testInstant.getTimeAgo()

        // Then
        assertEquals("6 months ago", result)
    }

    @Test
    fun getTimeAgo_shouldReturnYearsAgo_whenDurationIsOneYearOrMore() {
        // Given
        val testInstant = fixedNow.minus(Duration.days(400)) // ~1.1 years

        // When
        val result = testInstant.getTimeAgo()

        // Then
        assertEquals("1 years ago", result)
    }

    @Test
    fun getTimeAgo_shouldReturnJustNow_whenDurationIsZeroSeconds() {
        // Given
        val testInstant = fixedNow

        // When
        val result = testInstant.getTimeAgo()

        // Then
        assertEquals("0 seconds ago", result)
    }

    @Test
    fun getTimeAgo_shouldReturnSingularForm_whenDurationIsExactlyOneUnit() {
        val testInstant = fixedNow.minus(Duration.minutes(1))

        // When
        val result = testInstant.getTimeAgo()

        // Then
        assertEquals("1 minutes ago", result)
    }

    @Test
    fun getTimeAgo_shouldHandleBoundaryConditionsCorrectly() {
        val justBeforeMinute = fixedNow.minus(Duration.seconds(59))
        assertEquals("59 seconds ago", justBeforeMinute.getTimeAgo())

        val exactlyOneMinute = fixedNow.minus(Duration.seconds(60))
        assertEquals("1 minutes ago", exactlyOneMinute.getTimeAgo())
    }
}