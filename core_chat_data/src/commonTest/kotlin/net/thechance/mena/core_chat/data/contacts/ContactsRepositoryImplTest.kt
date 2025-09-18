package net.thechance.mena.core_chat.data.contacts

import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.respond
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.test.runTest
import kotlinx.io.IOException
import kotlinx.serialization.json.Json
import net.thechance.mena.core_chat.data.contacts.dto.ContactDto
import net.thechance.mena.core_chat.data.contacts.fakes.FakeContactsProvider
import net.thechance.mena.core_chat.data.contacts.fakes.FakeDataStore
import net.thechance.mena.core_chat.data.shared.dto.PagedDataDto
import net.thechance.mena.core_chat.domain.entity.Contact
import net.thechance.mena.core_chat.domain.exception.ContactSyncFailedException
import net.thechance.mena.core_chat.domain.exception.ContactsFetchFailedException
import net.thechance.mena.core_chat.domain.exception.ContactsPermissionDeniedException
import net.thechance.mena.core_chat.domain.exception.DataStoreException
import net.thechance.mena.core_chat.domain.exception.UnAuthorizedException
import net.thechance.mena.core_chat.domain.exception.UnknownException
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFailsWith
import com.bilalazzam.contacts_provider.Contact as DeviceContact


class ContactsRepositoryImplTest {
    private val mockContactsProvider = FakeContactsProvider()
    private val mockDataStore = FakeDataStore()
    private val json = Json { ignoreUnknownKeys = true }
    private val jsonHeaders = headersOf(
        HttpHeaders.ContentType,
        ContentType.Application.Json.toString()
    )

    private lateinit var httpClient: HttpClient
    private lateinit var repository: ContactsRepositoryImpl
    private val sampleContact = Contact(
        firstName = "Bilal",
        lastName = "Azzam",
        phone = "01026388780",
        isMenaUser = true,
        imageUrl = "http://example.com/image.jpg"
    )

    @BeforeTest
    fun setUp() {
        mockContactsProvider.reset()
        mockDataStore.reset()
        httpClient = createHttpClient(jsonHeaders, json)

        repository = createRepository(mockContactsProvider, mockDataStore, json, jsonHeaders)
    }

    @Test
    fun `should return paged contacts when getUserContacts is called successfully`() = runTest {

        val result = repository.getUserContacts(pageNumber = 1, pageSize = 10)


        assertThat(result.data).isEqualTo(
            listOf(
                sampleContact
            )
        )
    }


    @Test
    fun `should return empty paged contacts when getUserContacts returns no data`() = runTest {

        repository = createRepository(
            contactsProvider = mockContactsProvider,
            contactsDataStore = mockDataStore,
            json = json,
            jsonHeaders = jsonHeaders,
            contactsResponse = {
                respond(
                    content = successResponse<PagedDataDto<ContactDto>>(
                        json = json,
                        body = PagedDataDto(
                            data = emptyList(),
                            pageNumber = 1,
                            pageSize = 10,
                            totalItems = 0,
                            totalPages = 1
                        )
                    ),
                    status = HttpStatusCode.OK,
                    headers = jsonHeaders
                )
            }
        )


        val result = repository.getUserContacts(pageNumber = 1, pageSize = 10)


        assertThat(result.data).isEmpty()
    }

    @Test
    fun `should throw UnAuthorizedException when getUserContacts receives unauthorized response`() =
        runTest {

            repository = createRepository(
                contactsProvider = mockContactsProvider,
                contactsDataStore = mockDataStore,
                json = json,
                jsonHeaders = jsonHeaders,
                contactsResponse = {
                    respond(
                        content = errorResponse<PagedDataDto<ContactDto>>(
                            json = json,
                            status = 401,
                            message = "Unauthorized"
                        ),
                        status = HttpStatusCode.OK,
                        headers = jsonHeaders
                    )
                }
            )


            val exception = assertFailsWith<UnAuthorizedException> {

                repository.getUserContacts(pageNumber = 1, pageSize = 10)
            }
            assertThat(exception.message).isEqualTo("Unauthorized")
        }

    @Test
    fun `should throw UnknownException when getUserContacts receives error response`() = runTest {

        repository = createRepository(
            contactsProvider = mockContactsProvider,
            contactsDataStore = mockDataStore,
            json = json,
            jsonHeaders = jsonHeaders,
            contactsResponse = {
                respond(
                    content = errorResponse<PagedDataDto<ContactDto>>(
                        json = json,
                        status = 500,
                        message = "Server error"
                    ),
                    status = HttpStatusCode.OK,
                    headers = jsonHeaders
                )
            }
        )


        val exception = assertFailsWith<UnknownException> {

            repository.getUserContacts(pageNumber = 1, pageSize = 10)
        }
        assertThat(exception.message).isEqualTo("Server error")
    }

    @Test
    fun `should throw ContactsFetchFailedException when getUserContacts encounters network error`() =
        runTest {

            repository = createRepository(
                contactsProvider = mockContactsProvider,
                contactsDataStore = mockDataStore,
                json = json,
                jsonHeaders = jsonHeaders,
                contactsResponse = {
                    throw IOException("Network error")
                }
            )


            val exception = assertFailsWith<ContactsFetchFailedException> {

                repository.getUserContacts(pageNumber = 1, pageSize = 10)
            }
            assertThat(exception.message).isEqualTo("Couldn't get user contacts")
        }

