package net.thechance.mena.wallet.domain.repository

import net.thechance.mena.wallet.domain.model.FilterRequestParams

interface ExportTransactionsRepository {
    suspend fun getFilteredTransactionsFile(
        filterRequestParams: FilterRequestParams? = null
    ): ByteArray
}