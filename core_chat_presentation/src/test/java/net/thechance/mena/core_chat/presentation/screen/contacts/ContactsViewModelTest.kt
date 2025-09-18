package net.thechance.mena.core_chat.presentation.screen.contacts

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import net.thechance.mena.core_chat.domain.entity.Contact
import net.thechance.mena.core_chat.domain.model.PagedData
import net.thechance.mena.core_chat.domain.repository.ContactsRepository
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ContactsViewModelTest {
    private val contactsRepository = mockk<ContactsRepository>()

    private val viewModel by lazy {
        ContactsViewModel(
            contactsRepository = contactsRepository
        )
    }

    @Test
    fun `onRefreshContacts loads expected contact`() = runTest {
        val expectedContact = Contact(
            firstName = "Ali",
            lastName = "Ahmed",
            phone = "010",
            isMenaUser = true,
            imageUrl = null
        )

        coEvery { contactsRepository.getUserContacts(any(), any()) } returns PagedData(
            data = listOf(expectedContact),
            totalItems = 1,
            isLastPage = true
        )

        viewModel.onRefreshContacts()
        viewModel.state.test {
            val state = awaitItem()
            assertEquals<Any>(expectedContact, state.contacts.first())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onRefreshContacts handles empty contact list`() = runTest {
        coEvery { contactsRepository.getUserContacts(any(), any()) } returns mockk()
        viewModel.onRefreshContacts()
        viewModel.state.test {
            val item = awaitItem()
            assertNotNull(item)
        }

    }

    @Test
    fun `onRefreshContacts handles data load error`() = runTest {
        coEvery { contactsRepository.getUserContacts(any(), any()) } throws Exception()
        viewModel.onRefreshContacts()
        viewModel.state.test {
            val item = awaitItem()
        }

    }

    @Test
    fun `onBackClick emits NavigateBack effect`() = runTest {
        viewModel.effect.test {
            viewModel.onBackClick()
            val effect = awaitItem()
            assertEquals(ContactsScreenEffect.NavigateBack, effect)
        }
    }

    @Test
    fun `onResyncClick emits NavigateToSyncContacts effect`() = runTest {
        viewModel.effect.test {
            viewModel.onResyncClick()
            val item = awaitItem()
            assertEquals(ContactsScreenEffect.NavigateToSyncContacts, item)
        }
    }

    @Test
    fun `onContactClick emits NavigateToChatScreen effect with valid contactId`() = runTest {
        viewModel.effect.test {
            viewModel.onContactClick(1)
            val item = awaitItem()
            assertEquals(ContactsScreenEffect.NavigateToChatScreen(1), item)
        }
    }

}