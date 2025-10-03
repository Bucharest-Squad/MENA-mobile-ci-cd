package net.thechance.mena.dukan.presentation.viewModel.createProduct

import app.cash.turbine.test
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import net.thechance.mena.dukan.domain.entity.Shelf
import net.thechance.mena.dukan.domain.repository.ProductRepository
import net.thechance.mena.dukan.domain.repository.ShelfRepository
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class CreateProductViewModelTest {

    private val productRepository = mock<ProductRepository>()
    private val shelfRepository = mock<ShelfRepository>()
    private lateinit var viewModel: CreateProductViewModel
    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    @BeforeTest
    fun setUp() {
        viewModel = CreateProductViewModel(productRepository, shelfRepository,dispatcher)
    }

    @Test
    fun `getShelves Should update emit success state`() = scope.runTest {
        val shelves = listOf(Shelf("1", "Shelf1"))
        everySuspend { shelfRepository.getMyDukanShelves() } returns shelves

        viewModel = CreateProductViewModel(productRepository, shelfRepository)
        advanceUntilIdle()

        viewModel.state.test {
            assertEquals("Shelf1", awaitItem().shelves.first().name)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onProductNameChange should update product name`() = scope.runTest {
        viewModel.state.test {
            skipItems(1)
            viewModel.onProductNameChange("Phone")
            assertEquals("Phone", awaitItem().productName)
        }
    }

    @Test
    fun `onPriceChange should update price`() = scope.runTest {
        viewModel.state.test {
            skipItems(1)
            viewModel.onPriceChange("12.50")
            assertEquals("12.50", awaitItem().price)
        }
    }

    @Test
    fun `onDescriptionChange should update description`() = scope.runTest {
        viewModel.state.test {
            skipItems(1)
            viewModel.onDescriptionChange("desc")
            assertEquals("desc", awaitItem().description)
        }
    }

    @Test
    fun `onShelfSelect should update shelf selected`() = scope.runTest {
        val shelf = ShelfUiState("Shelf1")
        viewModel.updateState { copy(shelves = listOf(shelf)) }

        viewModel.state.test {
            skipItems(1)
            viewModel.onShelfSelect(shelf)
            val selected = awaitItem().shelves.first()
            assertTrue(selected.isSelected)
        }
    }

    @Test
    fun `onBackButton emits NavigateBack`() = scope.runTest {
        viewModel.effect.test {
            viewModel.onBackButton()
            assertEquals(CreateProductEffect.NavigateBack, awaitItem())
        }
    }


    @Test
    fun `onDismissSnackBar hides snackbar`() = scope.runTest {
        viewModel.updateState { copy(showSnackBar = true) }

        viewModel.onDismissSnackBar()

        val state = viewModel.state.value
        assertFalse(state.showSnackBar)
        assertNull(state.snackBarUiState)
    }
}
