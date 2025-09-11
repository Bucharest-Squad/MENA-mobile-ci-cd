package net.thechance.mena.trends.data.repository

import kotlinx.datetime.LocalDateTime
import net.thechance.mena.trends.domain.entity.Category
import net.thechance.mena.trends.domain.entity.Trend
import net.thechance.mena.trends.domain.repository.TrendRepository

class FakeTrendsRepositoryImpl : TrendRepository {

    private val categories = listOf(
        Category(id = 1, name = "Sport", emoji = "⚽"),
        Category(id = 2, name = "Food", emoji = "🍔"),
        Category(id = 3, name = "Code", emoji = "💻")
    )

    private val trend = Trend(
        id = 1,
        authorId = 1,
        thumbnailUrl = "https://example.com/thumb1.jpg",
        videoUrl = "https://example.com/video1.mp4",
        description = "First Trend",
        likesCount = 22,
        viewsCount = 150,
        createdAt = LocalDateTime(2002, 2, 22, 5, 15),
        categories = categories
    )

    private val trends: MutableList<Trend> = mutableListOf(
        trend,
        trend.copy(id = 2, description = "Second Trend", likesCount = 10, viewsCount = 40),
        trend.copy(id = 3, description = "Third Trend", likesCount = 100, viewsCount = 415),
        trend.copy(id = 4, description = "Fourth Trend", likesCount = 5, viewsCount = 8),
        trend.copy(id = 5, description = "Fifth Trend", likesCount = 0, viewsCount = 1000)
    )

    override suspend fun deleteTrendById(id: Int) {
        trends.removeAll { it.id == id }
    }

    override suspend fun getAllTrends(): List<Trend> = trends.toList()
}