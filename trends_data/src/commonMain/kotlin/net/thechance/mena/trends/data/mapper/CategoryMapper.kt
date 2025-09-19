package net.thechance.mena.trends.data.mapper

import net.thechance.mena.trends.data.dto.CategoryDto
import net.thechance.mena.trends.domain.entity.Category

fun CategoryDto.toEntity() =
    Category(
        id = id,
        name = name,
        emoji = emoji
    )