    @Test
    fun `should call contacts provider when syncContacts is called`() = runTest {

        mockContactsProvider.setContacts(
            listOf(
                DeviceContact(
                    id = "1",
                    firstName = "Bilal",
                    lastName = "Azzam",
                    phoneNumbers = listOf("01026388780")
                )
            )
        )


        repository.syncContacts()


        assertThat(mockContactsProvider.getAllContactsCalled).isTrue()
    }

    @Test
    fun `should throw ContactSyncFailedException when syncContacts encounters network error`() =
        runTest {

            repository = createRepository(
                contactsProvider = mockContactsProvider,
                contactsDataStore = mockDataStore,
                json = json,
                jsonHeaders = jsonHeaders,
                syncContactsResponse = {
                    throw IOException("Network error")
                }
            )


            val exception = assertFailsWith<ContactSyncFailedException> {

                repository.syncContacts()
            }
            assertThat(exception.message).isEqualTo("Couldn't sync user contacts")
        }

    @Test
    fun `should throw UnAuthorizedException when syncContacts receives failed response`() =
        runTest {

            repository = createRepository(
                contactsProvider = mockContactsProvider,
                contactsDataStore = mockDataStore,
                json = json,
                jsonHeaders = jsonHeaders,
                syncContactsResponse = {
                    respond(
                        content = errorResponse<PagedDataDto<ContactDto>>(
                            json = json,
                            status = 401,
                            message = "User is not authorized"
                        ),
                        status = HttpStatusCode.OK,
                        headers = jsonHeaders
                    )
                }
            )


            val exception = assertFailsWith<UnAuthorizedException> {

                repository.syncContacts()
            }
            assertThat(exception.message).isEqualTo("User is not authorized")
        }

    @Test
    fun `should throw ContactsPermissionDeniedException when syncContacts encounters permission denied`() =
        runTest {

            mockContactsProvider.throwPermissionDenied = true


            val exception = assertFailsWith<ContactsPermissionDeniedException> {

                repository.syncContacts()
            }
            assertThat(exception.message).isEqualTo("Contacts Permission Denied!")
        }

    @Test
    fun `should return false when getUserSyncedState is called and state is not set`() = runTest {

        val result = repository.getUserSyncedState()


        assertThat(result).isFalse()
    }

    @Test
    fun `should return true when getUserSyncedState is called and state is true`() = runTest {

        mockDataStore.setUserSyncedState(true)


        val result = repository.getUserSyncedState()


        assertThat(result).isTrue()
    }

    @Test
    fun `should return false when getUserSyncedState is called and state is false`() = runTest {

        mockDataStore.setUserSyncedState(false)


        val result = repository.getUserSyncedState()


        assertThat(result).isFalse()
    }

    @Test
    fun `should throw DataStoreException when getUserSyncedState encounters error`() = runTest {

        mockDataStore.throwOnRead = true


        val exception = assertFailsWith<DataStoreException> {

            repository.getUserSyncedState()
        }
        assertThat(exception.message).isEqualTo("error with data store")
    }

    @Test
    fun `should set user synced state to true when setUserSyncedState is called with true`() =
        runTest {

            repository.setUserSyncedState(true)


            assertThat(mockDataStore.getUserSyncedState()).isTrue()
        }

    @Test
    fun `should set user synced state to false when setUserSyncedState is called with false`() =
        runTest {

            repository.setUserSyncedState(false)


            assertThat(mockDataStore.getUserSyncedState()).isFalse()
        }

    @Test
    fun `should throw DataStoreException when setUserSyncedState encounters error`() = runTest {

        mockDataStore.throwOnUpdate = true


        val exception = assertFailsWith<DataStoreException> {

            repository.setUserSyncedState(true)
        }
        assertThat(exception.message).isEqualTo("error with data store")
    }

    @Test
    fun `should return true when getUserSyncedState is set to true`() = runTest {

        mockDataStore.setUserSyncedState(true)


        val result = repository.getUserSyncedState()


        assertThat(result).isTrue()
    }

    @Test
    fun `should return false when getUserSyncedState is set to false`() = runTest {

        mockDataStore.setUserSyncedState(false)


        val result = repository.getUserSyncedState()


        assertThat(result).isFalse()
    }

    @Test
    fun `should return false when getUserSyncedState is not set`() = runTest {

        val result = repository.getUserSyncedState()


        assertThat(result).isFalse()
    }

    @Test
    fun `should set synced state to true when setUserSyncedState is called with true`() = runTest {

        repository.setUserSyncedState(true)


        assertThat(mockDataStore.getUserSyncedState()).isTrue()
    }

    @Test
    fun `should set synced state to false when setUserSyncedState is called with false`() =
        runTest {

            repository.setUserSyncedState(false)


            assertThat(mockDataStore.getUserSyncedState()).isFalse()
        }

}