package net.thechance.mena.core_chat.data.contacts

import net.thechance.mena.core_chat.data.contacts.source.remote.ContactsRemoteDataSource
import net.thechance.mena.core_chat.domain.entity.Contact
import net.thechance.mena.core_chat.domain.exception.ContactsException
import net.thechance.mena.core_chat.domain.repository.ContactsRepository

class ContactsRepositoryImpl(
    private val contactsRemoteDataSource: ContactsRemoteDataSource
) : ContactsRepository {

    override suspend fun getUserContacts(
        pageNumber: Int,
        pageSize: Int
    ): List<Contact> {
        return runCatching {
            contactsRemoteDataSource.getUserContacts(
                pageNumber = pageNumber,
                pageSize = pageSize
            ).toListOfContact()
        }.getOrElse {
            throw ContactsException("Couldn't get user contacts", it)
        }
    }

    override suspend fun syncContacts(contacts: List<Contact>): List<Contact> {
        return runCatching {
            contactsRemoteDataSource.syncContacts(
                contacts = contacts.toListOfContactToAddDto()
            ).toListOfContact()
        }.getOrElse {
            throw ContactsException("Couldn't sync user contacts", it)
        }
    }
}

