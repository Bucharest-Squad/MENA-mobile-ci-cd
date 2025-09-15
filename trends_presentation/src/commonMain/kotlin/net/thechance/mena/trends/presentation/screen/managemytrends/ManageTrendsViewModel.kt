package net.thechance.mena.trends.presentation.screen.managemytrends

import net.thechance.mena.trends.domain.entity.Reel
import net.thechance.mena.trends.domain.repository.ReelRepository
import net.thechance.mena.trends.presentation.shared.base.BaseViewModel

class ManageTrendsViewModel(
    private val repository: ReelRepository
) : BaseViewModel<ManageTrendsUiState, ManageTrendsUiEffect>(
    initialState = ManageTrendsUiState(isLoading = true)
), ManageTrendsInteractionListener {

    init {
        loadReels()
    }

    private fun loadReels() {
        tryToExecute(
            block = { repository.getAllReels() },
            onSuccess = ::handleLoadReelsSuccess,
            onError = ::handleError,
            onStart = { updateState { copy(isLoading = true) } },
            onEnd = { updateState { copy(isLoading = false) } }
        )
    }

    private fun handleLoadReelsSuccess(reels: List<Reel>) {
        val uiModels = reels.map { it.toUiState() }
        updateState { copy(isLoading = false, reels = uiModels) }
    }

    override fun onRealTrendClick(reelId: Int) {
        sendEffect(ManageTrendsUiEffect.NavigateToTrend(reelId))

    }

    override fun onBackClick() {
        sendEffect(ManageTrendsUiEffect.NavigateBack)
    }

    private fun handleError(throwable: Throwable) {
        val errorRes = when (throwable) {
            // TODO: exceptions
            else -> null
        }
        updateState { copy(errorMessage = errorRes) }
    }
}