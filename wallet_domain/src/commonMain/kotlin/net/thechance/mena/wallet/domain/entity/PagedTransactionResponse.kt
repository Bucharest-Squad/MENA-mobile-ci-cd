package net.thechance.mena.wallet.domain.entity

data class PagedTransactionResponse(
    val totalElements: Long,
    val page: Long,
    val pageSize: Long,
    val totalPages: Long,
    val transactions: List<Transaction>

)