package net.thechance.mena.faith.domain.repository

import net.thechance.mena.faith.domain.entity.Ayah
import net.thechance.mena.faith.domain.entity.Surah

interface QuranRepository {
    suspend fun getAllSur(): List<Surah>
    suspend fun getAyatOfSurah(surahNumber: Int): List<Ayah>
    suspend fun getAyahContent(): String
}
