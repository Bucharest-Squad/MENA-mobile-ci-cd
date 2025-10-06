package net.thechance.mena.faith.domain.repository

interface QiblahRepository {
    suspend fun calculateQiblahDirection(latitude: Double, longitude: Double): Double

    suspend fun getLocationName(latitude: Double, longitude: Double): String

    suspend fun observeDeviceOrientation(): Double
}
