package net.thechance.mena.trends.presentation.screen.user_reel

import net.thechance.mena.trends.domain.repository.ReelRepository
import net.thechance.mena.trends.presentation.shared.base.BaseViewModel
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Provided

@KoinViewModel
class UserReelViewModel(
    @Provided private val reelRepository: ReelRepository
) : BaseViewModel<UserReelState, UserReelEffect>(UserReelState()),
    UserReelInteractionListener {


    private fun handleError(throwable: Throwable) {
        val errorRes = when (throwable) {
            //TODO() WILL HANDLE EXCEPTIONS
            else -> {}
        }
    }

    override fun onDescriptionClick(isCollapsed: Boolean) {
        updateState {
            copy(isDescriptionExpanded = !isCollapsed)
        }
    }

    override fun onBackClick() {
        sendEffect(UserReelEffect.NavigateBack)
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
