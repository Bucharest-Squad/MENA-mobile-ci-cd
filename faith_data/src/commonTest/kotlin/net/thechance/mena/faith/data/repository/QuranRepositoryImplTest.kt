package net.thechance.mena.faith.data.repository

import de.jensklingenberg.ktorfit.Response
import dev.mokkery.MockMode
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import net.thechance.mena.faith.data.database.AyahDao
import net.thechance.mena.faith.data.database.ReciterDto
import net.thechance.mena.faith.data.database.RecitersDao
import net.thechance.mena.faith.data.database.SurahAudioDao
import net.thechance.mena.faith.data.database.SurahAudioDto
import net.thechance.mena.faith.data.database.SurahDto
import net.thechance.mena.faith.data.datastore.TilawahDataStore
import net.thechance.mena.faith.data.remote.model.tilawah.AyahSoundUrlRequest
import net.thechance.mena.faith.data.remote.model.tilawah.RecitersRequest
import net.thechance.mena.faith.data.remote.model.tilawah.SurahSoundRequest
import net.thechance.mena.faith.data.remote.service.TilawahApiService
import net.thechance.mena.faith.domain.entity.Surah
import net.thechance.mena.faith.domain.model.LastAyahForTilawah
import net.thechance.mena.faith.domain.model.Reciter
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class QuranRepositoryImplTest {

    private val mockDao: AyahDao = mock(MockMode.autofill)
    private val tilawahDataStore: TilawahDataStore = mock(MockMode.autofill)
    private val surahSoundDao: SurahAudioDao = mock(MockMode.autofill)
    private val recitersDao: RecitersDao = mock(MockMode.autofill)

    private val tilawahApiService = mock<TilawahApiService>(MockMode.autofill)
    private val repository =
        QuranRepositoryImpl(
            ayahDao = mockDao,
            tilawahDataStore = tilawahDataStore,
            surahSoundDao = surahSoundDao,
            recitersDao = recitersDao,
            tilawahApiService = tilawahApiService
        )


    @Test
    fun `getSur Should return list of sur when called`() = runTest {
        everySuspend { mockDao.getSur() } returns SURAH_DTOS

        val result = repository.getSur()

        assertEquals(SUR_LIST, result)
    }

    @Test
    fun `searchForReciter Should return list of reciters when called`() = runTest {

        everySuspend { recitersDao.searchReciters(query = "query") } returns RECITER_DTOS

        val result = repository.searchForReciter(query = "query")

        assertEquals(RECITER_LIST, result)
    }

    @Test
    fun `getSur Should return empty list when database is empty`() = runTest {
        everySuspend { mockDao.getSur() } returns emptyList()

        val result = repository.getSur()

        assertTrue(result.isEmpty())
    }

    @Test
    fun `getAyatOfSurah Should return empty list when surah has no ayat`() = runTest {
        everySuspend { mockDao.getAyatOfSurah(1) } returns emptyList()

        val result = repository.getAyatOfSurah(1)

        assertTrue(result.isEmpty())
    }

    @Test
    fun `getAyatOfSurah Should return empty list when surah id is non exist`() = runTest {
        everySuspend { mockDao.getAyatOfSurah(999) } returns emptyList()

        val result = repository.getAyatOfSurah(999)

        assertTrue(result.isEmpty())
    }

    @Test
    fun `getAyatOfSurah Should return empty list when surah id is zero`() = runTest {
        everySuspend { mockDao.getAyatOfSurah(0) } returns emptyList()

        val result = repository.getAyatOfSurah(0)

        assertTrue(result.isEmpty())
    }

    @Test
    fun `searchForAyahInQuran Should return empty list when no ayah match the query`() = runTest {
        everySuspend { mockDao.searchForAyahInQuran("nonexistent") } returns emptyList()

        val result = repository.searchForAyahInQuran("nonexistent")

        assertTrue(result.isEmpty())
    }

    @Test
    fun `searchForAyahInSurah Should return empty list when no ayah match the query in the specified surah`() =
        runTest {
            everySuspend { mockDao.searchForAyahInSurah(1, "nonexistent") } returns emptyList()

            val result = repository.searchForAyahInSurah(1, "nonexistent")

            assertTrue(result.isEmpty())
        }

    @Test
    fun `getLastAyahForTilawah should return stored ayah when datastore has value`() = runTest {
        everySuspend { tilawahDataStore.getLastAyah() } returns SAVED_TILAWAH_PROGRESS

        val result = repository.getLastAyahForTilawah()

        assertEquals(SAVED_TILAWAH_PROGRESS, result)
    }

    @Test
    fun `getLastAyahForTilawah should return default ayah when datastore is empty`() = runTest {
        everySuspend { tilawahDataStore.getLastAyah() } returns null

        val result = repository.getLastAyahForTilawah()

        assertEquals(DEFAULT_TILAWAH, result)
    }

    @Test
    fun `saveLastAyahForTilawah should call datastore saveLastAyah`() = runTest {
        val ayahToSave = TILAWAH_AYAH_TO_SAVE

        repository.saveLastAyahForTilawah(ayahToSave)

        verifySuspend {
            tilawahDataStore.saveLastAyah(ayahToSave)
        }
    }

    @Test
    fun `saveDefaultReciter should call datastore saveDefaultReciter`() = runTest {
        val reciterId = 1

        repository.saveDefaultReciter(reciterId)

        verifySuspend {
            tilawahDataStore.saveDefaultReciter(reciterId)
        }
    }

    @Test
    fun `getDefaultReciter should call datastore getDefaultReciter`() = runTest {
        val expectedReciterId = 1
        everySuspend { tilawahDataStore.getDefaultReciter() } returns flowOf(expectedReciterId)
        val result = repository.getDefaultReciter()
        assertEquals(expectedReciterId, result.first())
    }

    private fun <T> makeSuccessFakeResponse(
        body: T,
        successStatus: HttpStatusCode = HttpStatusCode.OK
    ): Response<T> {
        val mockHttpResponse: HttpResponse = mock(MockMode.autofill) {
            everySuspend { status } returns successStatus
        }
        return Response.success(
            body = body,
            rawResponse = mockHttpResponse
        ) as Response<T>
    }

    @Test
    fun `getReciters should return mapped reciters`() = runTest {
        val apiReciters = listOf(
            RecitersRequest(
                id = 1,
                name = FIST_RECITER_NAME,
                arabicName = FIST_RECITER_ARABIC_NAME,
                tilawahType = FIRST_TILWAH_TYPE
            ),
            RecitersRequest(
                id = 2,
                name = SECOND_RECITER_NAME,
                arabicName = SECOND_RECITER_ARABIC_NAME,
                tilawahType = SECOND_TILWAH_TYPE
            )
        )

        everySuspend {
            tilawahApiService.getReciters()
        } returns makeSuccessFakeResponse(apiReciters)

        val result = repository.getReciters()

        assertEquals(apiReciters.map { it.id }, result.map { it.id })
        assertEquals(apiReciters.map { it.name }, result.map { it.name })
        verifySuspend { tilawahApiService.getReciters() }
    }

    @Test
    fun `getReciters should return empty list when api returns empty`() = runTest {
        everySuspend {
            tilawahApiService.getReciters()
        } returns makeSuccessFakeResponse(emptyList())

        val result = repository.getReciters()

        assertTrue(result.isEmpty())
        verifySuspend { tilawahApiService.getReciters() }
    }

    @Test
    fun `getSurahAudioCachePath should return null when path not found in database`() = runTest {
        everySuspend { surahSoundDao.getCachedAudioPath(SURAH_ID_1, RECITER_ID_1) } returns null

        val result = repository.getSurahAudioCachePath(SURAH_ID_1, RECITER_ID_1)

        assertEquals(null, result)
    }

    @Test
    fun `getSurahAudioCachePath should return null when file does not exist on filesystem`() =
        runTest {
            everySuspend {
                surahSoundDao.getCachedAudioPath(
                    SURAH_ID_1,
                    RECITER_ID_1
                )
            } returns NON_EXISTENT_PATH
            // Mock FileSystem.SYSTEM.exists to return false

            val result = repository.getSurahAudioCachePath(SURAH_ID_1, RECITER_ID_1)

            assertEquals(null, result)
        }

    @Test
    fun `saveSurahAudioToCache should call dao saveSurahAudio with correct parameters`() = runTest {
        repository.saveSurahAudioToCache(SURAH_ID_1, RECITER_ID_1, LOCAL_PATH_SURAH_1)

        verifySuspend {
            surahSoundDao.saveSurahAudio(
                SurahAudioDto(
                    surahId = SURAH_ID_1,
                    reciterId = RECITER_ID_1,
                    localFilePath = LOCAL_PATH_SURAH_1
                )
            )
        }
    }

    @Test
    fun `saveSurahAudioToCache should save multiple different surahs`() = runTest {
        repository.saveSurahAudioToCache(SURAH_ID_1, RECITER_ID_1, LOCAL_PATH_SURAH_1_SIMPLE)
        repository.saveSurahAudioToCache(SURAH_ID_2, RECITER_ID_1, LOCAL_PATH_SURAH_2_SIMPLE)

        verifySuspend {
            surahSoundDao.saveSurahAudio(
                SurahAudioDto(
                    surahId = SURAH_ID_1,
                    reciterId = RECITER_ID_1,
                    localFilePath = LOCAL_PATH_SURAH_1_SIMPLE
                )
            )
        }
        verifySuspend {
            surahSoundDao.saveSurahAudio(
                SurahAudioDto(
                    surahId = SURAH_ID_2,
                    reciterId = RECITER_ID_1,
                    localFilePath = LOCAL_PATH_SURAH_2_SIMPLE
                )
            )
        }
    }

    @Test
    fun `getRemoteSurahSoundUrl should return url from api service`() = runTest {
        everySuspend {
            tilawahApiService.getSurahSoundUrl(
                SurahSoundRequest(reciterId = RECITER_ID_1, surahNumber = SURAH_ID_1)
            )
        } returns makeSuccessFakeResponse(REMOTE_URL_SURAH_1_RECITER_1)

        val result = repository.getRemoteSurahSoundUrl(SURAH_ID_1, RECITER_ID_1)

        assertEquals(REMOTE_URL_SURAH_1_RECITER_1, result)
        verifySuspend {
            tilawahApiService.getSurahSoundUrl(
                SurahSoundRequest(
                    RECITER_ID_1,
                    SURAH_ID_1
                )
            )
        }
    }

    @Test
    fun `getRemoteSurahSoundUrl should call api with different surah and reciter ids`() = runTest {
        everySuspend {
            tilawahApiService.getSurahSoundUrl(SurahSoundRequest(RECITER_ID_1, SURAH_ID_1))
        } returns makeSuccessFakeResponse(REMOTE_URL_SURAH_1)

        everySuspend {
            tilawahApiService.getSurahSoundUrl(SurahSoundRequest(RECITER_ID_2, SURAH_ID_2))
        } returns makeSuccessFakeResponse(REMOTE_URL_SURAH_2)

        val result1 = repository.getRemoteSurahSoundUrl(SURAH_ID_1, RECITER_ID_1)
        val result2 = repository.getRemoteSurahSoundUrl(SURAH_ID_2, RECITER_ID_2)

        assertEquals(REMOTE_URL_SURAH_1, result1)
        assertEquals(REMOTE_URL_SURAH_2, result2)
    }

    @Test
    fun `isSurahAudioCached should return false when audio is not cached`() = runTest {
        everySuspend { surahSoundDao.getCachedAudioPath(SURAH_ID_1, RECITER_ID_1) } returns null

        val result = repository.isSurahAudioCached(SURAH_ID_1, RECITER_ID_1)

        assertEquals(false, result)
    }

    @Test
    fun `isSurahAudioCached should return false when cached file does not exist`() = runTest {
        everySuspend {
            surahSoundDao.getCachedAudioPath(
                SURAH_ID_1,
                RECITER_ID_1
            )
        } returns NON_EXISTENT_PATH

        val result = repository.isSurahAudioCached(SURAH_ID_1, RECITER_ID_1)

        assertEquals(false, result)
    }

    @Test
    fun `getAyahSoundUrl should return remote url when surah is not cached`() = runTest {
        everySuspend { surahSoundDao.getCachedAudioPath(SURAH_ID_1, RECITER_ID_1) } returns null
        everySuspend {
            // ✅ Use getAyahSoundUrl instead of getSurahSoundUrl
            tilawahApiService.getAyahSoundUrl(
                AyahSoundUrlRequest(RECITER_ID_1, AYAH_NUMBER_1, SURAH_ID_1)
            )
        } returns makeSuccessFakeResponse(REMOTE_URL_SURAH_1)

        val result = repository.getAyahSoundUrl(
            ayahNumber = AYAH_NUMBER_1,
            surahNumber = SURAH_ID_1,
            reciterId = RECITER_ID_1
        )

        assertEquals(REMOTE_URL_SURAH_1, result)
    }


    @Test
    fun `getAyahSoundUrl should fallback to remote when cached path is empty`() = runTest {
        everySuspend {
            surahSoundDao.getCachedAudioPath(SURAH_ID_1, RECITER_ID_1)
        } returns EMPTY_PATH

        everySuspend {
            tilawahApiService.getAyahSoundUrl(
                AyahSoundUrlRequest(RECITER_ID_1, AYAH_NUMBER_1, SURAH_ID_1)
            )
        } returns makeSuccessFakeResponse(REMOTE_URL_SURAH_1)

        val result = repository.getAyahSoundUrl(
            ayahNumber = AYAH_NUMBER_1,
            surahNumber = SURAH_ID_1,
            reciterId = RECITER_ID_1
        )

        assertEquals(REMOTE_URL_SURAH_1, result)
    }

    @Test
    fun `getAyahSoundUrl should attempt to find ayah in folder when surah is cached`() = runTest {
        everySuspend {
            surahSoundDao.getCachedAudioPath(SURAH_ID_1, RECITER_ID_1)
        } returns CACHED_SURAH_PATH

        everySuspend {
            mockDao.getSurah(SURAH_ID_1)
        } returns SurahDto(number = SURAH_ID_1, name = "Al-Fatihah", ayahCount = 7)

        everySuspend {
            tilawahApiService.getAyahSoundUrl(
                AyahSoundUrlRequest(
                    reciterId = RECITER_ID_1,
                    ayahNumber = AYAH_NUMBER_5,
                    surahNumber = SURAH_ID_1
                )
            )
        } returns makeSuccessFakeResponse(REMOTE_URL_SURAH_1)

        val result = repository.getAyahSoundUrl(
            ayahNumber = AYAH_NUMBER_5,
            surahNumber = SURAH_ID_1,
            reciterId = RECITER_ID_1
        )

        assertEquals(REMOTE_URL_SURAH_1, result)
    }

    @Test
    fun `getReciters Should return cached reciters when cache is not empty`() = runTest {
        everySuspend { recitersDao.getAllReciters() } returns RECITER_DTOS

        val result = repository.getReciters()

        assertEquals(RECITER_LIST, result)
        verifySuspend { recitersDao.getAllReciters() }
    }

    @Test
    fun `getReciters Should fetch from network when cache is empty`() = runTest {
        everySuspend { recitersDao.getAllReciters() } returns emptyList()
        everySuspend {
            tilawahApiService.getReciters()
        } returns makeSuccessFakeResponse(apiReciters)
        everySuspend { recitersDao.insertReciters(any()) } returns Unit

        val result = repository.getReciters()

        assertEquals(RECITER_LIST, result)
        verifySuspend { recitersDao.getAllReciters() }
        verifySuspend { tilawahApiService.getReciters() }
        verifySuspend { recitersDao.insertReciters(any()) }
    }

    @Test
    fun `getReciters Should sync network data to cache when cache is empty`() = runTest {
        everySuspend { recitersDao.getAllReciters() } returns emptyList()
        everySuspend {
            tilawahApiService.getReciters()
        } returns makeSuccessFakeResponse(apiReciters)
        everySuspend { recitersDao.insertReciters(any()) } returns Unit

        repository.getReciters()

        verifySuspend { recitersDao.insertReciters(any()) }
    }

    @Test
    fun `getReciters Should return empty list when both cache and network are empty`() = runTest {
        everySuspend { mockDao.getAllReciters() } returns emptyList()
        everySuspend {
            tilawahApiService.getReciters()
        } returns makeSuccessFakeResponse(emptyList())

        val result = repository.getReciters()

        assertTrue(result.isEmpty())
    }

    private companion object {

        const val AL_FATIHAH_NAME = "Al-Fatihah"
        const val AL_BAQARAH_NAME = "Al-Baqarah"
        const val FIST_RECITER_NAME = "Abdelbasit"
        const val SECOND_RECITER_NAME = "Mishary"
        const val FIST_RECITER_ARABIC_NAME = "عبدالباسط"
        const val SECOND_RECITER_ARABIC_NAME = "مشاري"
        const val FIRST_TILWAH_TYPE = "mujawad"
        const val SECOND_TILWAH_TYPE = "mujawad"

        // IDs
        const val SURAH_ID_1 = 1
        const val SURAH_ID_2 = 2
        const val RECITER_ID_1 = 1
        const val RECITER_ID_2 = 2
        const val AYAH_NUMBER_1 = 1
        const val AYAH_NUMBER_5 = 5


        const val NON_EXISTENT_PATH = "/cache/nonexistent.mp3"
        const val CACHED_SURAH_PATH = "/cache/surah_1"
        const val LOCAL_PATH_SURAH_1 = "/cache/surah_1_reciter_1.mp3"
        const val LOCAL_PATH_SURAH_1_SIMPLE = "/cache/surah_1.mp3"
        const val LOCAL_PATH_SURAH_2_SIMPLE = "/cache/surah_2.mp3"
        const val EMPTY_PATH = ""


        const val REMOTE_URL_SURAH_1_RECITER_1 = "https://example.com/surah_1_reciter_1.mp3"
        const val REMOTE_URL_SURAH_1 = "https://example.com/surah_1.mp3"
        const val REMOTE_URL_SURAH_2 = "https://example.com/surah_2.mp3"


        val TILAWAH_AYAH_TO_SAVE = LastAyahForTilawah(
            number = 3,
            surahId = 1,
            surahName = AL_FATIHAH_NAME
        )

        val DEFAULT_TILAWAH = LastAyahForTilawah(
            number = 1,
            surahId = 1,
            surahName = "Al-Fatiha"
        )

        val SAVED_TILAWAH_PROGRESS = LastAyahForTilawah(
            number = 5,
            surahId = 2,
            surahName = AL_BAQARAH_NAME
        )



        val SURAH_DTOS: List<SurahDto> = listOf(
            SurahDto(number = 1, name = AL_FATIHAH_NAME, ayahCount = 7),
            SurahDto(number = 2, name = AL_BAQARAH_NAME, ayahCount = 286)
        )

        val RECITER_DTOS: List<ReciterDto> = listOf(
            ReciterDto(
                id = 1,
                name = FIST_RECITER_NAME,
                nameAr = FIST_RECITER_ARABIC_NAME,
                tilawahType = FIRST_TILWAH_TYPE
            ),
            ReciterDto(
                id = 2,
                name = SECOND_RECITER_NAME,
                nameAr = SECOND_RECITER_ARABIC_NAME,
                tilawahType = SECOND_TILWAH_TYPE
            ),
        )


        val SUR_LIST = listOf(
            Surah(
                id = 1,
                order = Surah.SurahOrder.AlFatihah,
                name = AL_FATIHAH_NAME,
                ayahCount = 7,
            ),
            Surah(
                id = 2,
                order = Surah.SurahOrder.AlBaqarah,
                name = AL_BAQARAH_NAME,
                ayahCount = 286,
            )
        )

        val RECITER_LIST = listOf(
            Reciter(
                id = 1,
                name = FIST_RECITER_NAME,
                arabicName = FIST_RECITER_ARABIC_NAME,
                tilawahType = FIRST_TILWAH_TYPE
            ),
            Reciter(
                id = 2,
                name = SECOND_RECITER_NAME,
                arabicName = SECOND_RECITER_ARABIC_NAME,
                tilawahType = SECOND_TILWAH_TYPE
            )
        )

        val apiReciters = listOf(
            RecitersRequest(
                id = 1,
                name = FIST_RECITER_NAME,
                arabicName = FIST_RECITER_ARABIC_NAME,
                tilawahType = FIRST_TILWAH_TYPE
            ),
            RecitersRequest(
                id = 2,
                name = SECOND_RECITER_NAME,
                arabicName = SECOND_RECITER_ARABIC_NAME,
                tilawahType = SECOND_TILWAH_TYPE
            )
        )
    }
}