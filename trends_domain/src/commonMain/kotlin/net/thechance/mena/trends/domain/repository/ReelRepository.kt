package net.thechance.mena.trends.domain.repository

import net.thechance.mena.trends.domain.entity.Product
import net.thechance.mena.trends.domain.entity.Reel

interface ReelRepository {
    suspend fun deleteReelById(id: Int)
    suspend fun getAllReels(page : Int): List<Reel>
}