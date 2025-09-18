package net.thechance.mena.trends.presentation.shared.model

data class CategoryUiState(
    val id: String = "",
    val name: String = "",
    val emoji: String = "",
)

fun List<Selectable<CategoryUiState>>.toggleCategory(id: String) =
    map { if (it.value.id == id) it.copy(isSelected = !it.isSelected) else it }