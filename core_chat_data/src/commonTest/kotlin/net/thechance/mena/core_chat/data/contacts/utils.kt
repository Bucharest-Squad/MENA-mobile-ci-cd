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
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer
import net.thechance.mena.core_chat.data.contacts.dto.ContactDto
import net.thechance.mena.core_chat.data.contacts.fakes.sampleContactDto
import net.thechance.mena.core_chat.data.network.ApiConstants.CONTACTS_ENDPOINT
import net.thechance.mena.core_chat.data.network.ApiConstants.SYNC_CONTACTS_ENDPOINT
import net.thechance.mena.core_chat.data.shared.dto.BaseResponseDto
import net.thechance.mena.core_chat.data.shared.dto.PagedDataDto

val jsonSerialization = Json { ignoreUnknownKeys = true }
val jsonHeaders = headersOf(
    HttpHeaders.ContentType,
    ContentType.Application.Json.toString()
)

inline fun <reified T> successResponse(
    body: T
): String {
    return jsonSerialization.encodeToString(
        BaseResponseDto.serializer(serializer<T>()),
        BaseResponseDto(body = body, status = 200, success = true)
    )
}

inline fun <reified T> errorResponse(
    status: Int,
    message: String = ""
): String {
    return jsonSerialization.encodeToString(
        BaseResponseDto.serializer(serializer<T>()),
        BaseResponseDto(status = status, success = false, message = message)
    )
}

inline fun <reified T> MockRequestHandleScope.mockSuccessPagedResponse(
    body: PagedDataDto<T>
): HttpResponseData {
    return respond(
        content = successResponse(body),
        status = HttpStatusCode.OK,
        headers = jsonHeaders
    )
}

inline fun <reified T> MockRequestHandleScope.mockErrorPagedResponse(
    status: Int,
    message: String = ""
): HttpResponseData {
    return respond(
        content = errorResponse<T>(status, message),
        status = HttpStatusCode.OK,
        headers = jsonHeaders
    )
}


fun MockRequestHandleScope.defaultContactsResponse() = respond(
    content = successResponse<PagedDataDto<ContactDto>>(
        body = PagedDataDto(
            data = listOf(
                sampleContactDto
            ),
            pageNumber = 1,
            pageSize = 10,
            totalItems = 1,
            totalPages = 1
        )
    ),
    status = HttpStatusCode.OK,
    headers = jsonHeaders
)

fun MockRequestHandleScope.defaultSyncContactsResponse() = respond(
    content = successResponse<Unit>(
        body = Unit
    ),
    status = HttpStatusCode.OK,
    headers = jsonHeaders
)

fun createRepository(
    contactsProvider: ContactsProvider,
    contactsDataStore: DataStore<Preferences>,
    contactsResponse: (suspend MockRequestHandleScope.() -> HttpResponseData)? = null,
    syncContactsResponse: (suspend MockRequestHandleScope.() -> HttpResponseData)? = null
): ContactsRepositoryImpl {
    return ContactsRepositoryImpl(
        client = createHttpClient(
            contactsResponse = contactsResponse,
            syncContactsResponse = syncContactsResponse
        ),
        contactsProvider = contactsProvider,
        dataStore = contactsDataStore
    )
}

fun createHttpClient(
    contactsResponse: (suspend MockRequestHandleScope.() -> HttpResponseData)? = null,
    syncContactsResponse: (suspend MockRequestHandleScope.() -> HttpResponseData)? = null
) = HttpClient(MockEngine { request ->
    when (request.url.encodedPath) {
        CONTACTS_ENDPOINT -> if (contactsResponse == null)
            defaultContactsResponse()
        else
            contactsResponse()

        SYNC_CONTACTS_ENDPOINT -> if (syncContactsResponse == null)
            defaultSyncContactsResponse()
        else
            syncContactsResponse()

        else -> respond(
            content = "",
            status = HttpStatusCode.BadRequest,
            headers = jsonHeaders
        )
    }
}) {
    install(ContentNegotiation) {
        json(jsonSerialization)
    }
    install(DefaultRequest) {
        contentType(ContentType.Application.Json)
    }
}