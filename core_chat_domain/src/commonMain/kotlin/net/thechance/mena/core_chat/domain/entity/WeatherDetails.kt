package net.thechance.mena.core_chat.domain.entity

data class WeatherDetails(
    val currentTemperature: Double,
    val minTemperature: Double,
    val maxTemperature: Double,
    val weatherCode: Int
)
