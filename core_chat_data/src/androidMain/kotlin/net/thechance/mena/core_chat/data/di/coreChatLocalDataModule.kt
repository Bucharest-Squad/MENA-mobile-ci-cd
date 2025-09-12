package net.thechance.mena.core_chat.data.di

import com.bilalazzam.contacts_provider.ContactsProvider
import com.bilalazzam.contacts_provider.ContactsProviderFactory
import org.koin.dsl.module

actual val datasourceModule = module {
    single<ContactsProvider> { ContactsProviderFactory(get()).createContactsProvider() }
}