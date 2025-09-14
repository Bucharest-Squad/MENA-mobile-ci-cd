package net.thechance.mena.core_chat.data.contacts.source.device

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class SettingDataSource(private val dataStore : DataStore<Preferences> ) {

    suspend fun getUserSyncedState(): Boolean {
        return dataStore.data.map {
            it[USER_SYNCED_STATE_KEY]
        }.firstOrNull() == true
    }

    suspend fun setUserSyncedState(state : Boolean){
        dataStore.edit {preferences ->
            preferences[USER_SYNCED_STATE_KEY] = state
        }
    }



    private companion object{
        val USER_SYNCED_STATE_KEY = booleanPreferencesKey("user_synced_state_key")

    }
}