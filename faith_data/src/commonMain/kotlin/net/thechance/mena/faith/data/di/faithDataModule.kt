package net.thechance.mena.faith.data.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import net.thechance.mena.faith.data.database.AyahDao
import net.thechance.mena.faith.data.database.QuranDatabase
import net.thechance.mena.faith.data.datastore.TilawahDataStore
import net.thechance.mena.faith.data.datastore.TilawahDataStoreImpl
import net.thechance.mena.faith.data.datastore.createDataStore
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val faithDataModule = module {

    includes(platformModule())

    single<AyahDao> { get<QuranDatabase>().getAyaDao() }
    single<DataStore<Preferences>> { createDataStore() }
    singleOf(::TilawahDataStoreImpl) bind TilawahDataStore::class

    includes(networkModule)
    includes(repositoryModule)


}


