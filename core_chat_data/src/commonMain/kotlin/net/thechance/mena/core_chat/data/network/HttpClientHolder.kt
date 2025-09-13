package net.thechance.mena.core_chat.data.network

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

expect val platformHttpClientEngineFactory: HttpClientEngineFactory<HttpClientEngineConfig>

val httpClient = HttpClient(platformHttpClientEngineFactory) {
    defaultRequest { url(BASE_URL) }
    install(ContentNegotiation) {
        json(
            Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            }
        )
    }
    install(Logging) { level = LogLevel.ALL }

    install(HttpTimeout) {
        requestTimeoutMillis = TIME_OUT_INTERVAL_MILLI
        connectTimeoutMillis = TIME_OUT_INTERVAL_MILLI
        socketTimeoutMillis = TIME_OUT_INTERVAL_MILLI
    }
}

private const val BASE_URL = "localhost:8080/"
private const val TIME_OUT_INTERVAL_MILLI = 30_000L
