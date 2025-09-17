package net.thechance.mena.wallet.data.di

import org.koin.dsl.module

val walletDataModule = module {
    includes(walletRepositoryModule)
}