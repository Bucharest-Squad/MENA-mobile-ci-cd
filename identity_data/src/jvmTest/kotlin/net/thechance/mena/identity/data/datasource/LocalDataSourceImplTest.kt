package net.thechance.mena.identity.data.datasource


import assertk.assertThat
import assertk.assertions.isEmpty
import assertk.assertions.isEqualTo
import com.russhwolf.settings.Settings
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import kotlinx.serialization.json.Json
import net.thechance.mena.identity.data.datasource.localDataSource.UserLocalDataSourceImpl
import net.thechance.mena.identity.data.mapper.toDto
import net.thechance.mena.identity.domain.model.UserInfo
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull


class LocalDataSourceImplTest {

    private val mockSettings: Settings = mockk(relaxed = true)
    private val localDataSource: UserLocalDataSourceImpl = UserLocalDataSourceImpl(mockSettings)

    private val json = Json { ignoreUnknownKeys = true }



    @Test
    fun `saveAccessToken should store token in settings`() {
        // Given
        val accessToken = "test_access_token"
        every { mockSettings.putString(any(), any()) } just Runs

        // When
        localDataSource.saveAccessToken(accessToken)

        // Then
        verify { mockSettings.putString(UserLocalDataSourceImpl.ACCESS_TOKEN, accessToken) }
    }

    @Test
    fun `getAccessToken should return stored token from settings`() {
        // Given
        val expectedToken = "stored_access_token"
        every { mockSettings.getString(UserLocalDataSourceImpl.ACCESS_TOKEN, "") } returns expectedToken

        // When
        val result = localDataSource.getAccessToken()

        // Then
        assertThat(result).isEqualTo(result)
        verify { mockSettings.getString(UserLocalDataSourceImpl.ACCESS_TOKEN, "") }
    }

    @Test
    fun `getAccessToken should return empty string when no token stored`() {
        // Given
        every { mockSettings.getString(UserLocalDataSourceImpl.ACCESS_TOKEN, "") } returns ""

        // When
        val result = localDataSource.getAccessToken()

        // Then
        assertThat(result).isEmpty()
        verify { mockSettings.getString(UserLocalDataSourceImpl.ACCESS_TOKEN, "") }
    }

    @Test
    fun `saveRefreshToken should store token in settings`() {
        // Given
        val refreshToken = "test_refresh_token"
        every { mockSettings.putString(any(), any()) } just Runs

        // When
        localDataSource.saveRefreshToken(refreshToken)

        // Then
        verify { mockSettings.putString(UserLocalDataSourceImpl.REFRESH_TOKEN, refreshToken) }
    }

    @Test
    fun `getRefreshToken should return stored token from settings`() {
        // Given
        val expectedToken = "stored_refresh_token"
        every {
            mockSettings.getString(
                UserLocalDataSourceImpl.REFRESH_TOKEN,
                ""
            )
        } returns expectedToken

        // When
        val result = localDataSource.getRefreshToken()

        // Then
        assertThat(result).isEqualTo(expectedToken)
        verify { mockSettings.getString(UserLocalDataSourceImpl.REFRESH_TOKEN, "") }
    }

    @Test
    fun `getRefreshToken should return empty string when no token stored`() {
        // Given
        every { mockSettings.getString(UserLocalDataSourceImpl.REFRESH_TOKEN, "") } returns ""

        // When
        val result = localDataSource.getRefreshToken()

        // Then
        assertThat(result).isEmpty()
        verify { mockSettings.getString(UserLocalDataSourceImpl.REFRESH_TOKEN, "") }
    }

    @Test
    fun`getUserInfo should return stored user info from settings when user exists`() {

        val encoded = json.encodeToString(fakeUser.toDto())
        every { mockSettings.getStringOrNull(UserLocalDataSourceImpl.USER_KEY) } returns encoded

        val actual = localDataSource.getUserInfo()

        assertEquals( fakeUser, actual)

    }
    @Test
    fun `getUserInfo should return null when stored json is corrupted`() {
        every { mockSettings.putString(UserLocalDataSourceImpl.USER_KEY,"json") } just Runs
        assertNull(localDataSource.getUserInfo())
    }

    @Test
    fun`getUserInfo should return null from settings when user does not exist`() {

        val encoded = json.encodeToString(fakeUser.toDto())
        every { mockSettings.getStringOrNull(UserLocalDataSourceImpl.USER_KEY) } returns null

        val actual = localDataSource.getUserInfo()

        assertNull(actual)

    }

    @Test
    fun `saveUserInfo should store user in settings`(){
        val encoded = json.encodeToString(fakeUser.toDto())

        localDataSource.saveUserInfo(fakeUser)

        verify { mockSettings.putString(UserLocalDataSourceImpl.USER_KEY, encoded) }
    }

    private val fakeUser = UserInfo(
        firstName = "The",
        lastName = "Chance",
        username = "TheChance@test.com",
        profileImageUrl = ""
    )
}