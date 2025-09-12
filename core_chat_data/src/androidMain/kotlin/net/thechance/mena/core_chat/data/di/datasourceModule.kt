package net.thechance.mena.core_chat.data.di

import android.content.Context
import com.bilalazzam.contacts_provider.ContactsProvider
import com.bilalazzam.contacts_provider.ContactsProviderFactory
import org.koin.dsl.module

actual val dataSourceModule = module {
    single<ContactsProvider> { ContactsProviderFactory(get<Context>()).createContactsProvider() }
}