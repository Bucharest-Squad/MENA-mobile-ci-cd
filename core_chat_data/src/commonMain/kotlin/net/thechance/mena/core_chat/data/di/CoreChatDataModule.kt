package net.thechance.mena.core_chat.data.di

import org.koin.dsl.module
import net.thechance.mena.core_chat.data.contacts.ContactsRepositoryImpl
import net.thechance.mena.core_chat.data.contacts.source.remote.DummyContactsDataSource
import net.thechance.mena.core_chat.domain.repository.ContactsRepository
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind

val coreChatDataModule = module {
    factoryOf(::DummyContactsDataSource)
    factoryOf(::ContactsRepositoryImpl) bind ContactsRepository::class
}