package net.thechance.mena.identity.data.di

import com.russhwolf.settings.Settings
import io.ktor.client.engine.cio.CIO
import net.thechance.mena.identity.data.datasource.remoteDataSource.UserRemoteDataSource
import net.thechance.mena.identity.data.datasource.remoteDataSource.UserRemoteDataSourceImpl
import net.thechance.mena.identity.data.datasource.localDataSource.UserLocalDataSource
import net.thechance.mena.identity.data.datasource.localDataSource.UserLocalDataSourceImpl
import net.thechance.mena.identity.data.repository.AuthenticationRepositoryImpl
import net.thechance.mena.identity.data.utils.provideHttpClient
import net.thechance.mena.identity.domain.repository.AuthenticationRepository
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
            userLocalDataSource = get(),
            baseUrl = get<String>(named("baseUrl")),
        )
    }
    singleOf(::UserLocalDataSourceImpl) bind UserLocalDataSource::class
    singleOf(::AuthenticationRepositoryImpl) bind AuthenticationRepository::class
    singleOf(::UserRemoteDataSourceImpl) bind UserRemoteDataSource::class
}
