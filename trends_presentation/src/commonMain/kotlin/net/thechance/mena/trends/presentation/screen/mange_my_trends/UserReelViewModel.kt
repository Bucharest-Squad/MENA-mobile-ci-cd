package net.thechance.mena.trends.presentation.screen.mange_my_trends

import net.thechance.mena.trends.domain.repository.ReelRepository
import net.thechance.mena.trends.presentation.shared.base.BaseViewModel
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Provided

@KoinViewModel
class UserReelViewModel(
    @Provided private val reelRepository: ReelRepository
) : BaseViewModel<UserReelUiState, UserReelUiEffect>(UserReelUiState()),
    UserReelUiInteractionListener {


    private fun setLoadingTrue() = updateState { copy(isLoading = true) }

    private fun setLoadingFalse() = updateState { copy(isLoading = false) }

    private fun handleError(throwable: Throwable) {
        val errorRes = when (throwable) {
            //TODO() WILL HANDLE EXCEPTIONS
            else -> {}
        }
    }

    override fun onBackClick() {
        sendEffect(UserReelUiEffect.NavigateBack)
    }

    override fun onDeleteClick() {
        // Todo
    }

    override fun onConfirmDeleteClick() {
        // Todo
    }

    fun onDismissDialog() {
        // Todo
    }
}
