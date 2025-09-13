package net.thechance.mena.core_chat.data.di

import com.bilalazzam.contacts_provider.ContactsProvider
import org.koin.core.scope.Scope
import org.koin.dsl.module

internal val dataProviderModule = module {
    single { createContactsProvider() }
}

expect fun Scope.createContactsProvider(): ContactsProvider
