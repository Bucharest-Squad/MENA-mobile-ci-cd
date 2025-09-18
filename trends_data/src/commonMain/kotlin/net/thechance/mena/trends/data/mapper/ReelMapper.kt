package net.thechance.mena.trends.data.mapper

import kotlinx.datetime.LocalDateTime
import net.thechance.mena.trends.data.dto.ReelDto
import net.thechance.mena.trends.domain.entity.Reel

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