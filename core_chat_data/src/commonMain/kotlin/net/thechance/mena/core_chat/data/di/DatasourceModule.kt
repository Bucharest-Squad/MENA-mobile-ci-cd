package net.thechance.mena.core_chat.data.di

import net.thechance.mena.core_chat.data.contacts.source.remote.DummyContactsDataSource
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val dataSourceModule = module {
    singleOf(::DummyContactsDataSource)
}