package net.thechance.mena.identity.presentation.screen.profile

import app.cash.turbine.test
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var viewModel: ProfileScreenViewModel

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ProfileScreenViewModel()
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should emit NavigateEditProfileScreen effect when onEditProfileInfoClicked`() = runTest {

        viewModel.effect.test {
            viewModel.onEditProfileInfoClicked()
            val emittedEffect = awaitItem()
            assertTrue { emittedEffect is ProfileScreenUIEffect.NavigateToEditProfileScreen }
            cancelAndConsumeRemainingEvents()

        }
    }

    @Test
    fun `should emit NavigateEditProfileScreen effect when onShareClicked`() = runTest {
        viewModel.effect.test {
            viewModel.onShareClicked()
            val emittedEffect = awaitItem()
            assertTrue { emittedEffect is ProfileScreenUIEffect.NavigateToEditProfileScreen }
            cancelAndConsumeRemainingEvents()

        }
    }

    @Test
    fun `should update state to show share bottom sheet when onInviteFriendsClicked`() = runTest {
        viewModel.onInviteFriendsClicked()
        viewModel.state.test {
            val updatedState = awaitItem()
            assertEquals(true, updatedState.showShareBottomSheet)
            cancelAndConsumeRemainingEvents()

        }
    }

    @Test
    fun `should emit NavigateToChangePasswordScreen effect when onChangePasswordClicked`() = runTest {
        viewModel.effect.test {
            viewModel.onChangePasswordClicked()
            val emittedEffect = awaitItem()
            assertTrue { emittedEffect is ProfileScreenUIEffect.NavigateToChangePasswordScreen }
        }
    }

    @Test
    fun `should emit NavigateToLocationPickerScreen effect when onAddressesClicked`() = runTest {
        viewModel.effect.test {
            viewModel.onAddressesClicked()
            val emittedEffect = awaitItem()
            assertTrue { emittedEffect is ProfileScreenUIEffect.NavigateToLocationPickerScreen }
        }
    }

    @Test
    fun `should emit NavigateToPrivacyAndPolicyScreen effect when onPrivacySettingsClicked`() = runTest {
        viewModel.effect.test {
            viewModel.onPrivacySettingsClicked()
            val emittedEffect = awaitItem()
            assertTrue { emittedEffect is ProfileScreenUIEffect.NavigateToPrivacyAndPolicyScreen }
        }
    }

    @Test
    fun `should update state to show language dialog when onLanguageClicked`() = runTest {
        viewModel.onLanguageClicked()
        viewModel.state.test {
            val updatedState = awaitItem()
            assertTrue(updatedState.showLanguageDialog)
        }
    }

    @Test
    fun `should update state to show theme dialog when onThemeClicked`() = runTest {
        viewModel.onThemeClicked()
        viewModel.state.test {
            testDispatcher.scheduler.advanceUntilIdle()
            val updatedState = awaitItem()
            assertTrue(updatedState.showThemeDialog)
        }
    }

    @Test
    fun `should emit NavigateToPrivacyAndPolicyScreen effect when onPrivacyAndPolicyClicked`() = runTest {
        viewModel.effect.test {
            viewModel.onPrivacyAndPolicyClicked()
            val emittedEffect = awaitItem()
            assertTrue { emittedEffect is ProfileScreenUIEffect.NavigateToPrivacyAndPolicyScreen }
        }
    }

    @Test
    fun `should emit NavigateContactUsScreen effect when onContactUsClicked`() = runTest {
        viewModel.effect.test {
            viewModel.onContactUsClicked()
            val emittedEffect = awaitItem()
            assertTrue { emittedEffect is ProfileScreenUIEffect.NavigateContactUsScreen }
        }
    }

    @Test
    fun `should update state to hide language dialog when onDismissLanguageDialog`() = runTest {
        viewModel.onDismissLanguageDialog()
        viewModel.state.test {
            testDispatcher.scheduler.advanceUntilIdle()
            val updatedState = awaitItem()
            assertEquals(false, updatedState.showLanguageDialog)
        }
    }

    @Test
    fun `should update state to hide share bottom sheet when onDismissBottomSheet`() = runTest {
        viewModel.state.test {
            viewModel.onDismissBottomSheet()
            testDispatcher.scheduler.advanceUntilIdle()
            val updatedState = awaitItem()
            assertEquals(false, updatedState.showShareBottomSheet)
        }
    }

    @Test
    fun `should update state to hide theme dialog when onDismissThemeDialog`() = runTest {
        viewModel.state.test {
            viewModel.onDismissThemeDialog()
            testDispatcher.scheduler.advanceUntilIdle()
            val updatedState = awaitItem()
            assertEquals(false, updatedState.showThemeDialog)
        }
    }
}

