package net.thechance.mena.dukan.presentation.viewModel.dukanDetails

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import dev.mokkery.MockMode
import dev.mokkery.answering.returns
import dev.mokkery.answering.throws
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import net.thechance.mena.dukan.domain.entity.Color
import net.thechance.mena.dukan.domain.entity.Dukan
import net.thechance.mena.dukan.domain.entity.Product
import net.thechance.mena.dukan.domain.entity.Shelf
import net.thechance.mena.dukan.domain.repository.DukanRepository
import net.thechance.mena.dukan.domain.repository.ProductRepository
import net.thechance.mena.dukan.domain.repository.ShelfRepository
import net.thechance.mena.dukan.domain.util.PagedResult
import net.thechance.mena.dukan.presentation.screen.dukanDetails.DukanDetailsArgs
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class DukanDetailsViewModelTest {

    private val dukanRepository = mock<DukanRepository>(mode = MockMode.autofill)
    private val shelfRepository = mock<ShelfRepository>(mode = MockMode.autofill)
    private val productRepository = mock<ProductRepository>(mode = MockMode.autofill)
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var savedStateHandle: SavedStateHandle
    private lateinit var dukanDetailsViewModel: DukanDetailsViewModel

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        savedStateHandle = SavedStateHandle(mapOf(DukanDetailsArgs.DUKAN_ID to "dukan_123"))

        everySuspend { dukanRepository.getDukanDetailsByDukanId(any()) } returns dummyDukanDetails()
        everySuspend {
            shelfRepository.getShelvesByDukanId(
                any(),
                any(),
                any()
            )
        } returns PagedResult(
            items = dummyShelves(),
            totalItems = 3,
            currentPage = 1,
            totalPages = 1,
            hasNext = false,
            hasPrevious = false
        )
        everySuspend {
            productRepository.getProductsByShelfId(
                any(),
                any(),
                any()
            )
        } returns PagedResult(
            items = emptyList(),
            totalItems = 0,
            currentPage = 1,
            totalPages = 1,
            hasNext = false,
            hasPrevious = false
        )


        dukanDetailsViewModel = DukanDetailsViewModel(
            dukanRepository = dukanRepository,
            shelfRepository = shelfRepository,
            productRepository = productRepository,
            defaultDispatcher = testDispatcher,
            savedStateHandle = savedStateHandle
        )
    }

    @AfterTest
    fun cleanup() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init SHOULD load dukan details and update state correctly`() = runTest {
        advanceUntilIdle()

        val state = dukanDetailsViewModel.state.value
        assertFalse(state.isDukanInfoLoading)
    }


    @Test
    fun `init SHOULD handle dukan details loading error`() = runTest {
        val errorMessage = "Network Error"
        everySuspend { dukanRepository.getDukanDetailsByDukanId(any()) } throws Exception(
            errorMessage
        )

        dukanDetailsViewModel = DukanDetailsViewModel(
            dukanRepository = dukanRepository,
            shelfRepository = shelfRepository,
            productRepository = productRepository,
            defaultDispatcher = testDispatcher,
            savedStateHandle = savedStateHandle
        )
        advanceUntilIdle()

        val state = dukanDetailsViewModel.state.value
        assertFalse(state.isDukanInfoLoading)
    }

    @Test
    fun `onShelfClicked SHOULD update selected shelf id in state`() = runTest {
        advanceUntilIdle()
        val newShelfId = "shelf_2"

        dukanDetailsViewModel.updateState { copy(shelfIdSelected = "shelf_1") }
        dukanDetailsViewModel.state.test {
            val initialState = awaitItem()
            assertEquals("shelf_1", initialState.shelfIdSelected)

            dukanDetailsViewModel.onShelfClicked(newShelfId)

            val updatedState = awaitItem()
            assertEquals(newShelfId, updatedState.shelfIdSelected)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onShelfClicked SHOULD return pager that loads correct products when style is wide image`() =
        runTest {
            val targetShelfId = "shelf_1"
            dukanDetailsViewModel.updateState {
                copy(
                    dukanInfo = dukanInfo.copy(style = DukanDetailsUiState.Style.WIDE_IMAGE)
                )
            }
            everySuspend {
                productRepository.getProductsByShelfId(
                    shelfId = targetShelfId,
                    page = any(),
                    size = any()
                )
            } returns PagedResult(
                items = fakeProducts(),
                totalItems = 1,
                currentPage = 1,
                totalPages = 1,
                hasNext = false,
                hasPrevious = false
            )
            dukanDetailsViewModel.onShelfClicked(targetShelfId)


            val pager = dukanDetailsViewModel.pagerProduct
            pager.load()
            advanceUntilIdle()

            pager.flow.test {
                val productsPagingData = awaitItem()
                assertEquals(1, productsPagingData.items.size)
                assertEquals("product_1", productsPagingData.items.first().id)
                assertEquals("Laptop", productsPagingData.items.first().name)
                cancelAndIgnoreRemainingEvents()
            }
        }


    @Test
    fun `getProductsPager SHOULD return the same cached pager instance for the same shelfId`() =
        runTest {
            val pagerInstance1 = dukanDetailsViewModel.onShelfClicked("shelf_1")
            val pagerInstance2 = dukanDetailsViewModel.onShelfClicked("shelf_1")

            assertTrue(pagerInstance1 === pagerInstance2)
        }

    @Test
    fun `when onBackClicked SHOULD emit NavigateBack effect`() = runTest {
        dukanDetailsViewModel.effect.test {
            dukanDetailsViewModel.onBackClicked()
            assertEquals(DukanDetailsEffects.NavigateBack, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `when onViewAllShelfProductsClicked SHOULD emit NavigateToViewAllShelfProducts effect`() =
        runTest {
            val shelfId = "id123"
            val shelfName = "shelf"
            dukanDetailsViewModel.effect.test {
                dukanDetailsViewModel.onViewAllShelfProductsClicked(shelfId, shelfName)
                assertEquals(
                    DukanDetailsEffects.NavigateToViewAllShelfProducts(shelfId, shelfName),
                    awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `when onViewDukanOnMapClicked SHOULD emit NavigateToViewDukanOnMap effect`() = runTest {
        val lat = 28.0
        val lon = 29.0
        dukanDetailsViewModel.effect.test {
            dukanDetailsViewModel.onViewDukanOnMapClicked(lat, lon)
            assertEquals(
                DukanDetailsEffects.NavigateToViewDukanOnMap(lat, lon),
                awaitItem()
            )
            cancelAndIgnoreRemainingEvents()
        }
    }
}

private fun dummyDukanDetails() = Dukan(
    id = "dukan_123",
    name = "Test Dukan",
    address = "123 Test Street",
    imageUrl = "https://example.com/image.png",
    coordinates = Dukan.Coordinates(latitude = 30.0, longitude = 31.0),
    color = Color(id = "color_1", hexCode = "#FF0000"),
    style = Dukan.Style.WIDE_IMAGE,
    categories = emptySet(),
    status = Dukan.Status.APPROVED,
)

private fun dummyShelves() = listOf(
    Shelf(id = "shelf_1", name = "Electronics"),
    Shelf(id = "shelf_2", name = "Clothing"),
    Shelf(id = "shelf_3", name = "Books")
)

private fun fakeProducts(): List<Product> = listOf(
    Product(
        id = "product_1",
        name = "Laptop",
        description = "A cool laptop",
        price = 1200.0,
        imageUrls = emptyList(),
        createdAt = "2025-10-10T12:00:00Z"
    )
)

private val fakeProductsState = listOf(
    DukanDetailsUiState.ProductUiState(
        id = "product_1",
        name = "Laptop",
        imageUrl = "",
        price = 1200.0,
        description = "A cool laptop"
    )
)

private val fakeShelvesStateWithProducts = listOf(
    DukanDetailsUiState.ShelfUiState(
        id = "shelf_1",
        name = "Electronics",
        products = fakeProductsState
    ),
    DukanDetailsUiState.ShelfUiState(
        id = "shelf_2",
        name = "Clothing",
        products = fakeProductsState
    ),
    DukanDetailsUiState.ShelfUiState(
        id = "shelf_3",
        name = "Books",
        products = fakeProductsState
    )
)

