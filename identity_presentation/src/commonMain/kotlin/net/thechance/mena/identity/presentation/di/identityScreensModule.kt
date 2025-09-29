package net.thechance.mena.identity.presentation.di

import net.thechance.mena.identity.presentation.screen.forget_password.ForgetPasswordScreenModel
import net.thechance.mena.identity.presentation.screen.login.LoginScreenViewModel
import net.thechance.mena.identity.presentation.screen.profile.ProfileScreenViewModel
import net.thechance.mena.identity.presentation.screen.register.RegisterScreenModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val identityScreensModule = module {
    factoryOf(::LoginScreenViewModel)
    factoryOf(::RegisterScreenModel)
    factoryOf(::ForgetPasswordScreenModel)
    factoryOf(::ProfileScreenViewModel)
}