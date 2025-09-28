package net.thechance.mena.wallet.domain.repository

import kotlinx.datetime.LocalDateTime
import net.thechance.mena.wallet.domain.entity.Transaction

interface ExportTransactionsRepository {
    suspend fun getFilteredTransactionsFile(
        type: Set<Transaction.Type>? = null,
        status: Transaction.Status? = null,
        startDate: LocalDateTime? = null,
        endDate: LocalDateTime? = null
    ): ByteArray

}