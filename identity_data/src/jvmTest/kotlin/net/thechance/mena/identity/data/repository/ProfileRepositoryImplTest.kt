package net.thechance.mena.identity.data.repository

import assertk.assertFailure
import assertk.assertions.isInstanceOf
import io.ktor.client.plugins.ClientRequestException
import io.ktor.http.HttpStatusCode
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.thechance.mena.identity.data.datasource.localDataSource.UserLocalDataSource
import net.thechance.mena.identity.data.datasource.remoteDataSource.UserRemoteDataSource
import net.thechance.mena.identity.data.dto.profile.ProfileResponseDto
import net.thechance.mena.identity.data.mapper.toDomain
import net.thechance.mena.identity.domain.exception.InvalidCredentialsException
import net.thechance.mena.identity.domain.exception.UnAuthorizedException
import net.thechance.mena.identity.domain.exception.UnknownException
import net.thechance.mena.identity.domain.model.UserInfo
import org.junit.Test
import kotlin.test.assertEquals

class ProfileRepositoryImplTest {

    private val userRemoteDataSource = mockk<UserRemoteDataSource>()
    private val userLocalDataSource = mockk<UserLocalDataSource>(relaxed = true)
    private val profileRepositoryImpl = ProfileRepositoryImpl(
        userRemoteDataSource, userLocalDataSource
    )

    @Test
    fun `getUserInfoFromLocal should return user from local data source`() = runTest {

        coEvery { userLocalDataSource.getUserInfo() } returns fakeUserInfo

        val actual = profileRepositoryImpl.getUserInfoFromLocal()

        assertEquals( fakeUserInfo, actual)
    }

    @Test
    fun `fetchUserInfo should return user info from remote data source `() = runTest {

        coEvery { userRemoteDataSource.getUserInfo() } returns fakeProfileResponse

        val actual = profileRepositoryImpl.fetchUserInfo()

        assertEquals(actual,fakeProfileResponse.toDomain())
    }

    @Test
    fun `fetchUserInfo should throw UnAuthorizedException when remote returns 401`() = runTest {
        val clientException = ClientRequestException(
            response = mockk(relaxed = true) {
                every { status } returns HttpStatusCode.Unauthorized
            },
            cachedResponseText = "Unauthorized"
        )
        coEvery { userRemoteDataSource.getUserInfo() } throws clientException

        assertFailure {
            profileRepositoryImpl.fetchUserInfo()
        }.isInstanceOf<UnAuthorizedException>()
    }

    @Test
    fun `fetchUserInfo should throw InvalidCredentialsException when remote returns 404`() = runTest {
        val clientException = ClientRequestException(
            response = mockk(relaxed = true) {
                every { status } returns HttpStatusCode.NotFound
            },
            cachedResponseText = "Not Found"
        )
        coEvery { userRemoteDataSource.getUserInfo() } throws clientException

        assertFailure {
            profileRepositoryImpl.fetchUserInfo()
        }.isInstanceOf<InvalidCredentialsException>()

    }

    @Test
    fun `fetchUserInfo should throw UnknownException when remote returns other error`() = runTest {

        val clientException = ClientRequestException(
            response = mockk(relaxed = true) {
                every { status } returns HttpStatusCode.InternalServerError
            },
            cachedResponseText = "Internal Server Error"
        )
        coEvery { userRemoteDataSource.getUserInfo() } throws clientException

        assertFailure {
            profileRepositoryImpl.fetchUserInfo()
        }.isInstanceOf<UnknownException>()
    }

    @Test
    fun`should call saveUserInfo after successful fetchUserInfo`() = runTest {

        coEvery { userRemoteDataSource.getUserInfo() } returns fakeProfileResponse

        profileRepositoryImpl.fetchUserInfo()

        coVerify {
            userLocalDataSource.saveUserInfo(
                fakeProfileResponse.toDomain()
            )
        }
    }


    val fakeUserInfo = UserInfo(
        firstName = "The",
        lastName = "Chance",
        username = "TheChance@test.com",
        profileImageUrl = ""
    )
    val fakeProfileResponse = ProfileResponseDto(
            firstName = "The",
            lastName = "Chance",
            username = "TheChance@test.com",
            profileImageUrl = ""
        )
}