package net.thechance.mena.core_chat.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.bilalazzam.contacts_provider.ContactsProvider
import net.thechance.mena.core_chat.data.contacts.source.device.DeviceContactsDataSource
import net.thechance.mena.core_chat.data.contacts.source.device.SettingDataSource
import net.thechance.mena.core_chat.data.contacts.source.remote.DummyContactsDataSource
import org.koin.core.qualifier.qualifier
import org.koin.core.scope.Scope
import org.koin.dsl.module

const val SETTINGS_DATA_STORE = "settingsDataStore"

internal val dataSourceModule = module {
    single { createContactsProvider() }
    single(qualifier(name = SETTINGS_DATA_STORE)) { createSettingsDataStore() }
    single { SettingDataSource(get(qualifier(name = SETTINGS_DATA_STORE))) }
    single { DummyContactsDataSource() }
    single { DeviceContactsDataSource(get()) }
}

expect fun Scope.createContactsProvider(): ContactsProvider
expect fun Scope.createSettingsDataStore(): DataStore<Preferences>