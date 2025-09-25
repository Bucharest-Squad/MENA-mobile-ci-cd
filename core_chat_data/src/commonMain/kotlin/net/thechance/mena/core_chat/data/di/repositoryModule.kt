package net.thechance.mena.core_chat.data.di

import net.thechance.mena.core_chat.data.chat.ChatRepositoryImpl
import net.thechance.mena.core_chat.data.contacts.ContactsRepositoryImpl
import net.thechance.mena.core_chat.domain.repository.ChatRepository
import net.thechance.mena.core_chat.domain.repository.ContactsRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val repositoryModule = module {
    singleOf(::ContactsRepositoryImpl) bind ContactsRepository::class
    singleOf(::ChatRepositoryImpl) bind ChatRepository::class
}
