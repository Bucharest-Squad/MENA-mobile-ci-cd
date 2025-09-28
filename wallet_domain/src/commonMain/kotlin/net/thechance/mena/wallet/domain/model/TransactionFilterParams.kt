package net.thechance.mena.wallet.domain.model

import kotlinx.datetime.LocalDate

data class TransactionFilterParams(
    val type: TransactionType?,
    val status: TransactionStatus,
    val startDate: LocalDate?,
    val endDate :LocalDate?,
)
