package net.thechance.mena.wallet.data.repository.balance

import net.thechance.mena.wallet.data.utils.NetworkClient
import net.thechance.mena.wallet.domain.repository.BalanceRepository
import org.koin.core.annotation.Single

@Single
class FakeBalanceRepositoryImpl(
    private val networkClient: NetworkClient
) : BalanceRepository {

    override suspend fun getBalance(userId: Int): Double {
        return 530320.55 //TODO: add api call
    }
}