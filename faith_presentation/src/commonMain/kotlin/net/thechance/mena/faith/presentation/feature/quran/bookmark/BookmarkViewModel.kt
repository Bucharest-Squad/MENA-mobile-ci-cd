package net.thechance.mena.faith.presentation.feature.quran.bookmark

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mena.faith_presentation.generated.resources.Res
import mena.faith_presentation.generated.resources.bookmark_removed_successfully
import net.thechance.mena.faith.domain.entity.AyahBookmark
import net.thechance.mena.faith.domain.repository.BookmarkRepository
import net.thechance.mena.faith.presentation.base.BaseViewModel
import net.thechance.mena.faith.presentation.base.SnackBarState
import net.thechance.mena.faith.presentation.base.createPagingSourceFlow

class BookmarkViewModel(
    private val bookmarkRepository: BookmarkRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel<BookmarksScreenState, BookmarkEffect>(BookmarksScreenState()),
    BookmarkInteractionListener {

    private val cachedBookmarksFlow = createBookmarksPagingSource()
        .map { pagingData -> pagingData.map { bookmark -> bookmark.toUiState() } }
        .cachedIn(viewModelScope)

    private val deletedBookmarkIdsFlow = MutableStateFlow(setOf<Int>())

    private val filteredBookmarksFlow = combine(
        cachedBookmarksFlow,
        deletedBookmarkIdsFlow
    ) { pagingData, deletedIds ->
        pagingData.filter { bookmark -> bookmark.bookmarkId !in deletedIds }
    }

    init {
        initializeBookmarks()
    }

    override fun onBackClick() = sendEffect(BookmarkEffect.NavigateBack)

    override fun onStartTilawahClick() = sendEffect(BookmarkEffect.NavigateBack)

    override fun onDeleteBookmarkClick(bookmarkId: Int) {
        deletedBookmarkIdsFlow.update { currentSet ->
            currentSet + bookmarkId
        }

        viewModelScope.launch {
            tryToExecute(
                dispatcher = dispatcher,
                execute = { bookmarkRepository.deleteAyahBookmark(bookmarkId) },
                onSuccess = { onDeleteBookmarkSuccess() },
                onError = { error ->
                    deletedBookmarkIdsFlow.update { currentSet ->
                        currentSet - bookmarkId
                    }
                    handleErrorState(error)
                }
            )
        }
    }

    private fun initializeBookmarks() {
        updateState {
            it.copy(
                bookmarks = filteredBookmarksFlow,
                isLoading = false
            )
        }
    }

    private fun onDeleteBookmarkSuccess() {
        showSnackBar(
            messageResource = Res.string.bookmark_removed_successfully,
            status = SnackBarState.Status.Success
        )
    }

    private fun createBookmarksPagingSource(): Flow<PagingData<AyahBookmark>> {
        return createPagingSourceFlow(
            onError = {},
        ) { pageNumber, pageSize ->
            bookmarkRepository.getAyahBookmarks(pageNumber, pageSize)
        }
    }

    private fun handleErrorState(throwable: Throwable) {
        // TODO: handle error here
    }
}
