package net.thechance.mena.dukan.data.repository.dto.product

import kotlinx.serialization.Serializable

@Serializable
data class CreateProductResponse(
    val productId: String
)
