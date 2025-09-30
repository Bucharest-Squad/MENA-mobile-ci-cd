package net.thechance.mena.core_chat.data.di

import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import kotlinx.serialization.json.Json
import net.thechance.mena.core_chat.data.network.createHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val networkModule = module {
    single {
        Json {
            ignoreUnknownKeys = true
            prettyPrint = true
            isLenient = true
        }
    }

    single { createHttpClientEngine() }
    single(named("chatClient")) { createHttpClient(get(named("baseUrl")), get()) }
}

expect fun createHttpClientEngine(): HttpClientEngineFactory<HttpClientEngineConfig>