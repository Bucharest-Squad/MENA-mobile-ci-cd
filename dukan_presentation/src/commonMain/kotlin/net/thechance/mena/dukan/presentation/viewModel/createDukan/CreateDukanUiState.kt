package net.thechance.mena.dukan.presentation.viewModel.createDukan

import net.thechance.mena.dukan.domain.entity.Category

data class CreateDukanUiState(
    val name: String = "",
    val currentStep: CreateDukanStep = CreateDukanStep.BASIC_INFORMATION,
    val isButtonEnabled: Boolean = true, // TODO: Change this to be default be false
    val isButtonLoading: Boolean = false,
    val availableCategories: List<Category> = emptyList(),
    val selectedCategories: Set<Category> = emptySet(),
    val isNameUnique: Boolean = true,
    val showSnackBar: Boolean = false,
    val savedImageUri: String? = null,
    val isNextButtonEnabled: Boolean = false,
    val zoomFactor: Float = 1f,
    val isZoomOutEnabled: Boolean = false,
    val isEditIconVisible: Boolean = false,
    val isImageBeingCropped: Boolean = false,
) {
    enum class CreateDukanStep {
        BASIC_INFORMATION,
        SELECT_IMAGE,
        CROP_IMAGE,
        SELECT_LOCATION,
        SELECT_STYLE;

        companion object {
            val steps = entries
        }
    }
}