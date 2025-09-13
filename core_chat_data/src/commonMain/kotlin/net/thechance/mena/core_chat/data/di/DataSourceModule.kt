package net.thechance.mena.core_chat.data.di

import com.bilalazzam.contacts_provider.ContactsProvider
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import net.thechance.mena.core_chat.data.contacts.source.device.DeviceContactsDataSource
import net.thechance.mena.core_chat.data.contacts.source.remote.ContactsDataSource
import net.thechance.mena.core_chat.data.network.createHttpClient
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.module

internal val dataSourceModule = module {
    single(named("baseUrl")) { "http://192.168.88.15:8080" }
    single { createContactsProvider() }
    single { createHttpClientEngine() }
    single { createHttpClient(get(named("baseUrl"))) }
    single { ContactsDataSource(get(), get(named("baseUrl"))) }
    singleOf(::DeviceContactsDataSource)
}

expect fun Scope.createContactsProvider(): ContactsProvider
expect fun Scope.createHttpClientEngine(): HttpClientEngineFactory<HttpClientEngineConfig>