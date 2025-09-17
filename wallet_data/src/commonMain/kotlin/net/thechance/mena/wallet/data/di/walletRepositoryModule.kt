package net.thechance.mena.wallet.data.di

import net.thechance.mena.wallet.data.repository.balance.FakeBalanceRepositoryImpl
import net.thechance.mena.wallet.domain.repository.BalanceRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal val walletRepositoryModule = module {
    singleOf(::FakeBalanceRepositoryImpl) bind BalanceRepository::class
}