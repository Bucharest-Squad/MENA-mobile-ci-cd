package net.thechance.mena.trends.presentation.screen.manage_my_trends

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import net.thechance.mena.trends.domain.entity.Reel
import net.thechance.mena.trends.domain.repository.ReelsRepository
import net.thechance.mena.trends.presentation.shared.base.BasePagingSource
import net.thechance.mena.trends.presentation.shared.base.BaseViewModel
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Provided


@KoinViewModel
class ManageTrendsViewModel(
    @Provided private val repository: ReelsRepository,
) : BaseViewModel<ManageTrendsScreenState,
        ManageTrendsUiEffect>(ManageTrendsScreenState()),
    ManageTrendsInteractionListener {

    init {
        getReels()
    }

    private fun getReels() {
        tryToExecute(
            block = {
                Pager(PagingConfig(pageSize = 10, prefetchDistance = 5, initialLoadSize = 15)) {
                    BasePagingSource(
                        onError = {}
                    ) { page -> repository.getAllReels(page) }
                }.flow
            },
            onSuccess = ::onGetReelsSuccess,
            onError = {},
            onStart = { updateState { copy(isLoading = true) } },
            onEnd = { updateState { copy(isLoading = false) } }
        )
    }

    private fun onGetReelsSuccess(reelsFlow: Flow<PagingData<Reel>>) {
        val uiReelsFlow = reelsFlow.map { pagingData: PagingData<Reel> ->
                pagingData.map { reel -> reel.toUiState() }
            }
        updateState { copy(isLoading = false, reels = uiReelsFlow) }
    }

    override fun onReelItemClick(reelId: Int) {
        sendEffect(ManageTrendsUiEffect.NavigateToTrend(reelId))
    }

    override fun onBackClick() {
        sendEffect(ManageTrendsUiEffect.NavigateBack)
    }
}