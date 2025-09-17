package net.thechance.mena.core_chat.data.contacts

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.bilalazzam.contacts_provider.ContactsProvider
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestData
import io.ktor.client.request.HttpResponseData
import io.ktor.http.Headers
import io.ktor.http.HttpStatusCode
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import net.thechance.mena.core_chat.data.contacts.dto.ContactDto
import net.thechance.mena.core_chat.data.network.ApiConstants.CONTACTS_ENDPOINT
import net.thechance.mena.core_chat.data.network.ApiConstants.SYNC_CONTACTS_ENDPOINT
import net.thechance.mena.core_chat.data.shared.dto.BaseResponseDto
import net.thechance.mena.core_chat.data.shared.dto.PagedDataDto

inline fun <reified T> successResponse(json: Json, body: T): String {
    return json.encodeToString(
        BaseResponseDto.serializer(serializer<T>()),
        BaseResponseDto(body = body, status = 200, success = true)
    )
}

inline fun <reified T> errorResponse(json: Json, status: Int, message: String): String {
    return json.encodeToString(
        BaseResponseDto.serializer(serializer<T>()),
        BaseResponseDto(status = status, success = false, message = message)
    )
}

private fun createClient(
    json: Json,
    handler: suspend MockRequestHandleScope.(HttpRequestData) -> HttpResponseData
): HttpClient {
    val engine = MockEngine { request -> handler(this, request) }
    return HttpClient(engine) {
        install(ContentNegotiation) { json(json) }
    }
}

fun createRepository(
    contactsProvider: ContactsProvider,
    contactsDataStore: DataStore<Preferences>,
    json: Json,
    requestEndPoint: String = CONTACTS_ENDPOINT,
    response: suspend MockRequestHandleScope.() -> HttpResponseData
): ContactsRepositoryImpl {
    return ContactsRepositoryImpl(
        client = createClient(json) { request ->
            if (request.url.encodedPath == requestEndPoint) {
                response()
            } else {
                respond(content = "", status = HttpStatusCode.BadRequest)
            }
        },
        contactsProvider = contactsProvider,
        dataStore = contactsDataStore
    )
}

fun createHttpClient(
    headers: Headers,
    json: Json,
) = HttpClient(MockEngine { request ->
    when (request.url.encodedPath) {
        CONTACTS_ENDPOINT -> respond(
            content = successResponse<PagedDataDto<ContactDto>>(
                json = json,
                body = PagedDataDto(
                    data = listOf(
                        ContactDto(
                            name = "Bilal Azzam",
                            phoneNumber = "01026388780",
                            isMenaMember = true,
                            imageUrl = "http://example.com/image.jpg"
                        )
                    ),
                    pageNumber = 1,
                    pageSize = 10,
                    totalItems = 1,
                    totalPages = 1
                )
            ),
            status = HttpStatusCode.OK,
            headers = headers
        )

        SYNC_CONTACTS_ENDPOINT -> respond(
            content = successResponse<Unit>(
                json = json,
                body = Unit
            ),
            status = HttpStatusCode.OK,
            headers = headers
        )

        else -> respond(
            content = "",
            status = HttpStatusCode.BadRequest,
            headers = headers
        )
    }
}) {
    install(ContentNegotiation) {
        json(json)
    }
}