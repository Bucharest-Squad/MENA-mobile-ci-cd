package net.thechance.mena.faith.data.repository

import kotlinx.coroutines.flow.Flow
import net.thechance.mena.faith.data.database.AyahDao
import net.thechance.mena.faith.data.database.AyahDto
import net.thechance.mena.faith.data.database.SurahAudioDao
import net.thechance.mena.faith.data.database.SurahAudioDto
import net.thechance.mena.faith.data.datastore.TilawahDataStore
import net.thechance.mena.faith.data.mapper.toAyah
import net.thechance.mena.faith.data.mapper.toDomain
import net.thechance.mena.faith.data.mapper.toSurah
import net.thechance.mena.faith.data.remote.model.tilawah.SurahSoundRequest
import net.thechance.mena.faith.data.remote.service.TilawahApiService
import net.thechance.mena.faith.data.utils.executeApiSafely
import net.thechance.mena.faith.data.utils.executeLocalSafely
import net.thechance.mena.faith.domain.entity.Ayah
import net.thechance.mena.faith.domain.entity.Surah
import net.thechance.mena.faith.domain.model.LastAyahForTilawah
import net.thechance.mena.faith.domain.model.Reciter
import net.thechance.mena.faith.domain.repository.QuranRepository
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.SYSTEM

class QuranRepositoryImpl(
    val ayahDao: AyahDao,
    val surahSoundDao: SurahAudioDao,
    val tilawahApiService: TilawahApiService,
    val tilawahDataStore: TilawahDataStore
) : QuranRepository {

    override suspend fun getSur(): List<Surah> =
        executeLocalSafely {
            ayahDao.getSur().map { it.toSurah() }
        }

    override suspend fun getAyatOfSurah(surahId: Int): List<Ayah> =
        executeLocalSafely {
            ayahDao.getAyatOfSurah(surahId).map { it.toAyah() }
        }

    override suspend fun getLastAyahForTilawah(): LastAyahForTilawah {
        return tilawahDataStore.getLastAyah()
            ?: LastAyahForTilawah(number = 1, surahId = 1, surahName = "Al-Fatiha")
    }

    override suspend fun saveLastAyahForTilawah(savedAyah: LastAyahForTilawah) =
        tilawahDataStore.saveLastAyah(savedAyah)

    override suspend fun searchForAyahInSurah(
        surahId: Int,
        query: String,
    ): List<Ayah> =
        executeLocalSafely {
            ayahDao.searchForAyahInSurah(surahId = surahId, query = query).map(AyahDto::toAyah)
        }

    override suspend fun searchForAyahInQuran(query: String): List<Ayah> =
        executeLocalSafely {
            ayahDao.searchForAyahInQuran(query).map(AyahDto::toAyah)
        }

    override suspend fun getSurahAudioCachePath(surahId: Int, reciterId: Int): String? =
        executeLocalSafely {
            surahSoundDao.getCachedAudioPath(surahId, reciterId)?.takeIf { path ->
                FileSystem.SYSTEM.exists(path.toPath())
            }
        }

    override suspend fun saveSurahAudioToCache(
        surahId: Int,
        reciterId: Int,
        localPath: String
    ) {
        executeLocalSafely {
            val surahSound = SurahAudioDto(
                surahId = surahId,
                reciterId = reciterId,
                localFilePath = localPath
            )
            surahSoundDao.saveSurahAudio(surahSound)
        }
    }

    override suspend fun getRemoteSurahSoundUrl(
        surahId: Int,
        reciterId: Int
    ): String = "https://everyayah.com/data/Abdul_Basit_Mujawwad_128kbps/zips/001.zip"
//    {
//        executeApiSafely {
//            tilawahApiService.getSurahSoundUrl(
//                SurahSoundRequest(surahId, reciterId)
//            )
//        }
//    }

    override suspend fun isSurahAudioCached(surahId: Int, reciterId: Int): Boolean =
        executeLocalSafely {
            getSurahAudioCachePath(surahId, reciterId) != null
        }

    override suspend fun getAyahSoundUrl(
        ayahNumber: Int,
        surahNumber: Int,
        reciterId: Int
    ): String {

        val surahSound = getSurahAudioCachePath(surahNumber, reciterId)

        if (!surahSound.isNullOrEmpty()) findAyahInFolder(surahSound, ayahNumber)

        return executeApiSafely<String> {
            tilawahApiService.getSurahSoundUrl(
                SurahSoundRequest(surahNumber, reciterId)
            )
        }
    }

    private fun findAyahInFolder(folderPath: String, ayahNumber: Int): String? {
        val folder = folderPath.toPath()

        if (!FileSystem.SYSTEM.exists(folder)) return null

        val fileList = FileSystem.SYSTEM.list(folder)
        return fileList[ayahNumber.plus(1)].toString()
    }

    override suspend fun getReciters(): List<Reciter> =
        executeApiSafely { tilawahApiService.getReciters() }.map { it.toDomain() }

    override suspend fun getReciterById(reciterId: Int): Reciter =
        executeApiSafely { tilawahApiService.getReciters()}.first { it.id == reciterId }.toDomain()


    override suspend fun saveDefaultReciter(reciterId: Int) =
        tilawahDataStore.saveDefaultReciter(reciterId)


    override suspend fun getDefaultReciter(): Flow<Int> =
        tilawahDataStore.getDefaultReciter()
}