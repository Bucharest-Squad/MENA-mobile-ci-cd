package net.thechance.mena.identity.data.datasource

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first
import net.thechance.mena.identity.data.datasource.DataStoreKeys.REFRESH_TOKEN
import net.thechance.mena.identity.data.datasource.DataStoreKeys.USER_ACCESS_TOKEN

actual class TokenManager(
    private val dataStore: DataStore<Preferences>
) {
    actual suspend fun saveAccessToken(accessToken: String) {
        dataStore.edit { prefs ->
            prefs[USER_ACCESS_TOKEN] = accessToken
        }
    }

    actual suspend fun getAccessToken(): String {
        return dataStore.data.first()[USER_ACCESS_TOKEN].orEmpty()
    }

    actual suspend fun saveRefreshToken(refreshToken: String) {
        dataStore.edit { prefs ->
            prefs[REFRESH_TOKEN] = refreshToken
        }
    }

    actual suspend fun getRefreshToken(): String {
        return dataStore.data.first()[REFRESH_TOKEN].orEmpty()
    }

    actual suspend fun clearTokens() {
        dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}
