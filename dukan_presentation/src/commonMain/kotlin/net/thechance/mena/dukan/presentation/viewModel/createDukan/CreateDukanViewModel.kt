package net.thechance.mena.dukan.presentation.viewModel.createDukan

import net.thechance.mena.dukan.domain.entity.Category
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

    override fun onResetClicked() {
        updateState {
            copy(
                zoomFactor = MIN_ZOOM,
                isZoomOutEnabled = false
            )
        }
    }

    override fun onClickUploadImage() {
        emitEffect(CreateDukanEffect.NavigateToImageCropScreen)
    }

    override fun onClickEditImage() {
        emitEffect(CreateDukanEffect.NavigateToImageCropScreen)
    }

    override fun onCLickNext() {
        val current = state.value.currentStep
        when (current) {
            CreateDukanStep.BASIC_INFORMATION -> handleBasicInformationNext()
            else -> {
                updateState { copy(currentStep = nextStep(current)) }
                updateNextButtonEnableState()
            }
        }
    }

    override fun onSaveClicked() {
        updateState {
            copy(
                isNextButtonEnabled = true,
                isEditIconVisible = true
            )
        }
    }

    override fun onZoomInClicked() {
        val current = state.value.zoomFactor
        val newZoom = (current + ZOOM_STEP).coerceAtMost(MAX_ZOOM)
        updateState {
            copy(
                zoomFactor = newZoom,
                isZoomOutEnabled = newZoom > MIN_ZOOM
            )
        }
    }

    override fun onZoomOutClicked() {
        val current = state.value.zoomFactor
        val newZoom = (current - ZOOM_STEP).coerceAtLeast(MIN_ZOOM)
        updateState {
            copy(
                zoomFactor = newZoom,
                isZoomOutEnabled = newZoom > MIN_ZOOM
            )
        }
    }

    override fun onUploadAnotherImageClicked() {}

    override fun onNameChanged(name: String) {
        updateState { copy(name = name, showSnackBar = false) }
        updateNextButtonEnableState()
    }

    override fun isCategorySelected(): (Category) -> Boolean {
        return { category -> state.value.selectedCategories.contains(category) }
    }

    override fun onCategorySelected(category: Category): Boolean {
        if (!canSelectMoreCategories(state.value)) return false

        addCategoryToSelection(category)
        updateNextButtonEnableState()
        return true
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
        return canSelectMoreCategories(currentState) ||
                currentState.selectedCategories.contains(category)
    }

    private fun canSelectMoreCategories(currentState: CreateDukanUiState): Boolean {
        return currentState.selectedCategories.size < MAX_CATEGORIES
    }

    private fun addCategoryToSelection(category: Category) {
        updateState {
            copy(selectedCategories = selectedCategories + category)
        }
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
            CreateDukanStep.CROP_IMAGE -> CreateDukanStep.CROP_IMAGE
        }

    private fun previousStep(step: CreateDukanStep): CreateDukanStep =
        when (step) {
            CreateDukanStep.BASIC_INFORMATION -> step
            CreateDukanStep.SELECT_IMAGE -> CreateDukanStep.BASIC_INFORMATION
            CreateDukanStep.SELECT_LOCATION -> CreateDukanStep.SELECT_IMAGE
            CreateDukanStep.SELECT_STYLE -> CreateDukanStep.SELECT_LOCATION
            CreateDukanStep.CROP_IMAGE -> CreateDukanStep.CROP_IMAGE
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
            CreateDukanStep.SELECT_IMAGE -> currentState.savedImageUri != null
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
        private const val MIN_ZOOM = 1f
        private const val MAX_ZOOM = 4f
        private const val ZOOM_STEP = 0.25f
        private const val MIN_CATEGORIES = 1
        private const val MAX_CATEGORIES = 3
    }
}