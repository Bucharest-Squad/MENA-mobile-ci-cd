package net.thechance.mena.dukan.presentation.viewModel.createProduct

import net.thechance.mena.dukan.domain.entity.Product
import net.thechance.mena.dukan.domain.entity.Shelf

fun Shelf.toUiState(): ShelfUiState {
    return ShelfUiState(
        id = id,
        name = name,
    )
}

fun ProductUiState.toDomain(): Product {
    return Product(
        id = "",
        name = productName.trim(),
        description = description,
        price = price.toDouble(),
        shelfId = selectedShelf!!.name,
        dukanId = "",
        imageUrls = listOf()
    )
}