package net.thechance.mena.identity.data.di

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.russhwolf.settings.Settings
import io.ktor.client.engine.cio.CIO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import net.thechance.mena.identity.data.dataSource.local.database.dao.UserDao
import net.thechance.mena.identity.data.repository.AuthenticationRepositoryImpl
import net.thechance.mena.identity.domain.repository.AuthenticationRepository
import org.example.project.data.database.IdentityDatabase
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

expect val IdentityPlatformModule: Module
val identityDataModule = module {
    single { CIO.create() }
    singleOf(::Settings)
    single {
        provideHttpClient(
            engine = get(),
            baseUrl = get<String>(named("baseUrl")),
            settings = get()
        )
    }

    single { provideDatabaseBuilder() }
    single<IdentityDatabase> { getRoomDatabase(builder = get()) }

    single<UserDao> { get<IdentityDatabase>().getUserDao() }

    singleOf(::AuthenticationRepositoryImpl) bind AuthenticationRepository::class
}

fun getRoomDatabase(builder: RoomDatabase.Builder<IdentityDatabase>): IdentityDatabase {
    return builder
        .fallbackToDestructiveMigration(dropAllTables = true)
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}