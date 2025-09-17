package net.thechance.mena.trends.presentation.screen.managemytrendscreen

import net.thechance.mena.trends.domain.entity.Reel

internal fun Reel.toUiState(): ReelUiState {
    return ReelUiState(
        id = id,
        thumbnailUrl = thumbnailUrl
    )
}