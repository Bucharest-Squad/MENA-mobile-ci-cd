package net.thechance.mena.trends.domain.entity

data class Trend(
    val id: Int,
    val thumbnailUrl: String,
    val videoUrl: String,
    val likesCount: Int,
    val viewsCount: Int,
    val description: String,
    val createdAt: Long
)