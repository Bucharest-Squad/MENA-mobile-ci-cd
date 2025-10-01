package net.thechance.mena.dukan.presentation.viewModel.createProduct

import net.thechance.mena.dukan.domain.entity.Product
import net.thechance.mena.dukan.domain.entity.Shelf
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

fun Shelf.toUiState(): ShelfUiState {
    return ShelfUiState(
        id = id,
        name = name,
    )
}

@OptIn(ExperimentalTime::class)
fun ProductUiState.toDomain(): Product {
    return Product(
        id = "",
        name = productName,
        description = description,
        price = price.toDouble(),
        createdAt = "${Clock.System.now().toEpochMilliseconds()}",
        imageUrls = listOf()
    )
}