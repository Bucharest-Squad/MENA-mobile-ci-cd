package net.thechance.mena.wallet.data.repository.balance

import net.thechance.mena.wallet.data.dto.TransactionDto
import net.thechance.mena.wallet.data.exceptions.safeApiCall
import net.thechance.mena.wallet.data.network_client.NetworkClient
import net.thechance.mena.wallet.domain.entity.Transaction
import net.thechance.mena.wallet.domain.model.TransactionFilter
import net.thechance.mena.wallet.domain.repository.TransactionRepository

class TransactionRepositoryImpl(
    private val networkClient: NetworkClient
) : TransactionRepository {
    override suspend fun getTransactionHistory(transactionFilter: TransactionFilter?):List<Transaction>?{
        return safeApiCall<List<Transaction>?> {
              networkClient.get(Transaction_PATH)
        }
    }
    private companion object {
        const val Transaction_PATH = "wallet/transaction"
    }
}