package net.thechance.mena.faith.data.repository

import dev.mokkery.MockMode
import dev.mokkery.answering.returns
import dev.mokkery.answering.sequentiallyReturns
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify
import dev.mokkery.verifySuspend
import kotlinx.coroutines.test.runTest
import net.thechance.mena.faith.data.database.AyahDao
import net.thechance.mena.faith.data.database.AyahDto
import net.thechance.mena.faith.data.database.SurahDto
import net.thechance.mena.faith.data.utils.SearchAlgorithm
import net.thechance.mena.faith.domain.entity.Ayah
import net.thechance.mena.faith.domain.entity.Surah
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class QuranRepositoryImplTest {

    private val mockDao: AyahDao = mock(MockMode.autofill)
    private val mockSearchAlgorithm: SearchAlgorithm = mock(MockMode.autofill)
    private val repository = QuranRepositoryImpl(mockDao, mockSearchAlgorithm)


    @Test
    fun `getAllSur Should return list of sur when called`() = runTest {
        // Given
        everySuspend { mockDao.getAllSur() } returns SURAH_DTOS

        // When
        val result = repository.getAllSur()

        // Then
        assertEquals(SUR_LIST, result)
    }

    @Test
    fun `getAllSur Should return empty list when database is empty`() = runTest {
        // Given
        everySuspend { mockDao.getAllSur() } returns emptyList()

        // When
        val result = repository.getAllSur()

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `getAyatOfSurah Should return empty list when surah has no ayat`() = runTest {
        // Given
        everySuspend { mockDao.getAyatOfSurah(1) } returns emptyList()

        // When
        val result = repository.getAyatOfSurah(1)

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `getAyatOfSurah Should return empty list when surah id is non exist`() = runTest {
        // Given
        everySuspend { mockDao.getAyatOfSurah(999) } returns emptyList()

        // When
        val result = repository.getAyatOfSurah(999)

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `getAyatOfSurah Should return empty list when surah id is zero`() = runTest {
        // Given
        everySuspend { mockDao.getAyatOfSurah(0) } returns emptyList()

        // When
        val result = repository.getAyatOfSurah(0)

        // Then
        assertTrue(result.isEmpty())
    }

    @Test
    fun `searchForAyahInQuran Should return empty list when no ayah match the query`() =
        runTest {
            // Given
            every { mockSearchAlgorithm.isContainsQuery(any(), any()) } sequentiallyReturns listOf(false, false)
            everySuspend { mockDao.getAllAyat() } returns AYAT_DTOS

            // When
            val result = repository.searchForAyahInQuran("nonexistent")

            // Then
            assertTrue(result.isEmpty())
        }

    @Test
    fun `searchForAyahInQuran returns matching ayahs from all surahs`() =
        runTest {
            // Given
            val query = "الله"
            val expectedAyahs = AYAT_LIST
            everySuspend { mockDao.getAllAyat() } returns AYAT_DTOS
            every { mockSearchAlgorithm.isContainsQuery(any(), query) } returns true

            // When
            val result = repository.searchForAyahInQuran(query)

            // Then
            assertEquals(2, result.size)
            assertEquals(expectedAyahs[0].number, result[0].number)
            assertEquals(expectedAyahs[1].number, result[1].number)
        }

    @Test
    fun `searchForAyahInQuran returns empty list when Quran has no ayahs`() =
        runTest {
            // Given
            val query = "الله"
            everySuspend { mockDao.getAllAyat() } returns emptyList()

            // When
            val result = repository.searchForAyahInQuran(query)

            // Then
            assertTrue(result.isEmpty())
        }

    @Test
    fun `searchForAyahInQuran searches across all surahs`() =
        runTest {
            // Given
            val query = "السلام"
            val ayahDtos =
                listOf(
                    AyahDto(1, 1, "", "", 1, "بسم الله الرحمن الرحيم", "بسم الله الرحمن الرحيم", 1, 1, 1, 1),
                    AyahDto(1, 36, "", "", 1, "والسلام عليه", "والسلام عليه", 1, 1, 1, 1),
                    AyahDto(40, 41, "", "", 1, "السلام عليكم", "السلام عليكم", 1, 1, 1, 1),
                    AyahDto(60, 114, "", "", 1, "ورحمة الله وبركاته والسلام عليكم", "ورحمة الله وبركاته والسلام عليكم", 1, 1, 1, 1),
                )
            everySuspend { mockDao.getAllAyat() } returns ayahDtos
            every { mockSearchAlgorithm.isContainsQuery("بسم الله الرحمن الرحيم", query) } returns false
            every { mockSearchAlgorithm.isContainsQuery("والسلام عليه", query) } returns true
            every { mockSearchAlgorithm.isContainsQuery("السلام عليكم", query) } returns true
            every { mockSearchAlgorithm.isContainsQuery("ورحمة الله وبركاته والسلام عليكم", query) } returns true

            // When
            val result = repository.searchForAyahInQuran(query)

            // Then
            assertEquals(3, result.size)
            assertTrue(result.map { it.surahId }.contains(36))
            assertTrue(result.map { it.surahId }.contains(41))
            assertTrue(result.map { it.surahId }.contains(114))
        }

    @Test
    fun `searchForAyahInQuran uses plainContent for search`() =
        runTest {
            // Given
            val query = "رحمن"
            val ayahDtos = listOf(AYAT_DTOS.first())
            everySuspend { mockDao.getAllAyat() } returns ayahDtos
            every { mockSearchAlgorithm.isContainsQuery("بسم الله الرحمن الرحيم", query) } returns true

            // When
            val result = repository.searchForAyahInQuran(query)

            // Then
            assertEquals(1, result.size)
        }

    @Test
    fun `searchForAyahInQuran returns all matching ayahs including duplicates from different positions`() =
        runTest {
            // Given
            val query = "الله"
            everySuspend { mockDao.getAllAyat() } returns AYAT_DTOS
            every { mockSearchAlgorithm.isContainsQuery(any(), query) } returns true

            // When
            val result = repository.searchForAyahInQuran(query)

            // Then
            assertEquals(2, result.size)
        }

    @Test
    fun `searchForAyahInSurah Should return empty list when no ayah match the query in the specified surah`() =
        runTest {
            // Given
            every { mockSearchAlgorithm.isContainsQuery(any(), any()) } sequentiallyReturns listOf(false, false)
            everySuspend { mockDao.getAyatOfSurah(1) } returns AYAT_DTOS

            // When
            val result = repository.searchForAyahInSurah(1, "nonexistent")

            // Then
            verify { mockSearchAlgorithm.isContainsQuery(any(), any()) }
            verifySuspend { mockDao.getAyatOfSurah(any()) }
            assertTrue(result.isEmpty())
        }

    @Test
    fun `searchForAyahInSurah returns matching ayahs`() =
        runTest {
            // Given
            val surahId = 1
            val query = "الله"
            val expectedAyahs = AYAT_LIST

            everySuspend { mockDao.getAyatOfSurah(surahId) } returns AYAT_DTOS
            every { mockSearchAlgorithm.isContainsQuery("بسم الله الرحمن الرحيم", query) } returns true
            every { mockSearchAlgorithm.isContainsQuery("الحمد الله رب العالمين", query) } returns true

            // When
            val result = repository.searchForAyahInSurah(surahId, query)

            // Then
            assertEquals(2, result.size)
            assertEquals(expectedAyahs[0].number, result[0].number)
            assertEquals(expectedAyahs[1].number, result[1].number)
        }

    @Test
    fun `searchForAyahInSurah filters out non-matching ayahs`() =
        runTest {
            // Given
            val surahId = 1
            val query = "محمد"
            val ayahDtos =
                listOf(
                    AYAT_DTOS.first(),
                    AyahDto(
                        2,
                        1,
                        "",
                        "",
                        2,
                        "محمد رسول الله",
                        "محمد رسول الله",
                        1,
                        1,
                        1,
                        1,
                    ),
                    AYAT_DTOS.last(),
                )
            everySuspend { mockDao.getAyatOfSurah(surahId) } returns ayahDtos
            every { mockSearchAlgorithm.isContainsQuery("بسم الله الرحمن الرحيم", query) } returns false
            every { mockSearchAlgorithm.isContainsQuery("محمد رسول الله", query) } returns true
            every { mockSearchAlgorithm.isContainsQuery("الحمد الله رب العالمين", query) } returns false

            // When
            val result = repository.searchForAyahInSurah(surahId, query)

            // Then
            assertEquals(1, result.size)
            assertEquals(2, result[0].number)
        }

    @Test
    fun `searchForAyahInSurah returns empty list when surah has no ayahs`() =
        runTest {
            // Given
            val surahId = 1
            val query = "الله"

            everySuspend { mockDao.getAyatOfSurah(surahId) } returns emptyList()

            // When
            val result = repository.searchForAyahInSurah(surahId, query)

            // Then
            assertTrue(result.isEmpty())
        }

    @Test
    fun `searchForAyahInSurah uses plainContent for search`() =
        runTest {
            // Given
            val surahId = 1
            val query = "الرحمن"
            val plainContent = "الرحمن"

            everySuspend { mockDao.getAyatOfSurah(surahId) } returns listOf(AYAT_DTOS.first().copy(plainContent = plainContent))
            every { mockSearchAlgorithm.isContainsQuery(plainContent, query) } returns true

            // When
            val result = repository.searchForAyahInSurah(surahId, query)

            // Then
            assertEquals(1, result.size)
        }

    private companion object {
        const val AL_FATIHAH_NAME = "Al-Fatihah"
        const val AL_BAQARAH_NAME = "Al-Baqarah"

        val SURAH_DTOS: List<SurahDto> = listOf(
            SurahDto(number = 1, name = AL_FATIHAH_NAME, ayahCount = 7),
            SurahDto(number = 2, name = AL_BAQARAH_NAME, ayahCount = 286)
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

        val AYAT_DTOS = listOf(
            AyahDto(
                1,
                1,
                "",
                "",
                1,
                "بسم الله الرحمن الرحيم",
                "بسم الله الرحمن الرحيم",
                1,
                1,
                1,
                1,
            ),
            AyahDto(
                2,
                1,
                "",
                "",
                2,
                "الحمد لله رب العالمين",
                "الحمد الله رب العالمين",
                1,
                1,
                1,
                1,
            ),
        )

        val AYAT_LIST = listOf(
            Ayah(1, 1, "بسم الله الرحمن الرحيم", "بسم الله الرحمن الرحيم"),
            Ayah(2, 1, "الحمد لله رب العالمين", "الحمد الله رب العالمين"),
        )
    }
}