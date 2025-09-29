package net.thechance.mena.wallet.presentation.screen.export

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
import mena.wallet_presentation.generated.resources.Res
import mena.wallet_presentation.generated.resources.error_no_transactions
import net.thechance.mena.wallet.domain.exceptions.NoInternetException
import net.thechance.mena.wallet.domain.repository.ExportTransactionsRepository
import net.thechance.mena.wallet.presentation.model.FilterStatus
import net.thechance.mena.wallet.presentation.model.FilterType
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)

class ExportTransactionsViewModelTest {
    private val repository = mock<ExportTransactionsRepository>(mode = MockMode.autofill)
    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun terDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should send NavigateBack effect when onBackClicked is called`() = runTest {
        val viewModel = ExportTransactionsViewModel(repository, testDispatcher)
        viewModel.onBackClicked()

        viewModel.uiEffect.test {
            val effect = awaitItem()
            assertTrue(effect is ExportTransactionsEffect.NavigateBack)
        }
    }

    @Test
    fun `should update state when onAllTransactionsClicked is called`() = runTest {
        val viewModel = ExportTransactionsViewModel(repository, testDispatcher)
        viewModel.onCustomFilteringClicked()

        viewModel.state.test {
            skipItems(1)
            viewModel.onAllTransactionsClicked()

            val state = awaitItem()
            assertFalse(state.isCustomFilterCardSelected)
        }
    }

    @Test
    fun `should update state when onCustomFilteringClicked is called`() = runTest {
        val viewModel = ExportTransactionsViewModel(repository, testDispatcher)

        viewModel.state.test {
            skipItems(1)
            viewModel.onCustomFilteringClicked()

            val state = awaitItem()
            assertTrue(state.isCustomFilterCardSelected)
        }
    }

    @Test
    fun `should toggle type in state when onTypeSelected is called`() = runTest {
        val viewModel = ExportTransactionsViewModel(repository, testDispatcher)
        val type = FilterType.SENT

        viewModel.state.test {
            skipItems(1)
            viewModel.onTypeSelected(type)

            val stateWithType = awaitItem()
            assertTrue(stateWithType.selectedTransactionsTypes!!.contains(type))

            viewModel.onTypeSelected(type)
            val stateWithoutType = awaitItem()
            assertFalse(stateWithoutType.selectedTransactionsTypes!!.contains(type))
        }
    }

    @Test
    fun `should update status in state when onStatusSelected is called`() = runTest {
        val viewModel = ExportTransactionsViewModel(repository, testDispatcher)
        val status = FilterStatus.SUCCESS

        viewModel.state.test {
            skipItems(1)
            viewModel.onStatusSelected(status)

            val state = awaitItem()
            assertEquals(status, state.selectedTransactionsStatus)
        }
    }

    @Test
    fun `onViewAndShareClicked with non-empty pdf should navigate`() = runTest {
        everySuspend { repository.getFilteredTransactionsFile(any()) } returns byteArrayOf(1, 2, 3)

        val viewModel = ExportTransactionsViewModel(repository, testDispatcher)

        viewModel.uiEffect.test {
            viewModel.onViewAndShareClicked()
            advanceUntilIdle()

            val effect = awaitItem()
            assertTrue(effect is ExportTransactionsEffect.NavigateToViewFileScreen)
        }
    }

//    @Test
//    fun `onViewAndShareClicked with non-empty pdf should navigate`() = runTest {
//        everySuspend  { repository.getFilteredTransactionsFile(any()) } returns byteArrayOf(1, 2, 3)
//
//        val viewModel = ExportTransactionsViewModel(repository, testDispatcher)
//        viewModel.onViewAndShareClicked()
//        advanceUntilIdle()
//
//        viewModel.uiEffect.test {
//            val effect = awaitItem()
//            assertTrue(effect is ExportTransactionsEffect.NavigateToViewFileScreen)
//        }
//    }

}