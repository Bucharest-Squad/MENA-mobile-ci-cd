package net.thechance.mena.core_chat.data.di

import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.darwin.Darwin
import org.koin.core.scope.Scope

actual fun Scope.createHttpClientEngine(): HttpClientEngineFactory<HttpClientEngineConfig> {
    return Darwin
}