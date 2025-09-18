package net.thechance.mena.wallet.data.repository.balance

import net.thechance.mena.wallet.data.exceptions.safeApiCall
import net.thechance.mena.wallet.data.repository.balance.dto.BalanceDto
import net.thechance.mena.wallet.data.utils.NetworkClient
import net.thechance.mena.wallet.domain.repository.BalanceRepository
import org.koin.core.annotation.Single

@Single
class BalanceRepositoryImpl(
    private val networkClient: NetworkClient
) : BalanceRepository {

    override suspend fun getBalance(): Double {
        return safeApiCall<BalanceDto> {
            networkClient.get("${WALLET_PATH}/balance")
        }.balance
    }
    private companion object {
        const val WALLET_PATH = "wallet"
    }
}