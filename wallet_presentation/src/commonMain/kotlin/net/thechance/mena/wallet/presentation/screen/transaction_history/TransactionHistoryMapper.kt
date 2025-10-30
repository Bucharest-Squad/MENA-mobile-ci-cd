package net.thechance.mena.wallet.presentation.screen.transaction_history

import kotlinx.datetime.LocalDate
import net.thechance.mena.wallet.domain.entity.Transaction
import net.thechance.mena.wallet.domain.entity.TransactionStatus
import net.thechance.mena.wallet.domain.entity.TransactionType
import net.thechance.mena.wallet.domain.model.TransactionFilterParams
import net.thechance.mena.wallet.presentation.model.FilterStatus
import net.thechance.mena.wallet.presentation.model.FilterType
import net.thechance.mena.wallet.presentation.utils.formatLocalDateTime
import kotlin.uuid.ExperimentalUuidApi

@OptIn(ExperimentalUuidApi::class)
fun Transaction.toUi(): TransactionHistoryScreenState.TransactionHistoryUiState =
    TransactionHistoryScreenState.TransactionHistoryUiState(
        id = id,
        timeAndDate = formatLocalDateTime(date = createdAt, outputFormat = "dd MMM yyyy, h:mm a"),
        amount = amount.formatWithCommas(),
        type = getTransactionType(),
        status = getTransactionStatus(),
        contactName = getUserName()
    )

fun Double.formatWithCommas(): String {
    val roundedAmount = (this * 100).toLong() / 100.0
    val intPart = roundedAmount.toLong()
    val decimalPart = ((roundedAmount - intPart) * 100).toInt()

    return if (this >= 1000) {
        val intString = intPart.toString()
        val withCommas = buildString {
            intString.reversed().forEachIndexed { index, char ->
                if (index > 0 && index % 3 == 0) append(',')
                append(char)
            }
        }.reversed()

        "$withCommas.${decimalPart.toString().padStart(2, '0')}"
    } else {
        "$intPart.${decimalPart.toString().padStart(2, '0')}"
    }
}

fun TransactionFilterState.toParams(): TransactionFilterParams {
    return TransactionFilterParams(
        types = selectedTypes.map { it.toDomain() }.takeIf { it.isNotEmpty() },
        status = selectedStatus.toDomain(),
        startDate = startDate,
        endDate = endDate
    )
}

fun FilterType.toDomain(): TransactionType = when (this) {
    FilterType.SENT -> TransactionType.SENT
    FilterType.RECEIVED -> TransactionType.RECEIVED
    FilterType.ONLINE_PURCHASE -> TransactionType.ONLINE_PURCHASE
}
fun FilterStatus.toDomain(): TransactionStatus? = when (this) {
    FilterStatus.SUCCESS -> TransactionStatus.SUCCESS
    FilterStatus.FAILED -> TransactionStatus.FAILED
    FilterStatus.ALL -> null
}

private fun Transaction.getTransactionType(): TransactionHistoryScreenState.TransactionTypeUiState =
    when (type) {
        TransactionType.SENT -> TransactionHistoryScreenState.TransactionTypeUiState.SENT
        TransactionType.RECEIVED -> TransactionHistoryScreenState.TransactionTypeUiState.RECEIVED
        TransactionType.ONLINE_PURCHASE -> TransactionHistoryScreenState.TransactionTypeUiState.ONLINE_SHOPPING
    }

private fun Transaction.getTransactionStatus(): TransactionHistoryScreenState.TransactionStatusUiState =
    when (status) {
        TransactionStatus.SUCCESS -> TransactionHistoryScreenState.TransactionStatusUiState.SUCCESS
        TransactionStatus.FAILED -> TransactionHistoryScreenState.TransactionStatusUiState.FAILED
    }

private fun Transaction.getUserName(): String? = when (type) {
    TransactionType.SENT -> receiverName
    TransactionType.RECEIVED -> senderName
    else -> null
}
