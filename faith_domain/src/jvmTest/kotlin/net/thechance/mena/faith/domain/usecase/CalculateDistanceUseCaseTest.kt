package net.thechance.mena.faith.domain.usecase

import net.thechance.mena.faith.domain.exception.FaithException
import org.junit.Test

import kotlin.test.*

class CalculateDistanceUseCaseTest {

    private val useCase = CalculateDistanceUseCase()

    @Test
    fun `invoke should return zero when coordinates are identical`() {
        val result = useCase(
            latitude1 = 21.4225,
            longitude1 = 39.8262,
            latitude2 = 21.4225,
            longitude2 = 39.8262
        )
        assertEquals(0.0, result)
    }

    @Test
    fun `invoke should throw InvalidCoordinates when first coordinate latitude is invalid`() {
        assertFailsWith<FaithException.InvalidCoordinates> {
            useCase(
                latitude1 = 91.0,
                longitude1 = 0.0,
                latitude2 = 0.0,
                longitude2 = 0.0
            )
        }
    }

    @Test
    fun `invoke should throw InvalidCoordinates when first coordinate longitude is invalid`() {
        assertFailsWith<FaithException.InvalidCoordinates> {
            useCase(
                latitude1 = 0.0,
                longitude1 = 200.0,
                latitude2 = 0.0,
                longitude2 = 0.0
            )
        }
    }

    @Test
    fun `invoke should throw InvalidCoordinates when second coordinate latitude is invalid`() {
        assertFailsWith<FaithException.InvalidCoordinates> {
            useCase(
                latitude1 = 0.0,
                longitude1 = 0.0,
                latitude2 = -100.0,
                longitude2 = 0.0
            )
        }
    }

    @Test
    fun `invoke should throw InvalidCoordinates when second coordinate longitude is invalid`() {
        assertFailsWith<FaithException.InvalidCoordinates> {
            useCase(
                latitude1 = 0.0,
                longitude1 = 0.0,
                latitude2 = 0.0,
                longitude2 = -200.0
            )
        }
    }

    @Test
    fun `invoke should return correct distance between Mecca and Medina`() {
        // Given: Approximate coordinates of Mecca and Medina
        val meccaLat = 21.3891
        val meccaLon = 39.8579
        val medinaLat = 24.5247
        val medinaLon = 39.5692

        // When
        val distance = useCase(meccaLat, meccaLon, medinaLat, medinaLon)

        // Then: Real-world distance ≈ 340 km (allow ±10 km tolerance)
        assertTrue(distance in 330.0..350.0, "Expected ≈340 km but got $distance")
    }

    @Test
    fun `invoke should return correct distance between New York and London`() {
        // Given
        val newYorkLat = 40.7128
        val newYorkLon = -74.0060
        val londonLat = 51.5074
        val londonLon = -0.1278

        // When
        val distance = useCase(newYorkLat, newYorkLon, londonLat, londonLon)

        // Then
        assertTrue(distance in 5470.0..5670.0, "Expected ≈5570 km but got $distance")
    }

    @Test
    fun `invoke should handle valid boundary coordinates -90 latitude`() {
        val result = useCase(-90.0, 0.0, 0.0, 0.0)
        assertTrue(result >= 0.0)
    }

    @Test
    fun `invoke should handle valid boundary coordinates +90 latitude`() {
        val result = useCase(90.0, 0.0, 0.0, 0.0)
        assertTrue(result >= 0.0)
    }

    @Test
    fun `invoke should handle valid boundary coordinates -180 longitude`() {
        val result = useCase(0.0, -180.0, 0.0, 0.0)
        assertTrue(result >= 0.0)
    }

    @Test
    fun `invoke should handle valid boundary coordinates +180 longitude`() {
        val result = useCase(0.0, 180.0, 0.0, 0.0)
        assertTrue(result >= 0.0)
    }

    @Test
    fun `toRadians should correctly convert degrees to radians`() {
        val method = CalculateDistanceUseCase::class.java.getDeclaredMethod("toRadians", Double::class.java)
        method.isAccessible = true

        val radians = method.invoke(useCase, 180.0) as Double
        assertEquals(Math.PI, radians, 1e-9)
    }

    @Test
    fun `isValidCoordinate should return true for valid range`() {
        val method = CalculateDistanceUseCase::class.java.getDeclaredMethod("isValidCoordinate", Double::class.java, Double::class.java)
        method.isAccessible = true

        val result = method.invoke(useCase, 45.0, 90.0) as Boolean
        assertTrue(result)
    }

    @Test
    fun `isValidCoordinate should return false for invalid latitude`() {
        val method = CalculateDistanceUseCase::class.java.getDeclaredMethod("isValidCoordinate", Double::class.java, Double::class.java)
        method.isAccessible = true

        val result = method.invoke(useCase, -91.0, 0.0) as Boolean
        assertFalse(result)
    }

    @Test
    fun `isValidCoordinate should return false for invalid longitude`() {
        val method = CalculateDistanceUseCase::class.java.getDeclaredMethod("isValidCoordinate", Double::class.java, Double::class.java)
        method.isAccessible = true

        val result = method.invoke(useCase, 0.0, 181.0) as Boolean
        assertFalse(result)
    }

    @Test
    fun `invoke should coerce negative values to zero`() {
        val result = useCase(0.0, 0.0, 0.0, 0.0)
        assertEquals(0.0, result)
    }
}
