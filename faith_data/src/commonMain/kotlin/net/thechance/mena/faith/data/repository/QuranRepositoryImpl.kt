package net.thechance.mena.faith.data.repository

import net.thechance.mena.faith.data.database.AyahDao
import net.thechance.mena.faith.data.mapper.toAyah
import net.thechance.mena.faith.data.mapper.toSurah
import net.thechance.mena.faith.domain.entity.Ayah
import net.thechance.mena.faith.domain.entity.Surah
import net.thechance.mena.faith.domain.repository.QuranRepository

class QuranRepositoryImpl(val dao: AyahDao) : QuranRepository {

    override suspend fun getAllSur(): List<Surah> =
        dao.getAllSur().map { it.toSurah() }

    override suspend fun getAyatOfSurah(ayahId: Int): List<Ayah> =
        dao.getAyatOfSurah(surahNumber = ayahId).map { it.toAyah() }

    override suspend fun getAyahContent(): String {
        TODO("Not yet implemented")
    }
}
