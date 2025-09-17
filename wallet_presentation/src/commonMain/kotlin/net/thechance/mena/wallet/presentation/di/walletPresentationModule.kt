package net.thechance.mena.wallet.presentation.di

import org.koin.dsl.module

val walletPresentationModule = module {
    includes(walletViewModelModule)
}