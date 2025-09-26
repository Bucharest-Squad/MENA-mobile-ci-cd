package net.thechance.mena.identity.domain.useCase

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import net.thechance.mena.identity.domain.model.UserInfo
import net.thechance.mena.identity.domain.repository.ProfileRepository
import kotlin.test.Test
import kotlin.test.assertEquals

class ProfileUseCaseTest {

    private val profileRepository = mockk<ProfileRepository>()
    private val profileUseCase = ProfileUseCase(profileRepository)


    @Test
    fun`getUserInfo should return user info from local if available`() = runTest{

        coEvery { profileRepository.getUserInfoFromLocal() } returns fakeUserInfo

        val actual = profileUseCase.getUserInfo().first()

        assertEquals(fakeUserInfo , actual )
    }
     @Test
    fun`getUserInfo should return user info from remote if local is null `() = runTest{

        coEvery { profileRepository.getUserInfoFromLocal() } returns null

        coEvery { profileRepository.fetchUserInfo() } returns fakeUserInfo

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