package net.thechance.mena.wallet.data.repository

import net.thechance.mena.wallet.data.exceptions.safeApiCall
import net.thechance.mena.wallet.data.extension.NetworkClient
import net.thechance.mena.wallet.domain.repository.ExportTransactionsRepository

class ExportTransactionsRepositoryImpl(
    private val networkClient: NetworkClient
) : ExportTransactionsRepository {
    override suspend fun getFilteredTransactions(): ByteArray {
        return safeApiCall {
            networkClient.get(TRANSACTIONS_PATH)
        }
    }

    override suspend fun getAllTransactions(): ByteArray {
        return safeApiCall {
            networkClient.get(TRANSACTIONS_PATH)
        }
    }

    private companion object {
        const val TRANSACTIONS_PATH = ""
    }
}