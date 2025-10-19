package net.thechance.mena.faith.domain.usecase

import net.thechance.mena.faith.domain.entity.Location
import net.thechance.mena.faith.domain.exception.FaithException
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.round
import kotlin.math.sin

class QiblahBearingCalculatorUseCase {
    private var currentContinuousAzimuth: Float = 0f
    fun calculateQiblahAngle(userLocation: Location): Double {
        validateCoordinates(location = userLocation)
        val userLatitudeRadians = userLocation.latitude.toRadians()
        val userLongitudeRadians = userLocation.longitude.toRadians()
        val kaabaLatitudeRadians = KAABA_LATITUDE.toRadians()
        val kaabaLongitudeRadians = KAABA_LONGITUDE.toRadians()

        val longitudeDifference = kaabaLongitudeRadians - userLongitudeRadians


        val y = sin(longitudeDifference) * cos(kaabaLatitudeRadians)
        val x =
            cos(userLatitudeRadians) * sin(kaabaLatitudeRadians) - sin(userLatitudeRadians) * cos(
                kaabaLatitudeRadians
            ) * cos(longitudeDifference)

        val bearing = atan2(y, x)

        val qiblaAngle = (bearing.toDegrees() + 360) % 360

        return round(qiblaAngle)
    }

    fun calculateContinuousAzimuth(rawAzimuth: Float): Float {
        val oldAngleOnCircle = currentContinuousAzimuth % 360
        val diff = getShortestAngleDifference(from = oldAngleOnCircle, to = rawAzimuth)
        currentContinuousAzimuth += diff
        return currentContinuousAzimuth
    }

    fun getShortestAngleDifference(from: Float, to: Float): Float {
        val diff = (to - from) % 360
        return when {
            diff > 180f -> diff - 360f
            diff < -180f -> diff + 360f
            else -> diff
        }
    }

    private fun validateCoordinates(location: Location) {
        require(location.latitude in -90.0..90.0) {
            throw FaithException.InvalidLatitudeException
        }
        require(location.longitude in -180.0..180.0) {
            throw FaithException.InvalidLongitudeException
        }
    }

    private fun Double.toRadians(): Double = this * PI / 180.0

    private fun Double.toDegrees(): Double = this * 180.0 / PI

    companion object Companion {
        const val KAABA_LATITUDE = 21.4225
        const val KAABA_LONGITUDE = 39.8262
    }
}