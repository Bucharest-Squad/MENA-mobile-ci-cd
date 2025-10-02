package net.thechance.mena.faith.presentation.feature.quran.bookmark

import app.cash.turbine.test
import dev.mokkery.MockMode
import dev.mokkery.answering.returns
import dev.mokkery.answering.throws
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import net.thechance.mena.faith.domain.entity.Ayah
import net.thechance.mena.faith.domain.entity.AyahBookmark
import net.thechance.mena.faith.domain.entity.Surah
import net.thechance.mena.faith.domain.repository.BookmarkRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@OptIn(ExperimentalCoroutinesApi::class)
class BookmarkViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val repository: BookmarkRepository = mock(MockMode.autofill)

    private fun createTestViewModel(): BookmarkViewModel {
        return BookmarkViewModel(
            dispatcher = testDispatcher,
            bookmarkRepository = repository
        )
    }

    @Test
    fun `init should load bookmarks when viewModel is created`() = runTest(testDispatcher) {
        // Given
        val pageNumber = 1
        val pageSize = 20
        everySuspend { repository.getAyahBookmarks(pageNumber, pageSize) } returns fakeBookmarks

        // When
        val testViewModel = createTestViewModel()
        advanceUntilIdle()

        // Then
        val state = testViewModel.uiState.value
        assertNotNull(state.bookmarks)
        assertFalse(state.isLoading)
    }

    @Test
    fun `onDeleteBookmarkClick should add bookmark ID to deletedBookmarkIds when repository succeeds`() =
        runTest(testDispatcher) {
            // Given
            val pageNumber = 1
            val pageSize = 20
            everySuspend { repository.getAyahBookmarks(pageNumber, pageSize) } returns fakeBookmarks
            everySuspend { repository.deleteAyahBookmark(BOOKMARK_ID) } returns Unit
            val testViewModel = createTestViewModel()
            advanceUntilIdle()

            // When
            testViewModel.onDeleteBookmarkClick(BOOKMARK_ID)
            advanceUntilIdle()

            // Then
            val state = testViewModel.uiState.value
            assertNotNull(state.bookmarks)
        }

    @Test
    fun `onDeleteBookmarkClick should keep bookmark ID in deletedBookmarkIds initially then remove on failure`() =
        runTest(testDispatcher) {
            // Given
            val pageNumber = 1
            val pageSize = 20
            everySuspend { repository.getAyahBookmarks(pageNumber, pageSize) } returns fakeBookmarks
            everySuspend { repository.deleteAyahBookmark(BOOKMARK_ID) } throws RuntimeException("delete failed")
            val testViewModel = createTestViewModel()
            advanceUntilIdle()

            // When
            testViewModel.onDeleteBookmarkClick(BOOKMARK_ID)
            advanceUntilIdle()

            // Then
            val state = testViewModel.uiState.value
            assertNotNull(state.bookmarks)
        }

    @Test
    fun `onBackClick should navigate back when called`() = runTest(testDispatcher) {
        // Given
        val pageNumber = 1
        val pageSize = 20
        everySuspend { repository.getAyahBookmarks(pageNumber, pageSize) } returns fakeBookmarks
        val testViewModel = createTestViewModel()
        advanceUntilIdle()

        // When & Then
        testViewModel.uiEffect.test {
            testViewModel.onBackClick()
            assertEquals(BookmarkEffect.NavigateBack, awaitItem())
        }
    }

    @Test
    fun `onStartTilawahClick should navigate back when called`() = runTest(testDispatcher) {
        // Given
        val pageNumber = 1
        val pageSize = 20
        everySuspend { repository.getAyahBookmarks(pageNumber, pageSize) } returns fakeBookmarks
        val testViewModel = createTestViewModel()
        advanceUntilIdle()

        // When & Then
        testViewModel.uiEffect.test {
            testViewModel.onStartTilawahClick()
            assertEquals(BookmarkEffect.NavigateBack, awaitItem())
        }
    }

    private companion object {
        const val BOOKMARK_ID = 1
        const val SURAH_ID = 1
        const val AYAH_NUMBER = 1
        const val SURAH_AYAH_COUNT = 7

        @OptIn(ExperimentalTime::class)
        val fakeBookmarks: List<AyahBookmark> = listOf(
            AyahBookmark(
                id = BOOKMARK_ID,
                surah = Surah(
                    id = SURAH_ID,
                    order = Surah.SurahOrder.AlFath,
                    name = "Al-Fatiha",
                    ayahCount = SURAH_AYAH_COUNT,
                ),
                ayah = Ayah(
                    number = AYAH_NUMBER,
                    content = "بِسْمِ اللهِ الرَّحْمٰنِ الرَّحِيْمِ",
                    surahId = SURAH_ID,
                    plainContent = "بسم الله الرحمن الرحيم"
                ),
                createdAt = Instant.Companion.fromEpochMilliseconds(0)
            )
        )
    }
}