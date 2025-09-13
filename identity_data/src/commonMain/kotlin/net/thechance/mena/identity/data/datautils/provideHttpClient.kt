package net.thechance.mena.identity.data.datautils

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun provideHttpClient(
    engine: HttpClientEngine
): HttpClient {
    return HttpClient(engine) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                })
        }
        defaultRequest {
            url(BuildConfig.BASE_URL)
        }

        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
    }
}

