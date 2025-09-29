package net.thechance.mena.identity.domain.useCase

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import net.thechance.mena.identity.domain.model.UserInfo
import net.thechance.mena.identity.domain.repository.UserRepository
import kotlin.test.Test
import kotlin.test.assertEquals

class ProfileUseCaseTest {

    private val userRepository = mockk<UserRepository>()
    private val profileUseCase = ProfileUseCase(userRepository)


    @Test
    fun`getUserInfo should return user info from local if available`() = runTest{

        coEvery { userRepository.getUserInfoFromLocal() } returns fakeUserInfo

        val actual = profileUseCase.getUserInfo().first()

        assertEquals(fakeUserInfo , actual )
    }
     @Test
    fun`getUserInfo should return user info from remote if local is null `() = runTest{

        coEvery { userRepository.getUserInfoFromLocal() } returns null

        coEvery { userRepository.fetchUserInfo() } returns fakeUserInfo

        val actual = profileUseCase.getUserInfo().first()

        assertEquals(fakeUserInfo , actual )
    }


    val fakeUserInfo = UserInfo(
        firstName = "The",
        lastName = "Chance",
        username = "TheChance@test.com",
        profileImageUrl = ""
    )


}