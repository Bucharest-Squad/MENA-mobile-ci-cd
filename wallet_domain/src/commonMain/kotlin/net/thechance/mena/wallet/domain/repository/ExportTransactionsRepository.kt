package net.thechance.mena.wallet.domain.repository

import kotlinx.datetime.LocalDateTime
import net.thechance.mena.wallet.domain.entity.Transaction

interface ExportTransactionsRepository {
    suspend fun getFilteredTransactionsFile(
        type: Set<Transaction.Type>?,
        status: Transaction.Status?,
        startDate: LocalDateTime?,
        endDate: LocalDateTime?
    ): ByteArray
    suspend fun getAllTransactionsFile(): ByteArray
}