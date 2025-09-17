package net.thechance.mena.core_chat.contacts


import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import kotlinx.serialization.json.Json
import net.thechance.mena.core_chat.contacts.fakes.FakeContactsProvider
import net.thechance.mena.core_chat.contacts.fakes.FakeDataStore
import net.thechance.mena.core_chat.data.contacts.ContactsRepositoryImpl
import kotlin.test.BeforeTest

class ContactsRepositoryImplTest {


    private val mockContactsProvider = FakeContactsProvider(listOf())

    private val mockDataStore = FakeDataStore()

    private lateinit var repository: ContactsRepositoryImpl
    private val json = Json { ignoreUnknownKeys = true }

    @BeforeTest
    fun setUp() {
        // default HttpClient – replaced per test
        repository = ContactsRepositoryImpl(
            client = HttpClient(MockEngine { respond("") }),
            contactsProvider = mockContactsProvider,
            dataStore = mockDataStore
        )
    }

}



