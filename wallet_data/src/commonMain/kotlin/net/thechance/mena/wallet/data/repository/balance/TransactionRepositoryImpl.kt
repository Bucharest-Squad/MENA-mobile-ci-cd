package net.thechance.mena.wallet.data.repository.balance

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
              networkClient.get("$TRANSACTION_PATH?${transactionFilter?.toQueryParams()}")
        }
    }
    private companion object {
        const val TRANSACTION_PATH= "wallet/transaction"
    }
    private fun TransactionFilter.toQueryParams():String{
        val params = mutableListOf<String>()
        type?.let{
            params.add("type=$it")
        }
        status?.let{
            params.add("status=$it")
        }
        startDate?.let{
            params.add("startDate=$it")
        }
        endDate?.let{
            params.add("endDate=$it")
        }
        return params.joinToString ("&" )
    }
}