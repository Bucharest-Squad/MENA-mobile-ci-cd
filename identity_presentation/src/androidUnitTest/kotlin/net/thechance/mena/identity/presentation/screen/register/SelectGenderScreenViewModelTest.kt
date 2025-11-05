package net.thechance.mena.identity.presentation.screen.register

import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import net.thechance.mena.identity.domain.entity.Gender
import net.thechance.mena.identity.domain.model.RegisterRequest
import net.thechance.mena.identity.domain.repository.RegisterRepository
import net.thechance.mena.identity.helper.BaseCoroutineTest
import net.thechance.mena.identity.presentation.screen.register.selectGender.SelectGenderScreenUIEffect
import net.thechance.mena.identity.presentation.screen.register.selectGender.SelectGenderScreenViewModel
import org.junit.Before
import org.junit.Test

class SelectGenderScreenViewModelTest: BaseCoroutineTest() {
    private lateinit var selectGenderScreenViewModel: SelectGenderScreenViewModel
    private val registerRepository = mockk<RegisterRepository>()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        selectGenderScreenViewModel = SelectGenderScreenViewModel(
            registerRepository = registerRepository,
            phoneNumber = net.thechance.mena.identity.domain.entity.PhoneNumber("+964", "7901234567"),
            firstName = "Mohammed",
            lastName = "Ahmed",
            username = "mohammed123",
            password = "Password123",
            birthDate = LocalDate(2000, 1, 1),
            dispatcher = testDispatcher
        )
    }

    @Test
    fun `onChangeGender should update gender`() {
        val gender = Gender.MALE

        selectGenderScreenViewModel.onChangeGender(gender)
    }

    @Test
    fun `onClickRegister should navigate to upload profile image screen`() = runTest {
        coEvery { registerRepository.register(any<RegisterRequest>()) } returns Unit
        
        selectGenderScreenViewModel.onChangeGender(Gender.MALE)
        
        selectGenderScreenViewModel.effect.test {
            selectGenderScreenViewModel.onClickRegister()
            testDispatcher.scheduler.advanceUntilIdle()
            
            assert(awaitItem() == SelectGenderScreenUIEffect.NavigateToUploadProfileImage)
        }
    }
}