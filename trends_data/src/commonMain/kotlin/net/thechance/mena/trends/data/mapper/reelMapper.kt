package net.thechance.mena.trends.data.mapper

import kotlinx.datetime.LocalDateTime
import net.thechance.mena.trends.data.dto.CategoryDto
import net.thechance.mena.trends.data.dto.ReelDto
import net.thechance.mena.trends.domain.entity.Category
import net.thechance.mena.trends.domain.entity.Reel

fun CategoryDto.toEntity() =
    Category(
        id = id,
        name = name,
        emoji = iconPath
    )

fun ReelDto.toEntity() =
    Reel(
        id = id,
        thumbnailUrl = reelImageUrl,
        videoUrl = videoUrl,
        description = description,
        likesCount = likesCount,
        viewsCount = viewsCount,
        createdAt = LocalDateTime.parse(createdAt),
        categories = categories.map { it.toEntity() }
    )