@file:OptIn(ExperimentalCoroutinesApi::class)

package net.thechance.mena.core_chat.presentation.screen.contacts

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isNotNull
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import net.thechance.mena.core_chat.domain.entity.Contact
import net.thechance.mena.core_chat.domain.model.PagedData
import net.thechance.mena.core_chat.domain.repository.ContactsRepository
import net.thechance.mena.core_chat.presentation.navigation.ChatEffector
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test

class ContactsViewModelTest {
    private val contactsRepository = mock<ContactsRepository>()
    private val effector = mock<ChatEffector>()

    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init should load contacts successfully when viewModel is created`() = runTest {
        everySuspend { contactsRepository.getUserContacts(any(), any()) } returns PagedData(
            data = listOf(expectedContact),
            totalItems = 1,
            isLastPage = true
        )

        val viewModel = ContactsViewModel(contactsRepository, effector)
        advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            val contacts = state.contacts.first()
            assertThat(contacts).isNotNull()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onRefreshContacts should load expected contact when repository returns data`() = runTest {
        everySuspend { contactsRepository.getUserContacts(any(), any()) } returns PagedData(
            data = listOf(expectedContact),
            totalItems = 1,
            isLastPage = true
        )
        val viewModel = ContactsViewModel(contactsRepository, effector)
        advanceUntilIdle()

        viewModel.onRefreshContacts()
        advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            assertThat(state.contacts).isNotNull()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onRefreshContacts should handle empty list when repository returns no contacts`() =
        runTest {
            everySuspend { contactsRepository.getUserContacts(any(), any()) } returns PagedData(
                data = emptyList(),
                totalItems = 0,
                isLastPage = true
            )
            val viewModel = ContactsViewModel(contactsRepository, effector)
            advanceUntilIdle()

            viewModel.onRefreshContacts()
            advanceUntilIdle()

            viewModel.state.test {
                val item = awaitItem()
                assertThat(item).isNotNull()
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `onBackClick should call popBackStack when invoked`() = runTest {
        everySuspend { effector.popBackStack() } returns Unit
        val viewModel = ContactsViewModel(contactsRepository, effector)
        advanceUntilIdle()

        viewModel.onBackClick()
        advanceUntilIdle()

        verifySuspend {
            effector.popBackStack()
        }
    }

    @Test
    fun `onResyncClick should navigate to sync contacts when invoked`() = runTest {
        everySuspend { effector.navigate(any(), any(), any()) } returns Unit
        val viewModel = ContactsViewModel(contactsRepository, effector)
        advanceUntilIdle()

        viewModel.onReSyncClick()
        advanceUntilIdle()

        verifySuspend { effector.navigate(any(), any(), any()) }
    }

    @Test
    fun `onContactClick should navigate to chat screen when called`() = runTest {
        everySuspend { effector.navigate(any(), any(), any()) } returns Unit
        val viewModel = ContactsViewModel(contactsRepository, effector)
        advanceUntilIdle()


        viewModel.onContactClick(1)
        advanceUntilIdle()

        verifySuspend { effector.navigate(any(), any(), any()) }
    }

    companion object {
        val expectedContact = Contact(
            firstName = "John",
            lastName = "Doe",
            phone = "123456789",
            isMenaUser = true,
            imageUrl = null
        )
    }
}