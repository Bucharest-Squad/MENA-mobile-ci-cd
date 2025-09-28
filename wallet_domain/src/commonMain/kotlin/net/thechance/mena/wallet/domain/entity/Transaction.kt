package net.thechance.mena.wallet.domain.entity

import kotlinx.datetime.LocalDateTime
import net.thechance.mena.wallet.domain.model.TransactionStatus
import net.thechance.mena.wallet.domain.model.TransactionType
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
data class Transaction (
    val id: Uuid,
    val createdAt: LocalDateTime,
    val amount: Double,
    val status: TransactionStatus,
    val senderId: Uuid,
    val senderName: String,
    val receiverId: Uuid,
    val receiverName: String,
    val type: TransactionType
)