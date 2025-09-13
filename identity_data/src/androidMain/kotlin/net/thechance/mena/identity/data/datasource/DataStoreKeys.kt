package net.thechance.mena.identity.data.datasource

import androidx.datastore.preferences.core.stringPreferencesKey

object DataStoreKeys {
    val USER_ACCESS_TOKEN = stringPreferencesKey("access_token")
    val REFRESH_TOKEN = stringPreferencesKey("refresh_token")
}