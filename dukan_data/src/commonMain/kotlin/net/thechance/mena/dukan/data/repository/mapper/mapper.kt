package net.thechance.mena.dukan.data.repository.mapper

import net.thechance.mena.dukan.data.repository.dto.CreateDukanRequest
import net.thechance.mena.dukan.domain.entity.Dukan

fun Dukan.toDukanRequest(): CreateDukanRequest {
    return CreateDukanRequest(
        name = name,
        categoryIds = categories.map { it.id },
        imageUrl = imageUrl,
        address = address,
        latitude = coordinates.latitude,
        longitude = coordinates.longitude,
        colorId = color.id,
        style = style.name
    )
}