package net.thechance.mena.dukan.presentation.viewModel.manageDukan

import net.thechance.mena.dukan.domain.entity.Shelf

fun Shelf.toUiState(): ShelfUiState {
    return ShelfUiState(
        id = id,
        name = name
    )
}