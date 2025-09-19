package net.thechance.mena.trends.data.mapper

import net.thechance.mena.trends.data.dto.CategoryDto
import net.thechance.mena.trends.domain.entity.Category

fun CategoryDto.toEntity(): Category =
    Category(
        id = id.orEmpty(),
        name = name.orEmpty(),
        emoji = emoji.orEmpty()
    )

fun List<CategoryDto>.toEntityList(): List<Category> = map(CategoryDto::toEntity)