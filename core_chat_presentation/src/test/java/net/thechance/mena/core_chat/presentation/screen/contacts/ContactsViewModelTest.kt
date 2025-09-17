package net.thechance.mena.core_chat.presentation.screen.contacts

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.thechance.mena.core_chat.domain.repository.ContactsRepository
import org.junit.Assert.assertNotNull
import org.junit.Test
import kotlin.test.assertEquals

class ContactsViewModelTest {
    private val contactsRepository = mockk<ContactsRepository>()

    private val viewModel by lazy {
        ContactsViewModel(
            contactsRepository = contactsRepository
        )
    }

    @Test
    fun `onRefreshContacts loads contacts`() = runTest {
        coEvery { contactsRepository.getUserContacts(any(), any()) } returns mockk()
        viewModel.onRefreshContacts()
        viewModel.state.test {
            val item = awaitItem()
        }
    }


    @Test
    fun `onRefreshContacts handles empty contact list`()=runTest {
        coEvery { contactsRepository.getUserContacts(any(), any()) } returns mockk()
        viewModel.onRefreshContacts()
        viewModel.state.test {
            val item = awaitItem()
            assertNotNull(item)
        }

    }

    @Test
    fun `onRefreshContacts handles data load error`() =runTest{
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
    fun `onResyncClick emits NavigateToSyncContacts effect`() =runTest{
       viewModel.effect.test {
           viewModel.onResyncClick()
           val item = awaitItem()
          assertEquals(ContactsScreenEffect.NavigateToSyncContacts, item)
       }
    }

    @Test
    fun `onContactClick emits NavigateToChatScreen effect with valid contactId`()=runTest {
        viewModel.effect.test {
            viewModel.onContactClick(1)
            val item = awaitItem()
            assertEquals(ContactsScreenEffect.NavigateToChatScreen(1), item)
        }
    }

}