package net.thechance.mena.wallet.data.repository.balance

import net.thechance.mena.wallet.data.utils.ApiClient
import net.thechance.mena.wallet.domain.repository.BalanceRepository

class FakeBalanceRepositoryImpl(
    private val apiClient: ApiClient
) : BalanceRepository {

    override suspend fun getBalance(userId: Int): Double {
        return 530320.55 //TODO: add api call
    }
}