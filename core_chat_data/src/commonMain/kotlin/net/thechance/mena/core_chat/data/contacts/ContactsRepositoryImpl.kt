package net.thechance.mena.core_chat.data.contacts

import net.thechance.mena.core_chat.data.contacts.source.remote.ContactsRemoteDataSource
import net.thechance.mena.core_chat.data.shared.BaseRepository
import net.thechance.mena.core_chat.domain.entity.Contact
import net.thechance.mena.core_chat.domain.exception.ContactsException
import net.thechance.mena.core_chat.domain.model.PagedData
import net.thechance.mena.core_chat.domain.repository.ContactsRepository

class ContactsRepositoryImpl(
    private val contactsRemoteDataSource: ContactsRemoteDataSource
) : ContactsRepository, BaseRepository {

    override suspend fun getUserContacts(
        pageNumber: Int,
        pageSize: Int
    ): PagedData<Contact> {
        return safeCall {
            contactsRemoteDataSource.getUserContacts(
                pageNumber = pageNumber,
                pageSize = pageSize
            ).toPagedListOfContacts()
        }.getOrElse { throw ContactsException("Couldn't get user contacts", it) }
    }

    override suspend fun syncContacts(contacts: List<Contact>) {
        return safeCall {
            contactsRemoteDataSource.syncContacts(
                contacts = contacts.toListOfContactCreationRequestDto()
            ).successOrThrow("Couldn't sync user contacts")
        }.getOrElse { throw ContactsException("Couldn't sync user contacts", it) }
    }
}