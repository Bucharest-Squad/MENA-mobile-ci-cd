
package net.thechance.mena.core_chat.presentation.screen.contacts

import net.thechance.mena.core_chat.domain.entity.Contact
import net.thechance.mena.core_chat.domain.repository.ContactsRepository
import net.thechance.mena.core_chat.presentation.shared.BasePagingSource
import net.thechance.mena.core_chat.presentation.shared.BaseViewModel

class ContactsViewModel(
    private val contactsRepository: ContactsRepository
) : BaseViewModel<ContactsUiState>(ContactsUiState()) {

    init {
        getContacts()
    }

    fun getContacts() {
        val contactsFlow = createPagingFlow(
            pagingSourceFactory = {
                BasePagingSource(
                    onError = { throwable -> updateState {
                        it.copy(error = throwable.message)
                    } },
                    fetchItems = { page, pageSize ->
                        contactsRepository.getUserContacts(page, pageSize)
                    }
                )
            },
            mapper = { contact: Contact -> contact.toUiModel() }
        )
        updateState { it.copy(contacts = contactsFlow) }
    }
}

