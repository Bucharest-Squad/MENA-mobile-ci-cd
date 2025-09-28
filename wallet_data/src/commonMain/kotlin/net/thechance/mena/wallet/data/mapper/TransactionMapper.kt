package net.thechance.mena.wallet.data.mapper

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.char
import net.thechance.mena.wallet.data.dto.TransactionDto
import net.thechance.mena.wallet.domain.entity.Transaction
import net.thechance.mena.wallet.domain.model.TransactionStatus
import net.thechance.mena.wallet.domain.model.TransactionType
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
fun TransactionDto.toEntity(): Transaction {
    return Transaction(
        id = Uuid.parse(id),
        createdAt = LocalDateTime.parse(createdAt),
        status = TransactionStatus.valueOf(status),
        senderName = senderName,
        receiverName = receiverName,
        amount = amount,
        type = TransactionType.valueOf(type),
    )

}
val customFormat = LocalDateTime.Format {
    date(LocalDate.Formats.ISO)
    char(' ')
    hour(); char(':'); minute(); char(':'); second()
    char(','); secondFraction(fixedLength = 3)
}