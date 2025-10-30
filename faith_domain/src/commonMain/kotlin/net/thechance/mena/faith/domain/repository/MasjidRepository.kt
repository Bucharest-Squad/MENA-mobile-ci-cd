package net.thechance.mena.faith.domain.repository

import net.thechance.mena.faith.domain.entity.Masjid

interface MasjidRepository {
    suspend fun addMasjid(masjid: Masjid)
    suspend fun getNearbyMasjids(): List<Masjid>
}
