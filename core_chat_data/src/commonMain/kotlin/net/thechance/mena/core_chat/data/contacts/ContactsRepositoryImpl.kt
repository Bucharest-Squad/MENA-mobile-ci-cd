package net.thechance.mena.core_chat.data.contacts

import net.thechance.mena.core_chat.data.contacts.source.remote.ContactsRemoteDataSource
import net.thechance.mena.core_chat.domain.entity.Contact
import net.thechance.mena.core_chat.domain.exception.FailException
import net.thechance.mena.core_chat.domain.repository.ContactsRepository

class ContactsRepositoryImpl(
    private val contactsRemoteDataSource: ContactsRemoteDataSource
) : ContactsRepository {

    override suspend fun getUserContacts(
        userId: String,
        pageNumber: Int,
        pageSize: Int
    ): List<Contact> {
        return runCatching {
            contactsRemoteDataSource.getUserContacts(
                userId = userId,
                pageNumber = pageNumber,
                pageSize = pageSize
            ).toListOfContact()
        }.getOrElse {
            throw FailException("Couldn't get user $userId contacts", it)
        }
    }

    override suspend fun syncContacts(userId: String, contacts: List<Contact>): List<Contact> {
        return runCatching {
            contactsRemoteDataSource.syncContacts(userId, contacts.toListOfContactToAddDto())
                .toListOfContact()
        }.getOrElse {
            throw FailException("Couldn't sync user $userId contacts", it)
        }
    }
}

