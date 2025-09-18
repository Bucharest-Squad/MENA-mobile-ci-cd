package net.thechance.mena.trends.presentation.screen.user_reel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import net.thechance.mena.trends.domain.repository.ReelsRepository
import net.thechance.mena.trends.presentation.shared.base.BaseViewModel
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Provided

@KoinViewModel
class UserReelViewModel(
    @Provided private val reelsRepository: ReelsRepository
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
        updateState {
            copy(isConfirmationDialogVisible = true)
        }
    }

    override fun onConfirmDeleteClick() {
        viewModelScope.launch {
            val reelId = state.value.id
            reelsRepository.deleteReelById(reelId)

            updateState {
                copy(isConfirmationDialogVisible = false, isReelDeleted = true)
            }
        }
    }

    override fun onDismissDialog() {
        updateState {
            copy(isConfirmationDialogVisible = false)
        }
    }
}