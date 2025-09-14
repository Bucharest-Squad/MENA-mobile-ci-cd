package net.thechance.mena.trends.data.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

expect fun createHttpEngine(): HttpClientEngine

@Module
class NetworkModule {
    @Single
    fun provideHttpClient(engine: HttpClientEngine): HttpClient {
        return HttpClient(engine) {
            defaultRequest {
                // TODO: add base url
            }

            install(Logging) {
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        println("Http client: $message")
                    }
                }
            }

            install(HttpTimeout) {
                connectTimeoutMillis = TIME_OUT_INTERVAL_MILLI
                requestTimeoutMillis = TIME_OUT_INTERVAL_MILLI
            }

            install(ContentNegotiation) {
                json(
                    Json {
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }
        }
    }

    @Single
    fun provideHttpEngine(): HttpClientEngine = createHttpEngine()

    private companion object {
        const val TIME_OUT_INTERVAL_MILLI = 15_000L
    }
}