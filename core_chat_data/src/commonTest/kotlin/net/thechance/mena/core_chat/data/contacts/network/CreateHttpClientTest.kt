package net.thechance.mena.core_chat.data.contacts.network

import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isNotNull
import dev.mokkery.answering.returns
import dev.mokkery.every
import dev.mokkery.matcher.any
import dev.mokkery.mock
import dev.mokkery.verify
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.HttpClientEngineConfig
import io.ktor.client.engine.HttpClientEngineFactory
import io.ktor.client.engine.config
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respondOk
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.plugin
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.Url
import io.ktor.utils.io.InternalAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import net.thechance.mena.core_chat.data.network.createHttpClient
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.test.BeforeTest
import kotlin.test.Test

class HttpClientFactoryTest {

    private val mockEngine = mock<HttpClientEngine>()
    private lateinit var mockFactory: HttpClientEngineFactory<HttpClientEngineConfig>

    @OptIn(InternalAPI::class)
    @BeforeTest
    fun setUp() {
        every { mockEngine.coroutineContext } returns EmptyCoroutineContext
        every { mockEngine.dispatcher } returns Dispatchers.Default
        every { mockEngine.close() } returns Unit
        every { mockEngine.config } returns HttpClientEngineConfig()

        // 👇 This is the missing piece
        every { mockEngine.install(any()) } returns Unit

        mockFactory = mock {
            every { create(any()) } returns mockEngine
        }

    }

    @Test
    fun `createHttpClient should return non null HttpClient when factory is provided`() {
        val client = createHttpClient("https://example.com", mockFactory)
        assertThat(client).isNotNull()
    }

    @Test
    fun `createHttpClient should delegate engine creation to factory`() {
        createHttpClient("https://example.com", mockFactory)
        verify { mockFactory.create(any()) }
    }

    @Test
    fun `createHttpClient should install ContentNegotiation plugin`() {
        val client = createHttpClient("https://example.com", mockFactory)
        assertThat(client.plugin(ContentNegotiation)).isNotNull()
    }

    @Test
    fun `createHttpClient should install Logging plugin`() {
        val client = createHttpClient("https://example.com", mockFactory)
        assertThat(client.plugin(Logging)).isNotNull()
    }

    @Test
    fun `createHttpClient should set baseUrl in default request`() = runTest {
        var capturedUrl: Url? = null

        val engineFactory = MockEngine.config {
            addHandler { request ->
                capturedUrl = request.url
                respondOk()
            }
        }

        val client = createHttpClient("https://example.com", engineFactory)

        client.get("/test") // triggers request

        assertThat(capturedUrl.toString()).isEqualTo("https://example.com/test")
    }

    @Test
    fun `createHttpClient should set default ContentType to application json`() = runTest {
        var capturedContentType: ContentType? = null

        val engineFactory = MockEngine.config {
            addHandler { request ->
                capturedContentType = request.body.contentType
                respondOk()
            }
        }

        val client = createHttpClient("https://example.com", engineFactory)

        client.post("/test") { setBody("{}") }

        assertThat(capturedContentType).isEqualTo(ContentType.Application.Json)
    }


    @Test
    fun `createHttpClient should set Accept header to application json`() = runTest {
        var capturedAccept: String? = null

        val engineFactory = MockEngine.config {
            addHandler { request ->
                capturedAccept = request.headers[HttpHeaders.Accept]
                respondOk()
            }
        }

        val client = createHttpClient("https://example.com", engineFactory)

        client.get("/test")

        assertThat(capturedAccept).isEqualTo(ContentType.Application.Json.toString())
    }

}
