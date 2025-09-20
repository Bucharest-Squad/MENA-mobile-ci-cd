package net.thechance.mena.identity.presentation.screen.login


import app.cash.turbine.test
import dev.mokkery.answering.returns
import dev.mokkery.answering.throws
import dev.mokkery.everySuspend
import dev.mokkery.mock
import kotlinx.coroutines.test.runTest
import net.thechance.mena.identity.domain.exception.InvalidCredentialsException
import net.thechance.mena.identity.domain.useCase.LoginUseCase
import kotlin.test.Test
import kotlin.test.assertTrue

class LoginViewModelTest {

    val useCase: LoginUseCase = mock()
    val viewModel = LoginScreenModel(useCase)

    @Test
    fun`should navigate to home screen when login success`()= runTest{
        //Given
        everySuspend { useCase.login("+20", "1100661617","12345678") } returns Unit
        //When
        viewModel.login()
        //Then
        viewModel.effect.test {
            val effect = awaitItem()
            assertTrue { effect is LoginScreenUIEffect.NavigateToHome }
            cancelAndIgnoreRemainingEvents()
        }

    }

    @Test
    fun `should show invalid credentials message when user does not exist`()= runTest{
        //Given
        val countryCode = "+20"
        val mobileNumber = "1100661617"
        val password = "12345678"
        val errorMessage = "Invalid credentials. Please check your phone number and password."
        everySuspend { useCase.login(countryCode, mobileNumber, password) } throws InvalidCredentialsException(countryCode, mobileNumber)
        //When
        viewModel.login()
        //Then
        viewModel.state.test {
            val state = awaitItem()
            assertTrue { state.errorMessage == errorMessage }
            cancelAndIgnoreRemainingEvents()
        }

    }

    @Test
    fun`should change phone number when user type on phone number text field`()= runTest{
        //Given
        val phoneNumber = "1100661617"
        //When
        viewModel.onPhoneChanged(phoneNumber)
        //Then
        viewModel.state.test {
            val state = awaitItem()
            assertTrue { state.phoneNumber == phoneNumber }
            cancelAndIgnoreRemainingEvents()
        }
    }

     @Test
    fun`should change password when user type on password text field`()= runTest{
        //Given
        val password = "12345678"
        //When
        viewModel.onPasswordChanged(password)
        //Then
        viewModel.state.test {
            val state = awaitItem()
            assertTrue { state.password == password }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should change country code when user select country code from country code dialog`()= runTest{
        val countryCode = "+20"

        viewModel.onPhoneCodeChanged(countryCode)

        viewModel.state.test {
            val state = awaitItem()
            assertTrue { state.countryPickerUIState.currentCountry.callingCode == countryCode }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should login button is enabled when phone number and password are valid` () = runTest {
        val countryCode = "+20"
        val phoneNumber = "1100661617"
        val password = "12345678"

        viewModel.state.test {
            viewModel.onPhoneCodeChanged(countryCode)
            viewModel.onPhoneChanged(phoneNumber)
            viewModel.onPasswordChanged(password)

            val state = awaitItem()
            assertTrue { state.isLoginEnabled }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should login button is disabled when phone number is empty` () = runTest {
        val password = "12345678"

        viewModel.state.test {
            viewModel.onPasswordChanged(password)

            val state = awaitItem()
            assertTrue { !state.isLoginEnabled }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should login button is disabled when password is empty` () = runTest {
        val phoneNumber = "1100661617"
        val countryCode = "+20"

        viewModel.state.test {
            viewModel.onPhoneCodeChanged(countryCode)
            viewModel.onPhoneChanged(phoneNumber)

            val state = awaitItem()
            assertTrue { !state.isLoginEnabled }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should login button is disabled when password length is less than 8 characters` () = runTest {
        val phoneNumber = "1100661617"
        val countryCode = "+20"
        val password = "1234"

        viewModel.state.test {
            viewModel.onPhoneCodeChanged(countryCode)
            viewModel.onPhoneChanged(phoneNumber)
            viewModel.onPasswordChanged(password)

            val state = awaitItem()
            assertTrue { !state.isLoginEnabled }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should login button is disabled when phone number does not match  country code` () = runTest {
        val phoneNumber = "11006616"
        val countryCode = "+20"
        val password = "12345678"

        viewModel.state.test {
            viewModel.onPhoneCodeChanged(countryCode)
            viewModel.onPhoneChanged(phoneNumber)
            viewModel.onPasswordChanged(password)

            val state = awaitItem()
            assertTrue { !state.isLoginEnabled }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun`should change password visibility when user click on password visibility button`() = runTest {
        //When
        viewModel.onPasswordVisibilityToggled()

        //Then
        viewModel.state.test {
            val state = awaitItem()
            assertTrue { state.isPasswordVisible }
            cancelAndIgnoreRemainingEvents()
        }

    }

    @Test
    fun`should navigate to forget password screen when user click on forget password button`()= runTest{
        //When
        viewModel.onForgotPasswordClicked()
        //Then
        viewModel.effect.test {
            val effect = awaitItem()
            assertTrue { effect is LoginScreenUIEffect.NavigateToForgotPassword }
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun`should navigate to register screen when user click on register button`()= runTest{
        //When
        viewModel.onRegisterClicked()
        //Then
        viewModel.effect.test {
            val effect = awaitItem()
            assertTrue { effect is LoginScreenUIEffect.NavigateToRegister }
            cancelAndIgnoreRemainingEvents()
        }
    }




}