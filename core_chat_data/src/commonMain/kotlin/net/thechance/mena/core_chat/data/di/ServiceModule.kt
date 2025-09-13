package net.thechance.mena.core_chat.data.di

import com.bilalazzam.contacts_provider.ContactsProvider
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import net.thechance.mena.core_chat.data.network.createHttpClient
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.dsl.module

internal val serviceModule = module {
    single(named("baseUrl")) { "http://10.0.2.2:8080" }
    single { createContactsProvider() }
    single { createHttpClientEngine() }
    single { createHttpClient(get(named("baseUrl"))) }
}

expect fun Scope.createContactsProvider(): ContactsProvider
expect fun Scope.createHttpClientEngine(): HttpClientEngineFactory<HttpClientEngineConfig>