package net.thechance.mena.wallet.data.repository.balance

import net.thechance.mena.wallet.data.dto.PagedTransactionResponseDto
import net.thechance.mena.wallet.data.exceptions.safeApiCall
import net.thechance.mena.wallet.data.mapper.toEntity
import net.thechance.mena.wallet.data.network_client.NetworkClient
import net.thechance.mena.wallet.domain.entity.Transaction
import net.thechance.mena.wallet.domain.model.TransactionFilterParams
import net.thechance.mena.wallet.domain.repository.TransactionRepository
import io.ktor.http.Parameters

class TransactionRepositoryImpl(
    private val networkClient: NetworkClient
) : TransactionRepository {
    override suspend fun getTransactionHistory(transactionFilterParams: TransactionFilterParams?): List<Transaction> {
        return safeApiCall<PagedTransactionResponseDto> {
            networkClient.get("$TRANSACTION_PATH?${transactionFilterParams?.toParameters()}")
        }.transactions.map { it.toEntity() }

    }

    private companion object {
        const val TRANSACTION_PATH = "wallet/transactions"
    }

    private fun TransactionFilterParams.toParameters(): Parameters =
        Parameters.build {
            types?.forEach {
                append("type", it.name)
            }
            status?.let { append("status", it.name) }
            startDate?.let { append("startDate", it.toString()) }
            endDate?.let { append("endDate", it.toString()) }
        }
}