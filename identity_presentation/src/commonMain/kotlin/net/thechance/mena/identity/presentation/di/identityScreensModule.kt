package net.thechance.mena.identity.presentation.di

import net.thechance.mena.identity.presentation.screen.forgetPassword.ForgetPasswordScreenViewModel
import net.thechance.mena.identity.presentation.screen.forgetPasswordOtp.OtpScreenViewModel
import net.thechance.mena.identity.presentation.screen.login.LoginScreenViewModel
import net.thechance.mena.identity.presentation.screen.profile.ProfileScreenViewModel
import net.thechance.mena.identity.presentation.screen.register.RegisterScreenModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val identityScreensModule = module {
    factoryOf(::LoginScreenViewModel)
    factoryOf(::RegisterScreenModel)
    factoryOf(::ForgetPasswordScreenViewModel)
    factoryOf(::OtpScreenViewModel)
    factoryOf(::ProfileScreenViewModel)
}