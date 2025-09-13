package net.thechance.mena.identity.data.di

import net.thechance.mena.identity.data.datasource.RemoteAuthService
import net.thechance.mena.identity.data.datautils.provideHttpClient
import net.thechance.mena.identity.data.repository.AuthenticationRepositoryImpl
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import net.thechance.mena.identity.domain.repository.AuthenticationRepository
import org.koin.dsl.bind

expect val platformModule: Module
val sharedModule = module {
    singleOf (::provideHttpClient)
    singleOf(::RemoteAuthService)
    singleOf(::AuthenticationRepositoryImpl).bind<AuthenticationRepository>()
}
