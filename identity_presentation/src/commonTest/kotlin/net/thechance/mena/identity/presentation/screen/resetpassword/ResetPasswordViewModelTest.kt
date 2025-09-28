package net.thechance.mena.identity.presentation.screen.resetpassword

import app.cash.turbine.test
import dev.mokkery.MockMode
import dev.mokkery.answering.returns
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import net.thechance.mena.identity.domain.useCase.ResetPasswordUseCase
import net.thechance.mena.identity.domain.useCase.validation.mobileNumber.PasswordValidator
import net.thechance.mena.identity.presentation.screen.reset_password.ResetPasswordScreenUIEffect
import net.thechance.mena.identity.presentation.screen.reset_password.ResetPasswordScreenViewModel
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class ResetPasswordScreenViewModelTest {

    private lateinit var resetPasswordUseCase: ResetPasswordUseCase
    private lateinit var passwordValidator: PasswordValidator
    private lateinit var viewModel: ResetPasswordScreenViewModel

    private val testDispatcher = StandardTestDispatcher()

    private val validPassword = "Password123"
    private val invalidPassword = "short"

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        resetPasswordUseCase = mock(mode = MockMode.autofill)
        passwordValidator = mock(mode = MockMode.autofill)
        viewModel = ResetPasswordScreenViewModel(resetPasswordUseCase)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun setupValidPasswords() {
        viewModel.onNewPasswordChanged(validPassword)
        viewModel.onConfirmPasswordChanged(validPassword)
    }

    @Test
    fun `onNewPasswordChanged should update newPassword in state`() = runTest {
        val newPass = "NewP@ss123"
        viewModel.onNewPasswordChanged(newPass)
        viewModel.state.test {
            assertTrue { awaitItem().newPassword == newPass }
        }
    }

    @Test
    fun `onConfirmPasswordChanged should update confirmPassword in state`() = runTest {
        val confirmPass = "ConfirmP@ss123"
        viewModel.onConfirmPasswordChanged(confirmPass)
        viewModel.state.test {
            assertTrue { awaitItem().confirmPassword == confirmPass }
        }
    }

    @Test
    fun `onNewPasswordVisibilityToggled should toggle isNewPasswordVisible state`() = runTest {
        viewModel.onNewPasswordVisibilityToggled()
        viewModel.state.test {
            assertTrue { awaitItem().isNewPasswordVisible }
        }
    }

    @Test
    fun `onConfirmPasswordVisibilityToggled should toggle isConfirmPasswordVisible state`() =
        runTest {
            viewModel.onConfirmPasswordVisibilityToggled()
            viewModel.state.test {
                assertTrue { awaitItem().isConfirmPasswordVisible }
            }
        }

    @Test
    fun `checkResetButtonEnabled should be disabled when passwords do not match`() = runTest {
        viewModel.onNewPasswordChanged(validPassword)
        viewModel.onConfirmPasswordChanged("DifferentPass123")
        viewModel.state.test {
            assertFalse { awaitItem().isResetEnabled }
        }
    }

    @Test
    fun `checkResetButtonEnabled should be disabled when password is not secure`() = runTest {
        viewModel.onNewPasswordChanged(invalidPassword)
        viewModel.onConfirmPasswordChanged(invalidPassword)
        viewModel.state.test {
            assertFalse { awaitItem().isResetEnabled }
        }
    }

    @Test
    fun `checkResetButtonEnabled should be enabled when both passwords match and are secure`() =
        runTest {
            setupValidPasswords()
            viewModel.state.test {
                assertTrue { awaitItem().isResetEnabled }
            }
        }

    @Test
    fun `onResetPasswordClicked should show error message if passwords do not match`() = runTest {
        viewModel.onNewPasswordChanged(validPassword)
        viewModel.onConfirmPasswordChanged("DifferentPass123")

        viewModel.onResetPasswordClicked()
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.state.test {
            val state = awaitItem()
            assertTrue { state.errorMessage == "New password and confirm password do not match." }
        }
    }

    @Test
    fun `onResetPasswordClicked should call useCase and navigate on success`() = runTest {
        setupValidPasswords()

        everySuspend { resetPasswordUseCase.resetPassword(validPassword) } returns Unit

        viewModel.effect.test {
            viewModel.onResetPasswordClicked()

            viewModel.state.test {
                assertTrue { awaitItem().isLoading }
            }

            testDispatcher.scheduler.advanceUntilIdle()

            val effect = awaitItem()
            assertTrue { effect is ResetPasswordScreenUIEffect.NavigateBackToLogin }
        }

        verify { resetPasswordUseCase.resetPassword(validPassword) }
    }

    @Test
    fun `onBackClicked should send NavigateBackToLogin effect`() = runTest {
        viewModel.effect.test {
            viewModel.onBackClicked()
            val effect = awaitItem()
            assertTrue { effect is ResetPasswordScreenUIEffect.NavigateBackToLogin }
        }
    }
}