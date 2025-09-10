package net.thechance.mena.core_chat.data.contacts

import net.thechance.mena.core_chat.data.contacts.source.remote.FakeContactsDataSource
import net.thechance.mena.core_chat.data.shared.BaseRepository
import net.thechance.mena.core_chat.domain.entity.Contact
import net.thechance.mena.core_chat.domain.exception.ContactSyncFailedException
import net.thechance.mena.core_chat.domain.exception.ContactsFetchFailedException
import net.thechance.mena.core_chat.domain.model.PagedData
import net.thechance.mena.core_chat.domain.repository.ContactsRepository

class ContactsRepositoryImpl(
    private val contactsDataSource: FakeContactsDataSource
) : ContactsRepository, BaseRepository {

    override suspend fun getUserContacts(pageNumber: Int, pageSize: Int): PagedData<Contact> {
        return tryNetworkCall(
            defaultException = { ContactsFetchFailedException("Couldn't get user contacts", it) }) {
            contactsDataSource.getUserContacts(
                pageNumber = pageNumber,
                pageSize = pageSize
            )
        }.toPagedListOfContacts()
    }

    override suspend fun syncContacts(contacts: List<Contact>) {
        tryNetworkCall(
            defaultException = { ContactSyncFailedException("Couldn't sync user contacts", it) }) {
            contactsDataSource.syncContacts(contacts.toListOfContactCreationRequestDto())
        }
    }
}