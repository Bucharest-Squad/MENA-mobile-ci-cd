package net.thechance.mena.wallet.presentation.screen.transaction_history

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
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import net.thechance.mena.wallet.domain.entity.Transaction
import net.thechance.mena.wallet.domain.model.TransactionStatus
import net.thechance.mena.wallet.domain.model.TransactionType
import net.thechance.mena.wallet.domain.repository.TransactionRepository
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalUuidApi::class)
class TransactionHistoryViewModelTest {

    private val transactionRepository = mock<TransactionRepository>(mode = MockMode.autofill)
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: TransactionHistoryViewModel

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `state should not have error when repository returns value`() = runTest(testDispatcher) {
        everySuspend { transactionRepository.getTransactionHistory(0, 20, any()) } returns history

        viewModel = TransactionHistoryViewModel(transactionRepository)
        advanceUntilIdle()

        viewModel.state.test {
            val currentState = awaitItem()
            assertEquals(null, currentState.errorState)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should send NavigateBack effect when onBackClicked is called`() = runTest(testDispatcher) {
        everySuspend { transactionRepository.getTransactionHistory(0, 20, any()) } returns history

        viewModel = TransactionHistoryViewModel(transactionRepository)
        advanceUntilIdle()

        viewModel.uiEffect.test {
            viewModel.onBackClicked()

            val effect = awaitItem()
            assertTrue(effect is TransactionHistoryEffect.NavigateBack)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should send NavigateToTransactionDetails effect when onTransactionCardClicked is called`() =
        runTest(testDispatcher) {
            everySuspend { transactionRepository.getTransactionHistory(0, 20, any()) } returns history

            viewModel = TransactionHistoryViewModel(transactionRepository)
            advanceUntilIdle()

            val testTransactionId = Uuid.random()

            viewModel.uiEffect.test {
                viewModel.onTransactionCardClicked(testTransactionId)

                val effect = awaitItem()
                assertTrue(effect is TransactionHistoryEffect.NavigateToTransactionDetails)
                assertEquals(testTransactionId, (effect as TransactionHistoryEffect.NavigateToTransactionDetails).id)
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `should send NavigateToExportTransaction effect when onExportClicked is called`() =
        runTest(testDispatcher) {
            everySuspend { transactionRepository.getTransactionHistory(0, 20, any()) } returns history

            viewModel = TransactionHistoryViewModel(transactionRepository)
            advanceUntilIdle()

            viewModel.uiEffect.test {
                viewModel.onExportClicked()

                val effect = awaitItem()
                assertTrue(effect is TransactionHistoryEffect.NavigateToExportTransaction)
                cancelAndIgnoreRemainingEvents()
            }
        }

    private companion object {
        val history = listOf(
            Transaction(
                id = Uuid.random(),
                createdAt = LocalDateTime(
                    date = LocalDate(2025, 8, 20),
                    time = LocalTime(12, 0)
                ),
                amount = 120.0,
                status = TransactionStatus.SUCCESS,
                senderName = "Alice",
                receiverName = "Bob",
                type = TransactionType.SENT
            ),
            Transaction(
                id = Uuid.random(),
                createdAt = LocalDateTime(
                    date = LocalDate(2025, 8, 20),
                    time = LocalTime(12, 0)
                ),
                amount = 75.5,
                status = TransactionStatus.FAILED,
                senderName = "Charlie",
                receiverName = "You",
                type = TransactionType.RECEIVED
            ),
            Transaction(
                id = Uuid.random(),
                createdAt = LocalDateTime(
                    date = LocalDate(2025, 8, 20),
                    time = LocalTime(12, 0)
                ),
                amount = 200.0,
                status = TransactionStatus.SUCCESS,
                senderName = "Online Shop",
                receiverName = "You",
                type = TransactionType.ONLINE_PURCHASE
            ),
        )
        const val PAGE_SIZE = 20
        const val PAGE = 1
    }
}