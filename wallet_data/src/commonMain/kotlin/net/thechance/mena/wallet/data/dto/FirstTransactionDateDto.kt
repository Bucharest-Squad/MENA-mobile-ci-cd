package net.thechance.mena.wallet.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FirstTransactionDateDto(
    @SerialName("firstTransactionDate")
    val firstTransactionDate: String? = null
)
