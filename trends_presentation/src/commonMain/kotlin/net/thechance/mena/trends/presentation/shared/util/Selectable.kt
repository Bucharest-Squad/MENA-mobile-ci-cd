package net.thechance.mena.trends.presentation.shared.util

data class Selectable<T>(
    val value: T,
    val isSelected: Boolean =false
)
