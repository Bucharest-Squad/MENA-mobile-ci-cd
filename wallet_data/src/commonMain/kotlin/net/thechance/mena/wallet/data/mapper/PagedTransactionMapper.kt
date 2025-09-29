package net.thechance.mena.wallet.data.mapper

import net.thechance.mena.wallet.data.dto.PagedTransactionResponseDto
import net.thechance.mena.wallet.domain.entity.PagedTransactionResponse
import kotlin.uuid.ExperimentalUuidApi


@OptIn(ExperimentalUuidApi::class)
fun PagedTransactionResponseDto.toEntity(): PagedTransactionResponse{
    return PagedTransactionResponse(
        totalElements = totalElements,
        page = page,
        pageSize = pageSize,
        totalPages = totalPages,
        transactions = transactions.map { it.toEntity() }
    )

}