package net.thechance.mena.dukan.data.repository.mapper

import net.thechance.mena.dukan.data.repository.dto.product.CreateProductRequest
import net.thechance.mena.dukan.domain.entity.Product

fun  Product.toCreateProductRequest(): CreateProductRequest {
    return CreateProductRequest(
        name = name,
        description = description,
        price = price,
        shelfId = shelfId
    )
}

