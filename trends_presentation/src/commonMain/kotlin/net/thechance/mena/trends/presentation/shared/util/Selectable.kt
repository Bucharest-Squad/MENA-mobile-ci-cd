package net.thechance.mena.trends.presentation.shared.util

data class Selectable<T>(
    val uiState: T,
    val isSelected: Boolean
)
