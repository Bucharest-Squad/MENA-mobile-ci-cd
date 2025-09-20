package net.thechance.mena.core_chat.data.contacts.utils
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


class DataStoreFactoryTest {

    @Test
    fun `createDataStore should create usable DataStore when valid path provided`() = runBlocking {
        val pathProvider = { "test_datastore.preferences_pb" }

        val dataStore: DataStore<Preferences> = createDataStore(pathProvider)

        assertNotNull(dataStore)

        val testKey = intPreferencesKey("test_key")
        dataStore.edit { prefs -> prefs[testKey] = 99 }
        val value = dataStore.data.first()[testKey]

        assertEquals(99, value)
    }
}
