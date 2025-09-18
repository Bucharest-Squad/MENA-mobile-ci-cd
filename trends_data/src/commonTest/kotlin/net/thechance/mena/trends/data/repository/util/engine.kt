package net.thechance.mena.trends.data.repository.util

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpResponseData
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import net.thechance.mena.trends.data.repository.CategoryRepositoryImpl

val jsonSerialization = Json { ignoreUnknownKeys = true }
val jsonHeaders = headersOf(HttpHeaders.ContentType, ContentType.Application.Json.toString())

fun createCategoryRepository(
    getAllCategories: (suspend MockRequestHandleScope.() -> HttpResponseData)? = null,
    updateInterests: (suspend MockRequestHandleScope.() -> HttpResponseData)? = null,
): CategoryRepositoryImpl {
    return CategoryRepositoryImpl(
        createHttpClient(
            getAllCategories,
            updateInterests
        )
    )
}

fun createHttpClient(
    getAllCategories: (suspend MockRequestHandleScope.() -> HttpResponseData)? = null,
    updateInterests: (suspend MockRequestHandleScope.() -> HttpResponseData)? = null,
): HttpClient {
    return HttpClient(
        MockEngine { request ->
            when (request.url.encodedPath) {
                "/trends/category", "/trends/interests/user" -> {
                    getAllCategories?.invoke(this) ?: getAllCategoriesResponse()
                }

                "/trends/interests" -> updateInterests?.invoke(this) ?: updateInterestsResponse()

                else -> respond("", HttpStatusCode.BadRequest, jsonHeaders)
            }
        }
    ) {
        install(ContentNegotiation) { json(jsonSerialization) }
        install(DefaultRequest) { contentType(ContentType.Application.Json) }
    }
}
