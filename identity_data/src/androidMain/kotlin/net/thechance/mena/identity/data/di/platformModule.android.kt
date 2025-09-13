package net.thechance.mena.identity.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import io.ktor.client.engine.okhttp.OkHttp
import net.thechance.mena.identity.data.datasource.TokenManager
import org.koin.dsl.module

actual val platformModule = module {
    single { OkHttp.create() }
    single<DataStore<Preferences>> {
        val context: Context = get()
        PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile("prefs") },
        )
    }

    single {
        TokenManager(get())
    }

}