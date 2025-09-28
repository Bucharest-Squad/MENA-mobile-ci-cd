package net.thechance.mena.wallet.domain.repository

import net.thechance.mena.wallet.domain.entity.Transaction
import net.thechance.mena.wallet.domain.model.TransactionFilter


interface TransactionRepository {
    suspend fun getTransactionHistory(transactionFilter:TransactionFilter?):List<Transaction>?
}