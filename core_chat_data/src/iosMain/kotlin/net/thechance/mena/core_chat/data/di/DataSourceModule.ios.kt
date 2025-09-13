package net.thechance.mena.core_chat.data.di

import com.bilalazzam.contacts_provider.ContactsProvider
import com.bilalazzam.contacts_provider.ContactsProviderFactory
import org.koin.core.scope.Scope

actual fun Scope.createContactsProvider(): ContactsProvider {
    return ContactsProviderFactory().createContactsProvider()
}
