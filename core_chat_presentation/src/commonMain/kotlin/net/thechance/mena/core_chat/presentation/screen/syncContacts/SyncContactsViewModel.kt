package net.thechance.mena.core_chat.presentation.screen.syncContacts

import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import net.thechance.mena.core_chat.domain.repository.ContactsRepository
import net.thechance.mena.core_chat.presentation.shared.BaseViewModel

class SyncContactsViewModel(
    private val contactsRepository: ContactsRepository,
    private val permissionsController: PermissionsController
) : BaseViewModel<SyncContactsUiState>(SyncContactsUiState()) {

    init {
        onInit()
    }

    fun onInit() {
        tryToExecute(
            execute = { contactsRepository.getUserSyncedState() },
            onSuccess = { isSynced ->
                if (isSynced) {
                    updateState { it.copy(
                        showSyncView = true,
                        isFirstSynced = false)
                    }
                    syncContacts()
                } else {
                    updateState { it.copy(
                        showSyncView = true,
                        isFirstSynced = true)
                    }
                }
            },
            onError = { println(it.printStackTrace()) }
        )
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
                        isSyncFinished = false
                    )
                }
            },
            execute = {
                contactsRepository.syncContacts()
            },
            onSuccess = {
                updateState {
                    it.copy(
                        isLoading = false,
                        isSyncFinished = true
                    )
                }
                contactsRepository.setUserSyncedState(true)
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

