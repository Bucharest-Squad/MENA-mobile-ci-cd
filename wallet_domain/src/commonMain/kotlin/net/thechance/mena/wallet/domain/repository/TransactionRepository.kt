package net.thechance.mena.wallet.domain.repository

import net.thechance.mena.wallet.domain.entity.PagedTransactionResponse
import net.thechance.mena.wallet.domain.entity.Transaction
import net.thechance.mena.wallet.domain.model.TransactionFilterParams


interface TransactionRepository {
    suspend fun getTransactionHistory(transactionFilterParams:TransactionFilterParams?):List<PagedTransactionResponse>
}