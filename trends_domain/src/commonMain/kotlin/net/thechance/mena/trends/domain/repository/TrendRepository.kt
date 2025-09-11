package net.thechance.mena.trends.domain.repository

import net.thechance.mena.trends.domain.entity.Trend

interface TrendRepository {
    suspend fun deleteTrendById(id: Int)
    suspend fun getAllTrends(): List<Trend>
}