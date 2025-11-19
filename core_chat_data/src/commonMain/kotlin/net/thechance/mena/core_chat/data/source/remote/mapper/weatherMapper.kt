package net.thechance.mena.core_chat.data.source.remote.mapper

import net.thechance.mena.core_chat.data.source.remote.dto.WeatherDetailsDTO
import net.thechance.mena.core_chat.domain.entity.WeatherDetails

fun WeatherDetailsDTO.toEntity(): WeatherDetails = WeatherDetails(
    currentTemperature = currentTemperature,
    minTemperature = minTemperature,
    maxTemperature = maxTemperature,
    weatherCode = weatherCode
)
