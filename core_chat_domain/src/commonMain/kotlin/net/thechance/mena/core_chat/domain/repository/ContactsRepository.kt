package net.thechance.mena.core_chat.domain.repository

import net.thechance.mena.core_chat.domain.entity.Contact

interface ContactsRepository {
    suspend fun getUserContacts(pageNumber: Int, pageSize: Int): List<Contact>
    suspend fun syncContacts(contacts: List<Contact>): List<Contact>
}