package net.thechance.mena.admin_panel.data.repository

import de.jensklingenberg.ktorfit.Response
import dev.mokkery.MockMode
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import net.thechance.mena.admin_panel.data.local.InMemoryDukanDataStore
import net.thechance.mena.admin_panel.data.mapper.parseLocalDateTimeOrDefault
import net.thechance.mena.admin_panel.data.remote.api_service.DukanApiService
import net.thechance.mena.admin_panel.data.remote.dto.DukanPagedResponse
import net.thechance.mena.admin_panel.data.remote.dto.dukan.ProductDto
import net.thechance.mena.admin_panel.data.remote.dto.dukan.ShelfDto
import net.thechance.mena.admin_panel.data.repository.dukan.DukanRepositoryImpl
import net.thechance.mena.admin_panel.domain.entity.dukan.Dukan
import net.thechance.mena.admin_panel.domain.exceptions.UnauthorizedException
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class DukanRepositoryImplTest {

    private lateinit var dukanApiService: DukanApiService
    private lateinit var dukanRepository: DukanRepositoryImpl
    private lateinit var inMemoryDukanDataStore: InMemoryDukanDataStore

    @BeforeTest
    fun setup() {
        dukanApiService = mock<DukanApiService>(mode = MockMode.autofill)
        inMemoryDukanDataStore = InMemoryDukanDataStore()
        dukanRepository = DukanRepositoryImpl(dukanApiService, inMemoryDukanDataStore)
    }

    @Test
    fun `getDukanShelves should return mapped shelves paged result on success`() = runTest {
        everySuspend {
            dukanApiService.getDukanShelves(any(), any(), any())
        } returns successfulResponse(SHELF_PAGED)

        val result = dukanRepository.getDukanShelves(FAKE_UUID, page = 0, size = 10)

        assertEquals(1, result.items.size)
        assertEquals("Top Shelf", result.items.first().title)
    }

    @Test
    fun `getShelfProducts should return mapped products paged result on success`() = runTest {
        everySuspend {
            dukanApiService.getShelfProducts(any(), any(), any())
        } returns successfulResponse(PRODUCT_PAGED)

        val result = dukanRepository.getShelfProducts(FAKE_SHELF_ID, page = 0, size = 10)

        assertEquals(1, result.items.size)
        assertEquals("Product 1", result.items.first().name)
    }

    @Test
    fun `getShelfProducts should throw UnauthorizedException on 401 Unauthorized`() = runTest {
        everySuspend {
            dukanApiService.getShelfProducts(any(), any(), any())
        } returns unauthorizedResponse()

        val exception = assertFailsWith<UnauthorizedException> {
            dukanRepository.getShelfProducts(Uuid.random(), page = 0, size = 10)
        }

        assertTrue(exception.message?.contains("Unauthorized") == true)
    }

    @Test
    fun `storeDukanDetails then getDukanDetails should return same dukan`() {
        dukanRepository.storeDukanDetails(FAKE_DUKAN)
        val result = dukanRepository.getDukanDetails()

        assertEquals(FAKE_DUKAN, result)
    }

    @Test
    fun `clearDukanDetails should remove stored dukan`() {
        dukanRepository.storeDukanDetails(FAKE_DUKAN)
        dukanRepository.clearDukanDetails()

        assertFailsWith<IllegalArgumentException> {
            dukanRepository.getDukanDetails()
        }
    }

    companion object {
        @OptIn(ExperimentalUuidApi::class)
        val FAKE_UUID: Uuid = Uuid.random()

        @OptIn(ExperimentalUuidApi::class)
        val FAKE_SHELF_ID: Uuid = Uuid.random()

        val SHELF_DTO = ShelfDto(id = FAKE_UUID.toString(), title = "Top Shelf")

        val SHELF_PAGED = DukanPagedResponse(
            totalPages = 1,
            totalElements = 1,
            size = 10,
            content = listOf(SHELF_DTO),
            number = 0
        )

        val PRODUCT_DTO = ProductDto(
            id = FAKE_UUID.toString(),
            name = "Product 1",
            price = 100.0,
            discountedPrice = 80.0,
            description = "Good",
            imageUrls = listOf("https://img"),
            createdAt = "2025-10-31T00:00:00"
        )

        val PRODUCT_PAGED = DukanPagedResponse(
            totalPages = 1,
            totalElements = 1,
            size = 10,
            content = listOf(PRODUCT_DTO),
            number = 0
        )
        val FAKE_DUKAN = Dukan(
            id = FAKE_UUID,
            name = "Test Dukan",
            imageUrl = "",
            address = "Test Address",
            latitude = 30.0,
            longitude = 31.0,
            categories = emptyList(),
            createdAt = parseLocalDateTimeOrDefault("2025-10-31T00:00:00"),
            activationStatus = Dukan.ActivationStatus.ACTIVATED,
            status = Dukan.Status.APPROVED
        )

        private fun <T> successfulResponse(body: T): Response<T> {
            val mockHttpResponse: HttpResponse = mock(MockMode.autofill) {
                everySuspend { status } returns HttpStatusCode.OK
            }
            return Response.success(body = body, rawResponse = mockHttpResponse) as Response<T>
        }

        private fun <T> unauthorizedResponse(): Response<T> {
            val mockHttpResponse: HttpResponse = mock(MockMode.autofill) {
                everySuspend { status } returns HttpStatusCode.Unauthorized
            }
            return Response.error<T>(
                body = """{"message":"Unauthorized"}""",
                rawResponse = mockHttpResponse
            ) as Response<T>
        }
    }
}
