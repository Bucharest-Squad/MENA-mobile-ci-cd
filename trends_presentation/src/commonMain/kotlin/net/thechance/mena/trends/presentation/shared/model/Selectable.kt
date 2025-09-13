package net.thechance.mena.trends.presentation.shared.model

data class Selectable<T>(
    val value: T,
    val isSelected: Boolean = false,
)