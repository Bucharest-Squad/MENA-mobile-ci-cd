package net.thechance.mena.identity.data.datasource

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import net.thechance.mena.identity.data.datasource.remoteDataSource.UserRemoteDataSourceImpl
import net.thechance.mena.identity.data.dto.auth.LoginRequestDto
import net.thechance.mena.identity.data.dto.auth.LoginResponseDto
import net.thechance.mena.identity.data.dto.auth.RefreshRequestDto
import net.thechance.mena.identity.data.dto.profile.ProfileResponseDto
import org.junit.Test
import kotlin.test.BeforeTest
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class RemoteDataSourceImplTest {

    private val client: HttpClient = mockk(relaxed = true)
    private lateinit var remoteDataSource: UserRemoteDataSourceImpl

    @BeforeTest
    fun setUp() {
        remoteDataSource = UserRemoteDataSourceImpl(client)
    }


    @Test
    fun `login should return response when status is 200`() = runTest {
        val mockEngine = MockEngine { _ ->
            respond(
                content = Json.encodeToString(fakeLoginResponse),
                status = HttpStatusCode.OK,
                headers = headersOf(
                    HttpHeaders.ContentType, ContentType.Application.Json.toString()
                )
            )
        }
        val client = HttpClient(mockEngine) {
            install(ContentNegotiation) { json() }
        }
        remoteDataSource = UserRemoteDataSourceImpl(client)
        // When
        val actual = remoteDataSource.login(LoginRequestDto("110061617", "12345678"))

        // Then
        assertEquals(fakeLoginResponse, actual)
    }

    @Test
    fun `login should throw ClientRequestException when status is not 200`() = runTest {
        val mockEngine = MockEngine { _ ->
            respond(
                content = "Unauthorized",
                status = HttpStatusCode.Unauthorized,
                headers = headersOf(
                    HttpHeaders.ContentType, ContentType.Application.Json.toString()
                )
            )
        }
        val client = HttpClient(mockEngine) {
            install(ContentNegotiation) { json() }
        }
        remoteDataSource = UserRemoteDataSourceImpl(client)
        //When & Then
        assertFailsWith<ClientRequestException> {
            remoteDataSource.login(LoginRequestDto("110061617", "12345678"))
        }
    }

    @Test
    fun `refresh token should return response when status is 200`() = runTest {
        val mockEngine = MockEngine { _ ->
            respond(
                content = Json.encodeToString(fakeLoginResponse),
                status = HttpStatusCode.OK,
                headers = headersOf(
                    HttpHeaders.ContentType, ContentType.Application.Json.toString()
                )
            )
        }
        val client = HttpClient(mockEngine){
            install(ContentNegotiation){ json() }
        }
        remoteDataSource = UserRemoteDataSourceImpl(client)
        //When
        val actual  = remoteDataSource.refreshToken(RefreshRequestDto("fake_refresh_token"))
        //Then
        assertEquals(fakeLoginResponse , actual)
    }

    @Test
    fun `refresh token should throw ClientRequestException when status is not 200`() = runTest {
        val mockEngine = MockEngine { _ ->
            respond(
                content = "Unauthorized",
                status = HttpStatusCode.Unauthorized,
                headers = headersOf(
                    HttpHeaders.ContentType, ContentType.Application.Json.toString()
                )
            )
        }
        val client = HttpClient(mockEngine) {
            install(ContentNegotiation) { json() }
        }
        remoteDataSource = UserRemoteDataSourceImpl(client)
        //When & Then
        assertFailsWith<ClientRequestException> {
            remoteDataSource.refreshToken(RefreshRequestDto("fake_refresh_token"))
        }
    }

    @Test
    fun `getUserInfo should return response when status is 200`() = runTest {
        val mockEngine = MockEngine { _ ->
            respond(
                content = Json.encodeToString(fakeProfileResponse),
                status = HttpStatusCode.OK,
                headers = headersOf(
                    HttpHeaders.ContentType, ContentType.Application.Json.toString()
                )
            )
        }
        val client = HttpClient(mockEngine) {
            install(ContentNegotiation) { json() }
        }
        remoteDataSource = UserRemoteDataSourceImpl(client)

        val actual = remoteDataSource.getUserInfo()

        assertEquals(fakeProfileResponse, actual)
    }

    @Test
    fun `getUserInfo should throw ClientRequestException when status is not 200`() = runTest {
        val mockEngine = MockEngine { _ ->
            respond(
                content = "Unauthorized",
                status = HttpStatusCode.Unauthorized,
                headers = headersOf(
                    HttpHeaders.ContentType, ContentType.Application.Json.toString()
                )
            )
        }
        val client = HttpClient(mockEngine) {
            install(ContentNegotiation) { json() }
        }
        remoteDataSource = UserRemoteDataSourceImpl(client)
        //When & Then
        assertFailsWith<ClientRequestException> {
            remoteDataSource.getUserInfo()
        }
    }

    companion object {
        private val fakeLoginResponse = LoginResponseDto(
            accessToken = "fake_access_token", refreshToken = "fake_refresh_token"
        )
        val fakeProfileResponse = ProfileResponseDto(
            firstName = "The",
            lastName = "Chance",
            username = "TheChance@test.com",
            profileImageUrl = ""
        )

    }
}
