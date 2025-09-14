package net.thechance.mena.core_chat.data.contacts

import kotlinx.coroutines.flow.Flow
import net.thechance.mena.core_chat.data.contacts.source.device.DeviceContactsDataSource
import net.thechance.mena.core_chat.data.contacts.source.device.SettingDataSource
import net.thechance.mena.core_chat.data.contacts.source.remote.DummyContactsDataSource
import net.thechance.mena.core_chat.data.shared.BaseRepository
import net.thechance.mena.core_chat.domain.entity.Contact
import net.thechance.mena.core_chat.domain.exception.ContactSyncFailedException
import net.thechance.mena.core_chat.domain.exception.ContactsFetchFailedException
import net.thechance.mena.core_chat.domain.model.PagedData
import net.thechance.mena.core_chat.domain.repository.ContactsRepository

class ContactsRepositoryImpl(
    private val contactsDataSource: DummyContactsDataSource,
    private val deviceContactsDataSource: DeviceContactsDataSource,
    private val settingDataSource: SettingDataSource
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

    override suspend fun syncContacts() {
        tryNetworkCall(
            defaultException = { ContactSyncFailedException("Couldn't sync user contacts", it) }) {
            val contacts = deviceContactsDataSource.getDeviceContacts()
            contactsDataSource.syncContacts(contacts.toListOfContactCreationRequestDto())
        }
    }

    override suspend fun getUserSyncedState(): Boolean {
        return safeDataStoreCall{
            settingDataSource.getUserSyncedState()
        }
    }

    override suspend fun setUserSyncedState(state: Boolean) {
        return safeDataStoreCall {
            settingDataSource.setUserSyncedState(state)
        }
    }
}