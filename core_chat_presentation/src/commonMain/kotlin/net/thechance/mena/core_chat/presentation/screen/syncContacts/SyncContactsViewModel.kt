package net.thechance.mena.core_chat.presentation.screen.syncContacts

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import mena.core_chat_presentation.generated.resources.Res
import mena.core_chat_presentation.generated.resources.contacts_permission_required_message
import mena.core_chat_presentation.generated.resources.permission_denied_title
import mena.core_chat_presentation.generated.resources.something_went_wrong
import net.thechance.mena.core_chat.domain.repository.ContactsRepository
import net.thechance.mena.core_chat.presentation.components.SnackBarData
import net.thechance.mena.core_chat.presentation.navigation.ChatEffector
import net.thechance.mena.core_chat.presentation.navigation.ContactsRoute
import net.thechance.mena.core_chat.presentation.navigation.SyncContactsRoute
import net.thechance.mena.core_chat.presentation.shared.BaseViewModel
import org.jetbrains.compose.resources.getString

class SyncContactsViewModel(
    private val contactsRepository: ContactsRepository,
    private val permissionsController: PermissionsController,
    savedStateHandle: SavedStateHandle,
    effector: ChatEffector
) : BaseViewModel<SyncContactsState>(SyncContactsState(), effector),
    SyncContactsScreenInteractionListener {

    private val forceSync: Boolean = savedStateHandle.toRoute<SyncContactsRoute>().forceSync

    init {
        onForceSync()
    }

    fun onForceSync() {
        if (forceSync) {
            updateState { it.copy(isFirstSync = false) }
            syncContacts()
        } else {
            updateState { it.copy(isFirstSync = true, showSyncView = true, isLoading = false) }
        }
    }

    override fun onBackClick() {
        popBackStack()
    }

    override fun onSyncClick() {
        tryToExecute(
            execute = { permissionsController.providePermission(Permission.CONTACTS) },
            onSuccess = { syncContacts() },
            onError = ::handlePermissionError
        )
    }

    private fun handlePermissionError(throwable: Throwable) {
        when (throwable) {
            is DeniedAlwaysException -> {
                updateState {
                    it.copy(
                        isLoading = false,
                        deniedPermanently = true,
                    )
                }
                showSnackBar(
                    snackBarData = SnackBarData(
                        title = Res.string.permission_denied_title,
                        message = Res.string.contacts_permission_required_message,
                    )
                )
            }

            is DeniedException -> {
                updateState { it.copy(isLoading = false) }
                showSnackBar(
                    SnackBarData(
                        title = "Permission denied",
                        message = "Contacts permission is required to sync contacts",
                    )
                )
            }

            else -> onError(throwable)
        }
    }

    private fun syncContacts() {
        tryToExecute(
            onStart = { updateState { it.copy(isLoading = true, showSyncView = true) } },
            execute = { contactsRepository.syncContacts() },
            onSuccess = { onSyncContactsSuccess() },
            onError = ::onError
        )
    }

    private suspend fun onSyncContactsSuccess() {
        if (state.value.isFirstSync) {
            contactsRepository.setUserSyncedState(true)
            popBackStack()
            navigate(ContactsRoute)
        } else {
            popBackStack("is_sync_success" to true)
        }

        val g:String = getString(Res.string.something_went_wrong)
    }

    private fun onError(throwable: Throwable) {
        updateState {
            it.copy(
                isLoading = false,
            )
        }
        showSnackBar(
            SnackBarData(
                title = "Something went wrong",
                message = throwable.message ?: "Unknown error",
            )
        )
    }
}

