package net.thechance.mena.wallet.data.utils

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.statement.HttpResponse
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

expect val platformHttpClientEngineFactory: HttpClientEngineFactory<HttpClientEngineConfig>

class ApiClient {
    private var client: HttpClient = buildClient()

    suspend fun get(
        urlString: String,
        block: HttpRequestBuilder.() -> Unit = {}
    ): HttpResponse {
        return client.get(urlString, block)
    }

    suspend fun post(
        urlString: String,
        block: HttpRequestBuilder.() -> Unit = {}
    ): HttpResponse {
        return client.post(urlString, block)
    }

    suspend fun put(
        urlString: String,
        block: HttpRequestBuilder.() -> Unit = {}
    ): HttpResponse {
        return client.put(urlString, block)
    }

    suspend fun delete(
        urlString: String,
        block: HttpRequestBuilder.() -> Unit = {}
    ): HttpResponse {
        return client.delete(urlString, block)
    }

    private fun buildClient(): HttpClient {
        return HttpClient(platformHttpClientEngineFactory) {
            defaultRequest { url(BASE_URL) }

            install(Logging) { level = LogLevel.ALL }

            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                        encodeDefaults = true
                    }
                )
            }

            install(HttpTimeout) {
                requestTimeoutMillis = TIME_OUT_INTERVAL_MILLI
                connectTimeoutMillis = TIME_OUT_INTERVAL_MILLI
                socketTimeoutMillis = TIME_OUT_INTERVAL_MILLI
            }
        }
    }

    companion object {
        private const val BASE_URL = "https://" //TODO: add base url
        private const val TIME_OUT_INTERVAL_MILLI = 15_000L
    }
}