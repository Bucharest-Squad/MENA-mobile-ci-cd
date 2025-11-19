package net.thechance.mena.core_chat.data.source.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WeatherDetailsDTO(
    @SerialName("currentTemperature") val currentTemperature: Double,
    @SerialName("minTemperature") val minTemperature: Double,
    @SerialName("maxTemperature") val maxTemperature: Double,
    @SerialName("weatherCode") val weatherCode: Int
)
