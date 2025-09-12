package net.thechance.mena.core_chat.data.contacts.source.device

import com.bilalazzam.contacts_provider.Contact
import com.bilalazzam.contacts_provider.ContactsProvider
import com.bilalazzam.contacts_provider.ContactField.*

class DeviceContactsDataSource(private val contactsProvider: ContactsProvider) {
    suspend fun getDeviceContacts(): List<Contact> {
        return contactsProvider.getAllContacts(
            fields = setOf(
                ID,
                FIRST_NAME,
                LAST_NAME,
                PHONE_NUMBERS
            )
        )
    }
}