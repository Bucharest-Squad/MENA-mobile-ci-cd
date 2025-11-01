package net.thechance.mena.admin_panel.domin.service

import dev.mokkery.MockMode
import dev.mokkery.answering.returns
import dev.mokkery.answering.throws
import dev.mokkery.everySuspend
import dev.mokkery.mock
import dev.mokkery.verifySuspend
import kotlinx.coroutines.test.runTest
import net.thechance.mena.admin_panel.domain.repository.AdminAuthenticationRepository
import net.thechance.mena.admin_panel.data.service.AuthenticationService
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class AuthenticationServiceTest {

    private lateinit var adminAuthenticationRepository: AdminAuthenticationRepository
    private lateinit var authenticationService: AuthenticationService

    @BeforeTest
    fun setup() {
        adminAuthenticationRepository = mock(mode = MockMode.autofill)
        authenticationService = AuthenticationService(adminAuthenticationRepository)
    }

    @Test
    fun `getAccessToken should return token from repository`() = runTest {
        everySuspend { adminAuthenticationRepository.getAccessToken() } returns FAKE_ACCESS_TOKEN

        val result = authenticationService.getAccessToken()

        assertEquals(FAKE_ACCESS_TOKEN, result)
        verifySuspend { adminAuthenticationRepository.getAccessToken() }
    }

    @Test
    fun `refreshToken should return new token from repository`() = runTest {
        everySuspend { adminAuthenticationRepository.refreshAccessToken() } returns NEW_ACCESS_TOKEN

        val result = authenticationService.refreshToken()

        assertEquals(NEW_ACCESS_TOKEN, result)
        verifySuspend { adminAuthenticationRepository.refreshAccessToken() }
    }

    @Test
    fun `getAccessToken should throw exception when repository fails`() = runTest {
        everySuspend { adminAuthenticationRepository.getAccessToken() } throws Exception("failed")

        assertFailsWith<Exception> {
            authenticationService.getAccessToken()
        }
    }

    private companion object {
        const val FAKE_ACCESS_TOKEN = "fake_access_token"
        const val NEW_ACCESS_TOKEN = "new_access_token"
    }
}