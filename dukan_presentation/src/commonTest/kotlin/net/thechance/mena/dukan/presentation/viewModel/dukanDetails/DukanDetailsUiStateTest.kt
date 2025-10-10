package net.thechance.mena.dukan.presentation.viewModel.dukanDetails

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import net.thechance.mena.dukan.presentation.util.pagination.PagingData
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class DukanDetailsUiStateTest {

    @Test
    fun `default DukanDetailsUiState SHOULD have expected initial values`() = runTest {
        val state = DukanDetailsUiState()

        assertTrue(state.isDukanInfoLoading)
        assertNull(state.errorMessage)
        assertEquals(PagingData(), state.bestSellingProducts)
        assertEquals(PagingData(), state.shelves)
        assertEquals(DukanDetailsUiState.ShelvesState.LOADING, state.shelvesState)
        assertEquals(DukanDetailsUiState.ProductsState.LOADING, state.productsState)
        assertEquals("", state.shelfIdSelected)
        assertEquals("", state.dukanInfo.name)
        assertEquals("", state.dukanInfo.imageUrl)
        assertEquals(DukanDetailsUiState.Style.NO_IMAGE, state.dukanInfo.style)
        assertEquals(0L, state.dukanInfo.color)
        assertEquals(DukanDetailsUiState.Coordinates(), state.dukanInfo.coordinates)
    }

    @Test
    fun `Coordinates SHOULD store latitude and longitude correctly`() = runTest {
        val coords = DukanDetailsUiState.Coordinates(latitude = 30.0, longitude = 31.0)

        assertEquals(30.0, coords.latitude)
        assertEquals(31.0, coords.longitude)
    }

    @Test
    fun `ShelfUiState SHOULD store id and name correctly`() = runTest {
        val shelf = DukanDetailsUiState.ShelfUiState(
            id = "s1",
            name = "Fruits",
        )

        assertEquals("s1", shelf.id)
        assertEquals("Fruits", shelf.name)
    }

    @Test
    fun `ProductUiState SHOULD store its properties correctly`() = runTest {
        val product = DukanDetailsUiState.ProductUiState(
            id = "p1",
            name = "Banana",
            imageUrl = "banana.png",
            price = 5.0,
            description = "Fresh bananas"
        )

        assertEquals("p1", product.id)
        assertEquals("Banana", product.name)
        assertEquals("banana.png", product.imageUrl)
        assertEquals(5.0, product.price)
        assertEquals("Fresh bananas", product.description)
    }

    @Test
    fun `copy SHOULD create new instance with updated values`() = runTest {
        val initial = DukanDetailsUiState()
        val updated = initial.copy(
            dukanInfo = initial.dukanInfo.copy(name = "New Dukan Name")
        )

        assertEquals("New Dukan Name", updated.dukanInfo.name)
        assertEquals("", initial.dukanInfo.name)
        assertNotEquals(initial, updated)
        assertEquals(initial.isDukanInfoLoading, updated.isDukanInfoLoading)
    }
}