package net.thechance.mena.core_chat.data.di

import net.thechance.mena.core_chat.data.contacts.ContactsRepositoryImpl
import net.thechance.mena.core_chat.domain.repository.ContactsRepository
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

internal val repositoryModule = module {
    single { ContactsRepositoryImpl(baseUrl = get(named("baseUrl")), get(), get()) } bind ContactsRepository::class
}
