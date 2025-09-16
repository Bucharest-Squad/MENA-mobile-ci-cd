package net.thechance.mena.dukan.presentation.viewModel.createDukan

import net.thechance.mena.dukan.domain.entity.Category
import androidx.compose.ui.graphics.ImageBitmap
import com.attafitamim.krop.core.images.ImageSrc
import net.thechance.mena.dukan.presentation.viewModel.base.BaseViewModel
import net.thechance.mena.dukan.presentation.viewModel.createDukan.CreateDukanUiState.CreateDukanStep

class CreateDukanViewModel :
    BaseViewModel<CreateDukanUiState, CreateDukanEffect>(CreateDukanUiState()),
    CreateDukanInteractionListener {

    init {
        loadMockCategories() // TODO: Remove mock data
    }

    override fun onButtonClicked() {
        if (state.value.currentStep != CreateDukanUiState.CreateDukanStep.SELECT_STYLE) {
            onCLickNext()
        } else {
            onCreateClicked()
        }
    }

    override fun onBackClicked() {
        val current = state.value.currentStep
        if (current == CreateDukanUiState.CreateDukanStep.BASIC_INFORMATION) {
            // maybe do nothing or exit flow
        } else {
            updateState {
                copy(currentStep = previousStep(current))
            }
        }
        updateNextButtonEnableState()
    }


    private fun onCreateClicked() {
        TODO("Not yet implemented")
    }

    override fun onClickUploadImage(
        image: ImageSrc
    ) {
        updateState {
            copy(
                selectedImage = image,
                isEditIconVisible = false,
                isImageBeingCropped = true
            )
        }
        updateNextButtonEnableState()
    }

    override fun onClickEditImage() {
        emitEffect(CreateDukanEffect.NavigateToImageCropScreen)
    }

    override fun onCLickNext() {
        val current = state.value.currentStep
        updateState {
            CreateDukanStep.BASIC_INFORMATION -> handleBasicInformationNext()
            copy(currentStep = nextStep(current))
        }
    }

    override fun onImageCrop(image: ImageBitmap) {
        updateState {
            copy(
                croppedImage = image,
                selectedImage = null,
                isEditIconVisible = true,
                isImageBeingCropped = false
            )
        }
        updateNextButtonEnableState()
    }

    override fun onCancelCrop() {
        updateState {
            copy(
                selectedImage = null,
                isImageBeingCropped = false
            )
        }
        updateNextButtonEnableState()
    }


    override fun onNameChanged(name: String) {
        updateState { copy(name = name, showSnackBar = false) }
        updateNextButtonEnableState()
    }

    override fun isCategorySelected(category: Category): Boolean {
        return state.value.selectedCategories.contains(category)
    }

    override fun onCategorySelected(category: Category): Boolean {
        val currentState = state.value
        if (currentState.selectedCategories.size < MAX_CATEGORIES) {
            updateState {
                copy(selectedCategories = selectedCategories + category)
            }
            updateNextButtonEnableState()
            return true
        }
        return false
    }

    override fun onCategoryDeselected(category: Category): Boolean {
        updateState {
            copy(selectedCategories = selectedCategories - category)
        }
        updateNextButtonEnableState()
        return true
    }

    override fun onCategoryEnabled(category: Category): Boolean {
        val currentState = state.value
        return currentState.selectedCategories.size < MAX_CATEGORIES ||
                currentState.selectedCategories.contains(category)
    }

    private fun onCreateClicked() {
        TODO("Not yet implemented")
    }

    private fun handleBasicInformationNext() {
        if (isBasicInformationStepValid(state.value)) {
            checkNameUniqueness(state.value.name)
            return
        }
        updateState { copy(showSnackBar = true, isNameUnique = false) }
    }

    fun onImageCroppedAndSaved(croppedUri: String) {
        updateState {
            copy(
                savedImageUri = croppedUri,
                isNextButtonEnabled = true
            )
        }
    }

    private fun nextStep(step: CreateDukanStep): CreateDukanStep =
        when (step) {
            CreateDukanStep.BASIC_INFORMATION -> CreateDukanStep.SELECT_IMAGE
            CreateDukanStep.SELECT_IMAGE -> CreateDukanStep.SELECT_LOCATION
            CreateDukanStep.SELECT_LOCATION -> CreateDukanStep.SELECT_STYLE
            CreateDukanStep.SELECT_STYLE -> step
        }

    private fun previousStep(step: CreateDukanStep): CreateDukanStep =
        when (step) {
            CreateDukanStep.BASIC_INFORMATION -> step
            CreateDukanStep.SELECT_IMAGE -> CreateDukanStep.BASIC_INFORMATION
            CreateDukanStep.SELECT_LOCATION -> CreateDukanStep.SELECT_IMAGE
            CreateDukanStep.SELECT_STYLE -> CreateDukanStep.SELECT_LOCATION
        }

    private fun checkNameUniqueness(name: String) {
        tryToExecute(
            block = {
                // TODO: Use repository when dependency injection is set up
                // For now, use mock data
                name.lowercase() == "test"
            },
            onSuccess = { isTaken -> handleNameValidationResult(isTaken) },
            onError = { handleNameValidationError() }
        )
    }

    private fun handleNameValidationResult(isTaken: Boolean) {
        val current = state.value.currentStep
        updateState {
            copy(
                isNameUnique = !isTaken,
                showSnackBar = isTaken,
                currentStep = if (isTaken) current else nextStep(current)
            )
        }
        updateNextButtonEnableState()
    }

    private fun handleNameValidationError() {
        updateState { copy(isNameUnique = false, showSnackBar = true) }
        updateNextButtonEnableState()
    }

    private fun updateNextButtonEnableState() {
        val currentState = state.value
        val isNextButtonEnabled = when (currentState.currentStep) {
            CreateDukanStep.BASIC_INFORMATION -> isBasicInformationStepValid(currentState)
            CreateDukanStep.SELECT_IMAGE -> currentState.croppedImage != null
            CreateDukanStep.SELECT_LOCATION -> true
            CreateDukanStep.SELECT_STYLE -> true
            CreateDukanStep.CROP_IMAGE -> true
        }
        updateState { this.copy(isButtonEnabled = isNextButtonEnabled) }
    }

    private fun isBasicInformationStepValid(state: CreateDukanUiState): Boolean {
        return state.name.isNotBlank() &&
                state.selectedCategories.size in MIN_CATEGORIES..MAX_CATEGORIES &&
                !state.showSnackBar
    }

    private fun loadMockCategories() {
        // TODO: Remove mock data
        val categories = listOf(
            Category("1", "Food", ""),
            Category("2", "Electronics", ""),
            Category("3", "Clothing", ""),
            Category("4", "Books", ""),
            Category("5", "Sports", "")
        )
        updateState { copy(availableCategories = categories) }
    }

    private companion object {
        private const val MIN_CATEGORIES = 1
        private const val MAX_CATEGORIES = 3
    }
}