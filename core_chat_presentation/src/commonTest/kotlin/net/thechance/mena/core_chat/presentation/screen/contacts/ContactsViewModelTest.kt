package net.thechance.mena.core_chat.presentation.screen.contacts

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isNotNull
import assertk.assertions.isNull
import dev.mokkery.answering.returns
import dev.mokkery.answering.throws
import dev.mokkery.everySuspend
import dev.mokkery.matcher.any
import dev.mokkery.mock
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import net.thechance.mena.core_chat.domain.entity.Contact
import net.thechance.mena.core_chat.domain.model.PagedData
import net.thechance.mena.core_chat.domain.repository.ContactsRepository
import kotlin.test.Test
import kotlin.test.assertEquals
class ContactsViewModelTest {
    private val contactsRepository = mock<ContactsRepository>()

    private val viewModel by lazy {
        ContactsViewModel(
            contactsRepository = contactsRepository
        )
    }

    @Test
    fun `init should load contacts successfully when viewModel is created`() = runTest {

        everySuspend { contactsRepository.getUserContacts(any(), any()) } returns PagedData(
            data = listOf(expectedContact),
            totalItems = 1,
            isLastPage = true
        )

        val viewModel = ContactsViewModel(contactsRepository)

        viewModel.state.test {
            val state = awaitItem()
            val contacts = state.contacts.first()
            assertThat(contacts).isNotNull()
        }
    }

    @Test
    fun `onRefreshContacts should load expected contact when repository returns data`() = runTest {

        everySuspend { contactsRepository.getUserContacts(any(), any()) } returns PagedData(
            data = listOf(expectedContact),
            totalItems = 1,
            isLastPage = true
        )

        viewModel.onRefreshContacts()
        viewModel.state.test {
            val state = awaitItem()
            assertThat(state.contacts).isNotNull()
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `onRefreshContacts should handle empty list when repository returns no contacts`() = runTest {
        everySuspend { contactsRepository.getUserContacts(any(), any()) } returns PagedData(
            data = emptyList(),
            totalItems = 0,
            isLastPage = true
        )
        viewModel.onRefreshContacts()
        viewModel.state.test {
            val item = awaitItem()
            assertThat(item).isNotNull()
        }
    }

    @Test
    fun `onBackClick should emit NavigateBack when invoked`() = runTest {
        viewModel.effect.test {
            viewModel.onBackClick()
            val effect = awaitItem()
            assertEquals(ContactsScreenEffect.NavigateBack, effect)
        }
    }

    @Test
    fun `onResyncClick should emit NavigateToSyncContacts when invoked`() = runTest {
        viewModel.effect.test {
            viewModel.onResyncClick()
            val item = awaitItem()
            assertEquals(ContactsScreenEffect.NavigateToSyncContacts, item)
        }
    }

    @Test
    fun `onContactClick should emit NavigateToChatScreen when valid contactId is passed`() = runTest {
        viewModel.effect.test {
            viewModel.onContactClick(1)
            val item = awaitItem()
            assertEquals(ContactsScreenEffect.NavigateToChatScreen(1), item)
        }
    }

    @Test
    fun `onSnackBarDismiss should set snackBarData to null when invoked after error`() = runTest {
        everySuspend { contactsRepository.getUserContacts(1, 3) } throws Exception("Some error")
        viewModel.onRefreshContacts()

        viewModel.onSnackBarDismiss()

        viewModel.state.test {
            val state = awaitItem()
            assertThat(state.snackBarData).isNull()
            cancelAndIgnoreRemainingEvents()
        }
    }
    companion object{
        val expectedContact = Contact(
            firstName = "John",
            lastName = "Doe",
            phone = "123456789",
            isMenaUser = true,
            imageUrl = null
        )
    }
}
