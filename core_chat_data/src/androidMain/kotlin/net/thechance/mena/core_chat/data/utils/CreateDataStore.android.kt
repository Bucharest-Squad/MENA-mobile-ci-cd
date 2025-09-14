package net.thechance.mena.core_chat.data.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import net.thechance.mena.core_chat.data.contacts.utils.createDataStore
import net.thechance.mena.core_chat.data.contacts.utils.dataStoreName

fun createDataStore(context : Context): DataStore<Preferences>{
    return createDataStore {
        context.filesDir.resolve(dataStoreName).absolutePath
    }
}