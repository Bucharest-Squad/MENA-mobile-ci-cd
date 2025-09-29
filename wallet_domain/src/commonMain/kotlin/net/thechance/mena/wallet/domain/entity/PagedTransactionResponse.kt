package net.thechance.mena.wallet.domain.entity

data class PagedTransactionResponse(
    val totalElements: Int,
    val page: Int,
    val pageSize: Int,
    val totalPages: Int,
    val transactions: List<Transaction>

)