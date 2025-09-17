package net.thechance.mena.trends.presentation.shared.model

data class CategoryUiState(
    val id: Int? = null,
    val name: String = "",
    val emoji: String = "",
)

fun List<Selectable<CategoryUiState>>.toggleCategory(id: Int) =
    map { if (it.value.id == id) it.copy(isSelected = !it.isSelected) else it }