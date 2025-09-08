package net.thechance.mena.core_chat.domain.repository

import net.thechance.mena.core_chat.domain.entity.Contact

interface ContactsRepository {
    suspend fun getUserContacts(userId: String, pageNumber: Int, pageSize: Int): List<Contact>
    suspend fun syncContacts(userId: String, contacts: List<Contact>): List<Contact>
}