package net.thechance.mena.core_chat.data.shared.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PagedDataDto<T>(
    @SerialName("data")
    val data: List<T>?,
    @SerialName("page_number")
    val pageNumber: Int?,
    @SerialName("page_size")
    val pageSize: Int?,
    @SerialName("total_items")
    val totalItems: Int?,
    @SerialName("total_pages")
    val totalPages: Int?
)