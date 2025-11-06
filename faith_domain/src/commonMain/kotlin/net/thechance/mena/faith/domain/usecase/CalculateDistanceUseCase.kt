package net.thechance.mena.faith.domain.usecase


import net.thechance.mena.faith.domain.exception.FaithException
import kotlin.math.*


class CalculateDistanceUseCase {

    operator fun invoke(
        latitude1: Double,
        longitude1: Double,
        latitude2: Double,
        longitude2: Double
    ): Double {

        if (latitude1 == latitude2 && longitude1 == longitude2) return 0.0

        if (!isValidCoordinate(latitude1, longitude1) || !isValidCoordinate(latitude2, longitude2)) {
            throw FaithException.InvalidCoordinates
        }

        val earthRadiusKm = 6371.0

        val dLat = toRadians(latitude2 - latitude1)
        val dLon = toRadians(longitude2 - longitude1)

        val haversineComponent = sin(dLat / 2).pow(2.0) +
                cos(toRadians(latitude1)) *
                cos(toRadians(latitude2)) *
                sin(dLon / 2).pow(2.0)

        val centralAngle = 2 * atan2(sqrt(haversineComponent), sqrt(1 - haversineComponent))

        return (earthRadiusKm * centralAngle).coerceAtLeast(0.0)
    }

    private fun toRadians(degrees: Double): Double = degrees * (PI / 180)

    private fun isValidCoordinate(lat: Double, lon: Double): Boolean {
        return lat in -90.0..90.0 && lon in -180.0..180.0
    }
}

