package net.thechance.mena.core_chat.presentation.screen.chats

import net.thechance.mena.core_chat.domain.repository.ContactsRepository
import net.thechance.mena.core_chat.presentation.navigation.ContactsRoute
import net.thechance.mena.core_chat.presentation.navigation.SyncContactsRoute
import net.thechance.mena.core_chat.presentation.shared.BaseViewModel

class ChatsViewModel(
    private val contactsRepository: ContactsRepository
) : BaseViewModel<ChatsUiState>(ChatsUiState()) {

    fun onNewChatClicked() {
        tryToExecute(
            onStart = { updateState { it.copy(isLoading = false) } },
            execute = { contactsRepository.getUserSyncedState() },
            onSuccess = { isSynced ->
                updateState { it.copy(isSynced = isSynced, isLoading = false) }
                if (isSynced) {
                    navigate(ContactsRoute)
                } else {
                    navigate(SyncContactsRoute(forceSync = false))
                }
            }
        )
    }
}


