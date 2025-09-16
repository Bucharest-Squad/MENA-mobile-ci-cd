package net.thechance.mena.core_chat.presentation.screen.contacts

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import net.thechance.mena.core_chat.domain.entity.Contact
import net.thechance.mena.core_chat.domain.repository.ContactsRepository
import net.thechance.mena.core_chat.presentation.shared.BasePagingSource
import net.thechance.mena.core_chat.presentation.shared.BaseViewModel

class ContactsViewModel(
    private val contactsRepository: ContactsRepository,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel<ContactsUiState, ContactsScreenEffect>(ContactsUiState()),
    ContactsScreenInteractionListener {

    init {
        observeSyncSuccess()
        getContacts()
    }

    private fun observeSyncSuccess() {
        tryToCollect(
            collect = { savedStateHandle.getStateFlow("is_sync_success", false) },
            onCollect = { isSynced ->
                if (isSynced == true) {
                    getContacts()
                    savedStateHandle["is_sync_success"] = false
                }
            }
        )
    }

    fun getContacts() {
        val contactsFlow = createPagingFlow(
            pagingSourceFactory = {
                BasePagingSource(
                    onError = { throwable ->
                        updateState {
                            it.copy(error = throwable.message)
                        }
                    },
                    fetchItems = { page, pageSize ->
                        contactsRepository.getUserContacts(page, pageSize)
                    }
                )
            },
            mapper = { contact: Contact -> contact.toUiModel() }
        ).cachedIn(viewModelScope)
        updateState { it.copy(contacts = contactsFlow) }
    }

    override fun onBackClick() {
        emitEffect(ContactsScreenEffect.NavigateBack)
    }

    override fun onResyncClick() {
        emitEffect(ContactsScreenEffect.NavigateToSyncContacts)
    }

    override fun onContactClick(contactId: Int) {
        emitEffect(ContactsScreenEffect.NavigateToChatScreen(contactId))
    }
}