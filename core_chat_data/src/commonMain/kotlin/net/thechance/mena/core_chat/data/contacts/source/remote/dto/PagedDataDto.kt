package net.thechance.mena.core_chat.data.contacts.source.remote.dto

data class PagedDataDto<T>(
    val data: List<T>,
    val pageNumber: Int,
    val pageSize: Int,
    val totalItems: Int,
    val totalPages: Int
)