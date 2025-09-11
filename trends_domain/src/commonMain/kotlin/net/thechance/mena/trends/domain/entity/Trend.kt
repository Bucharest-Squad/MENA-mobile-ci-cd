package net.thechance.mena.trends.domain.entity

import kotlinx.datetime.LocalDateTime

data class Trend(
    val id: Int,
    val authorId: Int,
    val thumbnailUrl: String,
    val videoUrl: String,
    val description: String,
    val likesCount: Int,
    val viewsCount: Int,
    val createdAt: LocalDateTime,
    val categories: List<Category>
)