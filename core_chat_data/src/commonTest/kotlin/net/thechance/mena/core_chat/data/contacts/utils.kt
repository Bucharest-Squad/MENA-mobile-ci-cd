package net.thechance.mena.core_chat.data.contacts

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.bilalazzam.contacts_provider.ContactsProvider
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockRequestHandleScope
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpResponseData
import io.ktor.http.ContentType
import io.ktor.http.Headers
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
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

fun MockRequestHandleScope.defaultContactsResponse(json: Json, headers: Headers) = respond(
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

fun MockRequestHandleScope.defaultSyncContactsResponse(json: Json, headers: Headers) = respond(
    content = successResponse<Unit>(
        json = json,
        body = Unit
    ),
    status = HttpStatusCode.OK,
    headers = headers
)

fun createRepository(
    contactsProvider: ContactsProvider,
    contactsDataStore: DataStore<Preferences>,
    json: Json,
    jsonHeaders: Headers,
    contactsResponse: (suspend MockRequestHandleScope.() -> HttpResponseData)? = null,
    syncContactsResponse: (suspend MockRequestHandleScope.() -> HttpResponseData)? = null
): ContactsRepositoryImpl {
    return ContactsRepositoryImpl(
        client = createHttpClient(
            headers = jsonHeaders,
            json = json,
            contactsResponse = contactsResponse,
            syncContactsResponse = syncContactsResponse
        ),
        contactsProvider = contactsProvider,
        dataStore = contactsDataStore
    )
}

fun createHttpClient(
    headers: Headers,
    json: Json,
    contactsResponse: (suspend MockRequestHandleScope.() -> HttpResponseData)? = null,
    syncContactsResponse: (suspend MockRequestHandleScope.() -> HttpResponseData)? = null
) = HttpClient(MockEngine { request ->
    when (request.url.encodedPath) {
        CONTACTS_ENDPOINT -> if (contactsResponse == null)
            defaultContactsResponse(json, headers)
        else
            contactsResponse()

        SYNC_CONTACTS_ENDPOINT -> if (syncContactsResponse == null)
            defaultSyncContactsResponse(json, headers)
        else
            syncContactsResponse()

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
    install(DefaultRequest) {
        contentType(ContentType.Application.Json)
    }
}