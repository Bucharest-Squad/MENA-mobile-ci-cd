package net.thechance.mena.wallet.domain.model

import kotlinx.datetime.LocalDateTime
import net.thechance.mena.wallet.domain.entity.Transaction

data class FilterRequestParams(
    val type: Set<Transaction.Type>? = null,
    val status: Transaction.Status? = null,
    val startDate: LocalDateTime? = null,
    val endDate: LocalDateTime? = null
)
