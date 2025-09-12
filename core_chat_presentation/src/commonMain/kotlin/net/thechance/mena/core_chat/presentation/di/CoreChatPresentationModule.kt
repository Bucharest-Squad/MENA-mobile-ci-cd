package net.thechance.mena.core_chat.presentation.di

import org.koin.dsl.module
import net.thechance.mena.core_chat.api.CoreChatApi
import net.thechance.mena.core_chat.presentation.api.CoreChatApiImp
import net.thechance.mena.core_chat.presentation.screen.contacts.ContactsViewModel
import net.thechance.mena.core_chat.presentation.screen.syncContacts.SyncContactsViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind

val coreChatPresentationModule = module {
    factoryOf(::CoreChatApiImp) bind CoreChatApi::class
    viewModelOf(::ContactsViewModel)
    viewModelOf(::SyncContactsViewModel)
}