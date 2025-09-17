package net.thechance.mena.wallet.domain.di

import org.koin.dsl.module

val walletDomainModule = module {
    includes(walletUseCaseModule)
}