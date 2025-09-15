
package net.thechance.mena.core_chat.presentation.screen.syncContacts

import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import net.thechance.mena.core_chat.domain.repository.ContactsRepository
import net.thechance.mena.core_chat.presentation.shared.BaseViewModel

class SyncContactsViewModel(
    private val contactsRepository: ContactsRepository,
    private val permissionsController: PermissionsController
) : BaseViewModel<SyncContactsUiState>(SyncContactsUiState()) {

    fun onSyncContactsClicked() {
        tryToExecute(
            execute = { permissionsController.providePermission(Permission.CONTACTS) },
            onSuccess = { syncContacts() },
            onError = { throwable ->
                updateState { it.copy(
                    isLoading = false,
                    error = throwable.message)
                }
            },
        )
    }

    private fun syncContacts() {
        tryToExecute(
            onStart = {
                println("Starting sync...")
                updateState { it.copy(
                    isLoading = true,
                    isSyncFinished = false)
                }
            },
            execute = {
                contactsRepository.syncContacts()
            },
            onSuccess = {
                println("Sync successful, updating state.")
                updateState { it.copy(
                        isLoading = false,
                        isSyncFinished = true)
                }
            },
            onError = { throwable ->
                println("Sync failed: ${throwable.message}")
                updateState { it.copy(isLoading = false, error = throwable.message) }
            }
        )
    }
}

