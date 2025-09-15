package net.thechance.mena.core_chat.presentation.screen.syncContacts

import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import net.thechance.mena.core_chat.domain.repository.ContactsRepository
import net.thechance.mena.core_chat.presentation.shared.BaseViewModel

class SyncContactsViewModel(
    private val contactsRepository: ContactsRepository,
    private val permissionsController: PermissionsController
) : BaseViewModel<SyncContactsUiState, SyncContactsScreenEffect>(SyncContactsUiState()) {


    fun onForceSyncContacts(){
        syncContacts()
    }

    fun onSyncContactsClicked() {
        tryToExecute(
            execute = { permissionsController.providePermission(Permission.CONTACTS) },
            onSuccess = { syncContacts() },
            onError = { throwable ->
                updateState {
                    it.copy(
                        isLoading = false,
                        error = throwable.message
                    )
                }
            },
        )
    }

    private fun syncContacts() {
        tryToExecute(
            onStart = {
                updateState {
                    it.copy(
                        isLoading = true,
                        isSyncFinished = false,
                        showSyncView = true
                    )
                }
            },
            execute = {
                contactsRepository.syncContacts()
            },
            onSuccess = {
                updateState {
                    it.copy(
                        isSyncFinished = true
                    )
                }
                contactsRepository.setUserSyncedState(true)
                if(state.value.isFirstSynced) {
                    emitEffect(SyncContactsScreenEffect.NavigateToContacts)
                } else {
                    emitEffect(SyncContactsScreenEffect.NavigateBackWithResult)
                }
            },
            onError = { throwable ->
                updateState { it.copy(
                    isLoading = false,
                    error = throwable.message)
                }
            }
        )
    }
}

