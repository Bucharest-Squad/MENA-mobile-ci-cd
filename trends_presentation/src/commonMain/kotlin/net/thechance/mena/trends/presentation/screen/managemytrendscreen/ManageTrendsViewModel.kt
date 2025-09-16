package net.thechance.mena.trends.presentation.screen.managemytrendscreen

import net.thechance.mena.trends.domain.entity.Reel
import net.thechance.mena.trends.domain.repository.ReelRepository
import net.thechance.mena.trends.presentation.shared.base.BaseViewModel
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Provided


@KoinViewModel
class ManageTrendsViewModel(
    @Provided private val repository: ReelRepository,
) : BaseViewModel<ManageTrendsScreenState,
        ManageTrendsUiEffect>(ManageTrendsScreenState()),
    ManageTrendsInteractionListener {

    init {
        getReels()
    }

    private fun getReels() {
        tryToExecute(
            block = { repository.getAllReels() },
            onSuccess = ::onGetReelsSuccess,
            onError = {},
            onStart = { updateState { copy(isLoading = true) } },
            onEnd = { updateState { copy(isLoading = false) } }
        )
    }

    private fun onGetReelsSuccess(reels: List<Reel>) {
        val uiReals = reels.map { it.toUiState() }
        updateState { copy(isLoading = false, reels = uiReals) }
    }

    override fun onReelItemClick(reelId: Int) {
        sendEffect(ManageTrendsUiEffect.NavigateToTrend(reelId))

    }

    override fun onBackClick() {
        sendEffect(ManageTrendsUiEffect.NavigateBack)
    }

}