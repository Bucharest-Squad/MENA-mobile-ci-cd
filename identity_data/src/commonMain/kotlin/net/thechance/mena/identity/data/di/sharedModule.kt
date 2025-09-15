package net.thechance.mena.identity.data.di

import com.russhwolf.settings.Settings
import net.thechance.mena.identity.data.datasource.RemoteAuthService
import net.thechance.mena.identity.data.datasource.TokenManager
import net.thechance.mena.identity.data.datautils.provideHttpClient
import net.thechance.mena.identity.data.repository.AuthenticationRepositoryImpl
import net.thechance.mena.identity.domain.repository.AuthenticationRepository
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module
val sharedModule = module {
    singleOf(::Settings)
    singleOf(::provideHttpClient)
    singleOf(::TokenManager)
    singleOf(::RemoteAuthService)
    singleOf(::AuthenticationRepositoryImpl).bind<AuthenticationRepository>()
}
