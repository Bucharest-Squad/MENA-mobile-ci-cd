package net.thechance.mena.dukan.presentation.viewModel.createDukan

import net.thechance.mena.dukan.domain.entity.Category

data class CreateDukanUiState(
    val name: String = "",
    val currentStep: Int = 0,
    val isButtonEnabled: Boolean = false,
    val isButtonLoading: Boolean = false,
    val availableCategories: List<Category> = emptyList(),
    val selectedCategories: Set<Category> = emptySet(),
    val isNameUnique: Boolean = true,
    val showSnackBar: Boolean = false,
)
