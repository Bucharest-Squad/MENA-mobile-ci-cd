package net.thechance.mena.core_chat.data.contacts

import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import io.ktor.client.HttpClient
import kotlinx.coroutines.test.runTest
import kotlinx.io.IOException
import net.thechance.mena.core_chat.data.contacts.dto.ContactDto
import net.thechance.mena.core_chat.data.contacts.fakes.FakeContactsProvider
import net.thechance.mena.core_chat.data.contacts.fakes.FakeDataStore
import net.thechance.mena.core_chat.data.contacts.fakes.createSamplePagedData
import net.thechance.mena.core_chat.data.contacts.fakes.sampleContact
import net.thechance.mena.core_chat.data.contacts.fakes.sampleDeviceContact
import net.thechance.mena.core_chat.data.shared.dto.PagedDataDto
import net.thechance.mena.core_chat.domain.exception.ContactSyncFailedException
import net.thechance.mena.core_chat.domain.exception.ContactsFetchFailedException
import net.thechance.mena.core_chat.domain.exception.ContactsPermissionDeniedException
import net.thechance.mena.core_chat.domain.exception.DataStoreException
import net.thechance.mena.core_chat.domain.exception.UnAuthorizedException
import net.thechance.mena.core_chat.domain.exception.UnknownException
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFailsWith


class ContactsRepositoryImplTest {
    private val mockContactsProvider = FakeContactsProvider()
    private val mockDataStore = FakeDataStore()
    private lateinit var httpClient: HttpClient
    private lateinit var repository: ContactsRepositoryImpl

    @BeforeTest
    fun setUp() {
        mockContactsProvider.reset()
        mockDataStore.reset()
        httpClient = createHttpClient()

        repository = createRepository(mockContactsProvider, mockDataStore)
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
            contactsResponse = {
                mockSuccessPagedResponse<PagedDataDto<ContactDto>>(
                    body = PagedDataDto(
                        data = emptyList(),
                        pageNumber = 1,
                        pageSize = 10,
                        totalItems = 0,
                        totalPages = 1
                    )
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
                contactsResponse = {
                    mockErrorPagedResponse<PagedDataDto<ContactDto>>(
                        status = 401
                    )
                }
            )

            assertFailsWith<UnAuthorizedException> {
                repository.getUserContacts(pageNumber = 1, pageSize = 10)
            }
        }

    @Test
    fun `should throw UnknownException when getUserContacts receives error response`() = runTest {

        repository = createRepository(
            contactsProvider = mockContactsProvider,
            contactsDataStore = mockDataStore,
            contactsResponse = {
                mockErrorPagedResponse<PagedDataDto<ContactDto>>(
                    status = 500
                )
            }
        )


        assertFailsWith<UnknownException> {
            repository.getUserContacts(pageNumber = 1, pageSize = 10)
        }
    }

    @Test
    fun `should throw ContactsFetchFailedException when getUserContacts encounters network error`() =
        runTest {

            repository = createRepository(
                contactsProvider = mockContactsProvider,
                contactsDataStore = mockDataStore,
                contactsResponse = {
                    throw IOException()
                }
            )


            assertFailsWith<ContactsFetchFailedException> {
                repository.getUserContacts(pageNumber = 1, pageSize = 10)
            }
        }

    @Test
    fun `should call contacts provider when syncContacts is called`() = runTest {

        mockContactsProvider.setContacts(
            listOf(
                sampleDeviceContact
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
                syncContactsResponse = {
                    throw IOException()
                }
            )


            assertFailsWith<ContactSyncFailedException> {
                repository.syncContacts()
            }
        }

    @Test
    fun `should throw UnAuthorizedException when syncContacts receives failed response`() =
        runTest {

            repository = createRepository(
                contactsProvider = mockContactsProvider,
                contactsDataStore = mockDataStore,
                syncContactsResponse = {
                    mockErrorPagedResponse<PagedDataDto<ContactDto>>(
                        status = 401
                    )
                }
            )


            assertFailsWith<UnAuthorizedException> {
                repository.syncContacts()
            }
        }

    @Test
    fun `should throw ContactsPermissionDeniedException when syncContacts encounters permission denied`() =
        runTest {

            mockContactsProvider.throwPermissionDenied = true


            assertFailsWith<ContactsPermissionDeniedException> {
                repository.syncContacts()
            }
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


        assertFailsWith<DataStoreException> {
            repository.getUserSyncedState()
        }
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


        assertFailsWith<DataStoreException> {
            repository.setUserSyncedState(true)
        }
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

    @Test
    fun `should return 10 contacts on first page`() = runTest {
        val page1Contacts = createSamplePagedData(
            pageNumber = 1,
            pageSize = 10,
            totalItems = 15,
            totalPages = 2
        )

        repository = createRepository(
            contactsProvider = mockContactsProvider,
            contactsDataStore = mockDataStore,
            contactsResponse = {
                mockSuccessPagedResponse(
                    body = page1Contacts
                )
            }
        )

        val result = repository.getUserContacts(pageNumber = 1, pageSize = 10)

        assertThat(result.data).isEqualTo(page1Contacts.data?.map { it.toDomain() })
    }

    @Test
    fun `should return 5 contacts on second page`() = runTest {
        val page2Contacts = createSamplePagedData(5)

        repository = createRepository(
            contactsProvider = mockContactsProvider,
            contactsDataStore = mockDataStore,
            contactsResponse = {
                mockSuccessPagedResponse(
                    body = page2Contacts
                )
            }
        )

        val result = repository.getUserContacts(pageNumber = 2, pageSize = 10)

        assertThat(result.data).isEqualTo(page2Contacts.data?.map { it.toDomain() })
    }

    @Test
    fun `should return empty list when requesting page beyond total pages`() = runTest {
        repository = createRepository(
            contactsProvider = mockContactsProvider,
            contactsDataStore = mockDataStore,
            contactsResponse = {
                mockSuccessPagedResponse<PagedDataDto<ContactDto>>(
                    body = PagedDataDto(
                        data = emptyList(),
                        pageNumber = 3,
                        pageSize = 10,
                        totalItems = 15,
                        totalPages = 2
                    )
                )
            }
        )

        val result = repository.getUserContacts(pageNumber = 3, pageSize = 10)

        assertThat(result.data).isEmpty()
    }

}