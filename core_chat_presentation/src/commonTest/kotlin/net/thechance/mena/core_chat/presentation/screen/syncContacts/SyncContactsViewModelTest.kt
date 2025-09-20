package net.thechance.mena.core_chat.presentation.screen.syncContacts

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isFalse
import assertk.assertions.isNotNull
import assertk.assertions.isTrue
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import dev.mokkery.answering.returns
import dev.mokkery.answering.throws
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verify
import dev.mokkery.verifySuspend
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import net.thechance.mena.core_chat.domain.repository.ContactsRepository
import kotlin.test.Test

class SyncContactsViewModelTest {

    private val contactsRepository = mock<ContactsRepository>()
    private val permissionsController = mock<PermissionsController>()

    @Test
    fun `should set isFirstSync to false and call syncContacts when onForceSync is true`() = runTest {
        everySuspend { contactsRepository.syncContacts() } returns Unit
        everySuspend { contactsRepository.setUserSyncedState(true) } returns Unit

        val viewModel = SyncContactsViewModel(contactsRepository, permissionsController)

        viewModel.onForceSync(true)
        val result = viewModel.state.first()

        print(result.isFirstSync)
        assertThat(result.isFirstSync).isFalse()
        verifySuspend { contactsRepository.syncContacts() }
    }

    @Test
    fun `should set isFirstSync to true and showSyncView when onForceSync is false`() = runTest {
        val viewModel = SyncContactsViewModel(contactsRepository, permissionsController)

        viewModel.onForceSync(false)
        val result = viewModel.state.first()

        assertThat(result.isFirstSync).isTrue()
        verify {
            result.showSyncView
            result.isLoading.not()
        }
    }

    @Test
    fun `should request permission and sync contacts when onSyncClick is called successfully`() = runTest {
        everySuspend { permissionsController.providePermission(Permission.CONTACTS) } returns Unit
        everySuspend { contactsRepository.syncContacts() } returns Unit
        everySuspend { contactsRepository.setUserSyncedState(true) } returns Unit

        val viewModel = SyncContactsViewModel(contactsRepository, permissionsController)

        viewModel.state.test {
            awaitItem()

            viewModel.onSyncClick()

            val loaded = awaitItem()
            verify { loaded.isLoading }.equals(false)

            verifySuspend { permissionsController.providePermission(Permission.CONTACTS) }
            verifySuspend { contactsRepository.syncContacts() }

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should handle DeniedException when permission is denied`() = runTest {
        everySuspend { permissionsController.providePermission(Permission.CONTACTS) } throws DeniedException(Permission.CONTACTS)
        val viewModel = SyncContactsViewModel(contactsRepository, permissionsController)

        viewModel.state.test {
            awaitItem()

            viewModel.onSyncClick()

            val error = awaitItem()
            verify { error.isLoading }.equals(false)
            verify { error.snackBarData?.title }.equals("Permission denied")
            verify { error.snackBarData?.message }.equals("Contacts permission is required to sync contacts")

            verifySuspend { permissionsController.providePermission(Permission.CONTACTS) }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should handle DeniedAlwaysException when permission is permanently denied`() = runTest {
        everySuspend { permissionsController.providePermission(Permission.CONTACTS) } throws DeniedAlwaysException(Permission.CONTACTS)

        val viewModel = SyncContactsViewModel(contactsRepository, permissionsController)

        viewModel.state.test {
            awaitItem()

            viewModel.onSyncClick()

            val error = awaitItem()
            verify { error.isLoading }.equals(false)
            verify { error.deniedPermanently }.equals(true)
            verify { error.snackBarData?.title }.equals("Permission denied")
            verify { error.snackBarData?.message }.equals("Contacts permission is required to sync contacts")

            verifySuspend { permissionsController.providePermission(Permission.CONTACTS) }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should clear snackBarData when onSnackBarDismiss is called`() = runTest {
        everySuspend { permissionsController.providePermission(Permission.CONTACTS) } throws DeniedException(Permission.CONTACTS)
        val viewModel = SyncContactsViewModel(contactsRepository, permissionsController)

        viewModel.state.test {
            awaitItem()

            viewModel.onSyncClick()

            val stateWithSnackBar = awaitItem()
            assertThat(stateWithSnackBar.snackBarData).isNotNull()

            viewModel.onSnackBarDismiss()

            val result = awaitItem()

            verify { result.snackBarData }.equals(null)
            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `should handle sync error with proper error message`() = runTest {
        val errorMessage = "Sync failed"
        everySuspend { contactsRepository.syncContacts() } throws Exception(errorMessage)

        val viewModel = SyncContactsViewModel(contactsRepository, permissionsController)

        viewModel.state.test {
            awaitItem()

            viewModel.onForceSync(true)

            val loading = awaitItem()
            assertThat(loading.isLoading).isTrue()

            val error = awaitItem()


            verify { error.isLoading }.equals(false)
            verify { error.snackBarData?.title }.equals("Something went wrong")
            verify { error.snackBarData?.message }.equals(errorMessage)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should handle sync error with unknown error when message is null`() = runTest {
        everySuspend { contactsRepository.syncContacts() } throws Exception()

        val viewModel = SyncContactsViewModel(contactsRepository, permissionsController)

        viewModel.state.test {
            awaitItem()

            viewModel.onForceSync(true)

            val loading = awaitItem()
            assertThat(loading.isLoading).isTrue()

            val error = awaitItem()

            verify { error.isLoading }.equals(false)
            verify { error.snackBarData?.title }.equals("Something went wrong")
            verify { error.snackBarData?.message }.equals("Unknown error")

            cancelAndIgnoreRemainingEvents()
        }
    }
}