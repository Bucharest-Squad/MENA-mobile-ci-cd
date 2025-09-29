package net.thechance.mena.dukan.data.repository.dto.product

import kotlinx.serialization.Serializable


@Serializable
data class ProductDto(
    val id: String,
    val name: String,
    val shelfId: String,
    val price: Double,
    val description: String,
    val imageUrls: List<String>,
    val createdAt: String
)