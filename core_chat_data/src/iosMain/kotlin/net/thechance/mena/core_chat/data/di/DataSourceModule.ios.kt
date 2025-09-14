package net.thechance.mena.core_chat.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.bilalazzam.contacts_provider.ContactsProvider
import com.bilalazzam.contacts_provider.ContactsProviderFactory
import net.thechance.mena.core_chat.data.utils.createDataStore
import org.koin.core.scope.Scope

actual fun Scope.createContactsProvider(): ContactsProvider {
    return ContactsProviderFactory().createContactsProvider()
}

actual fun Scope.createSettingsDataStore(): DataStore<Preferences>{
    return createDataStore()
}