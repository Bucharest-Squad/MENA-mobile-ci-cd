package net.thechance.mena.identity.presentation.di

import net.thechance.mena.identity.presentation.screen.login.LoginScreenModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val identityScreensModule = module {
    factoryOf(::LoginScreenModel)
}