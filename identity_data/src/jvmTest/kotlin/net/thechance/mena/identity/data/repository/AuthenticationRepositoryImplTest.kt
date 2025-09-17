package net.thechance.mena.identity.data.repository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import net.thechance.mena.identity.data.datasource.AuthRemoteDataSource
import net.thechance.mena.identity.data.datasource.LocalDataSource
import net.thechance.mena.identity.data.dto.auth.LoginRequestDto
import net.thechance.mena.identity.data.dto.auth.LoginResponseDto
import net.thechance.mena.identity.data.dto.auth.RefreshRequestDto
import org.junit.Test

class AuthenticationRepositoryImplTest {

    private val authRemoteDataSource = mockk<AuthRemoteDataSource>()
    private val localDataSource = mockk<LocalDataSource>(relaxed = true)
    private val authenticationRepository = AuthenticationRepositoryImpl(
        authRemoteDataSource, localDataSource
    )


    @Test
    fun `should return loginResponse on login given phone and password and save tokens`() = runTest {
        //given
        val mobileNumber = "0123456789"
        val passWord = "testpassword"
        val countryCoed="+20"
        coEvery { authRemoteDataSource.login(any()) } returns fakeLoginResponse

        //when
        authenticationRepository.login(countryCoed,mobileNumber, passWord)

        //then
        coVerify { authRemoteDataSource.login(LoginRequestDto(countryCoed+mobileNumber, passWord)) }
        coVerify { localDataSource.saveAccessToken(fakeLoginResponse.accessToken) }
        coVerify { localDataSource.saveRefreshToken(fakeLoginResponse.refreshToken) }
    }

    @Test
    fun `getAccessToken refreshes and saves tokens`() = runTest {
        //given
        coEvery { localDataSource.getRefreshToken() } returns "old_refresh_token"
        coEvery { authRemoteDataSource.refreshToken(any()) } returns fakeLoginResponse
        coEvery { localDataSource.getAccessToken() } returns fakeLoginResponse.accessToken

        //when
        val result = authenticationRepository.getToken()

        //then
        coVerify { localDataSource.getRefreshToken() }
        coVerify { authRemoteDataSource.refreshToken(RefreshRequestDto("old_refresh_token")) }
        coVerify { localDataSource.saveAccessToken(fakeLoginResponse.accessToken) }
        coVerify { localDataSource.saveRefreshToken(fakeLoginResponse.refreshToken) }
        coVerify { localDataSource.getAccessToken() }

        assertEquals(fakeLoginResponse.accessToken, result)
    }
}
val fakeLoginResponse = LoginResponseDto(
    accessToken = "fake_access_token",
    refreshToken = "fake_refresh_token"
)