package net.thechance.mena.core_chat.presentation.screen.chats

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import net.thechance.mena.core_chat.domain.repository.ContactsRepository
import net.thechance.mena.core_chat.presentation.shared.BaseViewModel

class ChatsViewModel(
    private val contactsRepository: ContactsRepository
) : BaseViewModel<ChatsUiState>(ChatsUiState()) {

    private val _effect = MutableSharedFlow<ChatsScreenEffect>()
    val effect: SharedFlow<ChatsScreenEffect> = _effect.asSharedFlow()

    fun onNewChatClicked() {
        tryToExecute(
            onStart = { updateState { it.copy(isLoading = false) } },
            execute = { contactsRepository.getUserSyncedState() },
            onSuccess = { isSynced ->
                updateState { it.copy(isSynced = isSynced, isLoading = false) }
                if (isSynced) {
                    emitEffect(ChatsScreenEffect.NavigateToContacts)
                } else {
                    emitEffect(ChatsScreenEffect.NavigateToSyncContacts)
                }
            }
        )
    }

    private fun emitEffect(effect: ChatsScreenEffect) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }
}

data class ChatsUiState(
    val isLoading: Boolean = false,
    val isSynced: Boolean = false
)

sealed interface ChatsScreenEffect {
    object NavigateToContacts : ChatsScreenEffect
    object NavigateToSyncContacts : ChatsScreenEffect
}