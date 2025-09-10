package net.thechance.mena.trends.domain.repository

import net.thechance.mena.trends.domain.entity.Interest

interface InterestsRepository {
    suspend fun getAllInterests(): List<Interest>
    suspend fun checkUserHasInterests(): Boolean
    suspend fun updateUserInterests(interestsIds: List<Int>)
}