package net.thechance.mena.wallet.domain.repository

import kotlinx.datetime.LocalDateTime
import net.thechance.mena.wallet.domain.entity.Transaction
import net.thechance.mena.wallet.domain.model.FilterRequestParams

interface ExportTransactionsRepository {
    suspend fun getFilteredTransactionsFile(
        filterRequestParams: FilterRequestParams? = null
    ): ByteArray

}