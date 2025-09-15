package net.thechance.mena.trends.presentation.screen.managemytrends

import net.thechance.mena.trends.domain.entity.Reel
import net.thechance.mena.trends.domain.repository.ReelRepository
import net.thechance.mena.trends.presentation.shared.base.BaseViewModel
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Provided


@KoinViewModel
class ManageTrendsViewModel(
    @Provided private val repository: ReelRepository,
    initialState: ManageTrendsUiState
) : BaseViewModel<ManageTrendsUiState,
        ManageTrendsUiEffect>(initialState),
    ManageTrendsInteractionListener {

    init {
        loadReels()
    }

    private fun loadReels() {
        tryToExecute(
            block = { repository.getAllReels() },
            onSuccess = ::onHandleLoadReelsSuccess,
            onError = ::onHandleError,
            onStart = { updateState { copy(isLoading = true) } },
            onEnd = { updateState { copy(isLoading = false) } }
        )
    }

    private fun onHandleLoadReelsSuccess(reels: List<Reel>) {
        val uiModels = reels.map { it.toUiState() }
        updateState { copy(isLoading = false, reels = uiModels) }
    }

    override fun onRealTrendClick(reelId: Int) {
        sendEffect(ManageTrendsUiEffect.NavigateToTrend(reelId))

    }

    override fun onBackClick() {
        sendEffect(ManageTrendsUiEffect.NavigateBack)
    }

    private fun onHandleError(throwable: Throwable) {
        val errorMessage = when (throwable) {
            // TODO: exceptions
            else -> null
        }
        updateState { copy(errorMessage = errorMessage) }
    }
}