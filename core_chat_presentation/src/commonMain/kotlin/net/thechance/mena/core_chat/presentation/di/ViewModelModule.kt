package net.thechance.mena.core_chat.presentation.di

import androidx.lifecycle.SavedStateHandle
import net.thechance.mena.core_chat.presentation.screen.chats.ChatsViewModel
import net.thechance.mena.core_chat.presentation.screen.contacts.ContactsViewModel
import net.thechance.mena.core_chat.presentation.screen.syncContacts.SyncContactsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

internal val viewModelModule = module {
    viewModelOf(::ChatsViewModel)
    viewModel { (handle: SavedStateHandle) ->
        ContactsViewModel(
            savedHandle = handle,
            contactsRepository = get()
        )
    }
    viewModelOf(::SyncContactsViewModel)
}