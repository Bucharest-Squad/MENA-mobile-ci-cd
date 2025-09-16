package net.thechance.mena.dukan.presentation.viewModel.createDukan

import androidx.compose.ui.graphics.ImageBitmap
import com.attafitamim.krop.core.images.ImageSrc
import net.thechance.mena.dukan.domain.entity.Category

data class CreateDukanUiState(
    val name: String = "",
    val currentStep: CreateDukanStep = CreateDukanStep.BASIC_INFORMATION,
    val isButtonEnabled: Boolean = false,
    val isButtonLoading: Boolean = false,
    val croppedImage: ImageBitmap? = null,
    val availableCategories: List<Category> = emptyList(),
    val selectedCategories: Set<Category> = emptySet(),
    val isSelected: Boolean = false,
    val isEnabled: Boolean = true,
    val isNameUnique: Boolean = true,
    val showSnackBar: Boolean = false,
    val isNextButtonEnabled: Boolean = false,
    val isEditIconVisible: Boolean = false,
    val selectedImage: ImageSrc? = null,
    val isNextButtonEnabled: Boolean = false,
    val isImageBeingCropped: Boolean = false,
) {
    enum class CreateDukanStep {
        BASIC_INFORMATION,
        SELECT_IMAGE,
        SELECT_LOCATION,
        SELECT_STYLE;

        companion object {
            val steps = entries
        }
    }
}