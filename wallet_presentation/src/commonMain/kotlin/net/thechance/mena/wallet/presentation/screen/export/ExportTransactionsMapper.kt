package net.thechance.mena.wallet.presentation.screen.export

import net.thechance.mena.wallet.domain.entity.Transaction
import net.thechance.mena.wallet.presentation.model.FilterStatus
import net.thechance.mena.wallet.presentation.model.FilterType

fun FilterType.toDomain(): Transaction.Type {
    return when (this) {
        FilterType.SENT -> Transaction.Type.SENT
        FilterType.RECEIVED -> Transaction.Type.RECEIVED
        FilterType.ONLINE_PURCHASE -> Transaction.Type.ONLINE_PURCHASE
    }
}

fun FilterStatus.toDomain(): Transaction.Status? {
    return when (this) {
        FilterStatus.ALL -> null
        FilterStatus.FAILED -> Transaction.Status.FAIL
        FilterStatus.SUCCESS -> Transaction.Status.SUCCESS
    }
}