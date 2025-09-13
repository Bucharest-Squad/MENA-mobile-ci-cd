package net.thechance.mena.core_chat.data.contacts.source.remote

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import net.thechance.mena.core_chat.data.contacts.source.remote.dto.ContactCreationRequestDto
import net.thechance.mena.core_chat.data.contacts.source.remote.dto.ContactDto
import net.thechance.mena.core_chat.data.shared.dto.BaseResponseDto
import net.thechance.mena.core_chat.data.shared.dto.PagedDataDto

class ContactsDataSource(
    private val client: HttpClient,
    private val baseUrl: String,
) {

//    suspend fun getPagedContact(pageNumber: Int, pageSize: Int) {
//        val response: BaseResponseDto<PagedDataDto<ContactDto>> =
//            client.get("http://localhost/contacts") {
//                url {
//                    parameters.append("pageNumber", pageNumber)
//                    parameters.append("pageSize", pageSize)
//                }
//            }
//        println(response.status)
//    }

//    suspend fun syncContacts(body: List<ContactRequest>) {
//        val response: HttpResponse =
//            client.post("http://localhost/contacts/sync") {
//                contentType(ContentType.Application.Json)
//                setBody(body)
//            }
//        println(response.status)
//    }

    suspend fun getUserContacts(
        pageNumber: Int,
        pageSize: Int
    ): BaseResponseDto<PagedDataDto<ContactDto>> {
        return client.get("$baseUrl/contacts") {
            parameter("pageNumber", pageNumber)
            parameter("pageSize", pageSize)
        }.body()
    }

    suspend fun syncContacts(
        contacts: List<ContactCreationRequestDto>
    ): BaseResponseDto<Unit> {
        return client.post("$baseUrl/contacts/sync") {
            contentType(ContentType.Application.Json)
            setBody(contacts)
        }.body()
    }
}
