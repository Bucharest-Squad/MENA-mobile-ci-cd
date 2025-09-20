package net.thechance.mena.core_chat.presentation.screen.syncContacts

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.toRoute
import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.mokkery.MockMode
import dev.mokkery.answering.returns
import dev.mokkery.answering.throws
import dev.mokkery.every
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import net.thechance.mena.core_chat.domain.repository.ContactsRepository
import net.thechance.mena.core_chat.presentation.navigation.ChatEffector
import net.thechance.mena.core_chat.presentation.navigation.SyncContactsRoute
import kotlin.test.Test

class SyncContactsViewModelTest {

    private val contactsRepository = mock<ContactsRepository>()
    private val permissionsController = mock<PermissionsController>()
    private val effector = mock<ChatEffector>()

    private fun createSavedStateHandle(forceSync: Boolean): SavedStateHandle {
        val savedStateHandle = mock<SavedStateHandle>(mode = MockMode.autofill)
        every { savedStateHandle.toRoute<SyncContactsRoute>() } returns SyncContactsRoute(forceSync)
        return savedStateHandle
    }

    @Test
    fun `should set isFirstSync to false and call syncContacts when forceSync is true`() = runTest {
        everySuspend { contactsRepository.syncContacts() } returns Unit
        everySuspend { contactsRepository.setUserSyncedState(true) } returns Unit

        val savedStateHandle = createSavedStateHandle(true)
        val viewModel = SyncContactsViewModel(contactsRepository, permissionsController, savedStateHandle, effector)

        val result = viewModel.state.first()

        assertThat(result.isFirstSync).isFalse()
        verifySuspend { contactsRepository.syncContacts() }
    }

    @Test
    fun `should set isFirstSync to true and showSyncView when forceSync is false`() = runTest {
        val savedStateHandle = createSavedStateHandle(false)
        val viewModel = SyncContactsViewModel(contactsRepository, permissionsController, savedStateHandle, effector)

        val result = viewModel.state.first()

        assertThat(result.isFirstSync).isTrue()
        assertThat(result.showSyncView).isTrue()
        assertThat(result.isLoading).isFalse()
    }

    @Test
    fun `should request permission and sync contacts when onSyncClick is called successfully`() = runTest {
        everySuspend { permissionsController.providePermission(Permission.CONTACTS) } returns Unit
        everySuspend { contactsRepository.syncContacts() } returns Unit
        everySuspend { contactsRepository.setUserSyncedState(true) } returns Unit

        val savedStateHandle = createSavedStateHandle(false)
        val viewModel = SyncContactsViewModel(contactsRepository, permissionsController, savedStateHandle, effector)

        viewModel.state.test {
            awaitItem()

            viewModel.onSyncClick()

            val loaded = awaitItem()
            assertThat(loaded.isLoading).isFalse()

            verifySuspend { permissionsController.providePermission(Permission.CONTACTS) }
            verifySuspend { contactsRepository.syncContacts() }

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should handle DeniedException when permission is denied`() = runTest {
        everySuspend { permissionsController.providePermission(Permission.CONTACTS) } throws DeniedException(Permission.CONTACTS)
        val savedStateHandle = createSavedStateHandle(false)
        val viewModel = SyncContactsViewModel(contactsRepository, permissionsController, savedStateHandle, effector)

        viewModel.state.test {
            awaitItem()

            viewModel.onSyncClick()

            val error = awaitItem()
            assertThat(error.isLoading).isFalse()

            verifySuspend { permissionsController.providePermission(Permission.CONTACTS) }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should handle DeniedAlwaysException when permission is permanently denied`() = runTest {
        everySuspend { permissionsController.providePermission(Permission.CONTACTS) } throws DeniedAlwaysException(Permission.CONTACTS)

        val savedStateHandle = createSavedStateHandle(false)
        val viewModel = SyncContactsViewModel(contactsRepository, permissionsController, savedStateHandle, effector)

        viewModel.state.test {
            awaitItem()

            viewModel.onSyncClick()

            val error = awaitItem()
            assertThat(error.isLoading).isFalse()
            assertThat(error.deniedPermanently).isTrue()

            verifySuspend { permissionsController.providePermission(Permission.CONTACTS) }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should handle sync error with proper error message`() = runTest {
        val errorMessage = "Sync failed"
        everySuspend { contactsRepository.syncContacts() } throws Exception(errorMessage)

        val savedStateHandle = createSavedStateHandle(true)
        val viewModel = SyncContactsViewModel(contactsRepository, permissionsController, savedStateHandle, effector)

        viewModel.state.test {
            awaitItem()

            val loading = awaitItem()
            assertThat(loading.isLoading).isTrue()

            val error = awaitItem()
            assertThat(error.isLoading).isFalse()

            cancelAndIgnoreRemainingEvents()
        }
    }
}