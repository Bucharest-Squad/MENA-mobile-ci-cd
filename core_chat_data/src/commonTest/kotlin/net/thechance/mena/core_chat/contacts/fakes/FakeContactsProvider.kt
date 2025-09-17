package net.thechance.mena.core_chat.contacts.fakes

import com.bilalazzam.contacts_provider.Contact
import com.bilalazzam.contacts_provider.ContactField
import com.bilalazzam.contacts_provider.ContactsProvider
import com.bilalazzam.contacts_provider.Contact as DeviceContact


class FakeContactsProvider(
    private val contacts: List<DeviceContact>
) : ContactsProvider {
    override suspend fun getAllContacts(fields: Set<ContactField>): List<DeviceContact> {
        return contacts
    }
}