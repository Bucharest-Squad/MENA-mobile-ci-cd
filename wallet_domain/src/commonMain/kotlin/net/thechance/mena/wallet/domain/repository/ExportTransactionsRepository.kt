package net.thechance.mena.wallet.domain.repository

interface ExportTransactionsRepository {
    suspend fun getFilteredTransactions(): ByteArray
    suspend fun getAllTransactions(): ByteArray
}