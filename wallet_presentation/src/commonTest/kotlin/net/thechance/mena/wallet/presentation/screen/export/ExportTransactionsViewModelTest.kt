package net.thechance.mena.wallet.presentation.screen.export

import app.cash.turbine.test
import dev.mokkery.MockMode
import dev.mokkery.answering.returns
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
import net.thechance.mena.wallet.domain.repository.ExportTransactionsRepository
import net.thechance.mena.wallet.presentation.base.CustomToastState
import net.thechance.mena.wallet.presentation.base.SnackBarState
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

    @Test
    fun `should update startDate when onFromDateClicked is called`() = runTest {
        val viewModel = ExportTransactionsViewModel(repository, testDispatcher)

        viewModel.state.test {
            skipItems(1)
            viewModel.onFromDateClicked()
            val state = awaitItem()
            assertEquals("2025/09/01", state.startDate)
        }
    }

    @Test
    fun `should update endDate when onToDateClicked is called`() = runTest {
        val viewModel = ExportTransactionsViewModel(repository, testDispatcher)

        viewModel.state.test {
            skipItems(1)
            viewModel.onToDateClicked()
            val state = awaitItem()
            assertEquals("2025/09/27", state.endDate)
        }
    }


    @Test
    fun `onDownloadClicked with empty pdf should show toast`() = runTest {
        everySuspend { repository.getFilteredTransactionsFile(any()) } returns byteArrayOf()

        val viewModel = ExportTransactionsViewModel(repository, testDispatcher)

        viewModel.state.test {
            viewModel.onDownloadClicked()
            skipItems(2)

            val toastState = awaitItem().toast
            assertToastState(isVisible = true, toastState = toastState)

            cancelAndIgnoreRemainingEvents()
        }
    }

    private fun assertSnackBarState(
        isVisible: Boolean,
        isSuccess: Boolean,
        snackBarState: SnackBarState
    ) {
        assertEquals(isVisible, snackBarState.isVisible)
        assertEquals(isSuccess, snackBarState.isSuccess)
    }

    private fun assertToastState(
        isVisible: Boolean,
        toastState: CustomToastState
    ) {
        assertEquals(isVisible, toastState.isVisible)
    }


}