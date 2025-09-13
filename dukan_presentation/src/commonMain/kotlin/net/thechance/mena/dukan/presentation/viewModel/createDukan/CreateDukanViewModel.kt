package net.thechance.mena.dukan.presentation.viewModel.createDukan

import net.thechance.mena.dukan.domain.entity.Category
import net.thechance.mena.dukan.presentation.viewModel.base.BaseViewModel

class CreateDukanViewModel :
    BaseViewModel<CreateDukanUiState, CreateDukanEffect>(CreateDukanUiState()),
    CreateDukanInteractionListener {

    override fun onButtonClicked() {
        if (state.value.currentStep != SELECT_STYLE_INDEX) {
            if (state.value.currentStep == BASIC_INFORMATION_INDEX) {
                if (checkNameUniqueness(state.value.name)) return
            }
            onNextClicked()
        } else {
            onCreateClicked()
        }
    }

    override fun onBackClicked() {
        if (state.value.currentStep == BASIC_INFORMATION_INDEX) {

        } else {
            updateState { copy(currentStep = currentStep - 1) }
        }
        updateNextButtonEnableState()
    }

    private fun onCreateClicked() {
        TODO("Not yet implemented")
    }

    override fun onNextClicked() {
        updateState {
            copy(currentStep = currentStep + 1)
        }
        updateNextButtonEnableState()
    }

    override fun onNameChanged(name: String) {
        updateState { copy(name = name, showSnackBar = false) }
        updateNextButtonEnableState()
    }

    private fun checkNameUniqueness(name: String): Boolean {
        // TODO: Implement real uniqueness check when DukanRepository is available
        if (name.lowercase() == "test") {
            updateState {
                copy(
                    isNameUnique = false,
                    showSnackBar = true
                )
            }
            updateNextButtonEnableState()
            return true
        } else {
            updateState {
                copy(
                    isNameUnique = true,
                    showSnackBar = false
                )
            }
            updateNextButtonEnableState()
            return false
        }
    }

    override fun onCategorySelected(category: Category) {
        if (state.value.selectedCategories.size < 3) {
            updateState { copy(selectedCategories = state.value.selectedCategories + category) }
        }
        updateNextButtonEnableState()
    }

    override fun onCategoryDeselected(category: Category) {
        updateState { copy(selectedCategories = state.value.selectedCategories - category) }
        updateNextButtonEnableState()
    }

    init {
        loadMockCategories()
    }

    private fun loadMockCategories() {
        val categories = listOf(
            Category("1", "Food", ""),
            Category("2", "Electronics", ""),
            Category("3", "Clothing", ""),
            Category("4", "Books", ""),
            Category("5", "Sports", "")
        )
        updateState { copy(availableCategories = categories) }
    }

    private fun updateNextButtonEnableState() {
        val state = state.value
        val isNextButtonEnabled = when (state.currentStep) {
            BASIC_INFORMATION_INDEX -> state.name.isNotBlank() &&
                    state.selectedCategories.size in 1..3 &&
                    !state.showSnackBar

            SELECT_IMAGE_INDEX -> true
            SELECT_LOCATION_INDEX -> true
            SELECT_STYLE_INDEX -> true
            else -> true
        }
        updateState { this.copy(isButtonEnabled = isNextButtonEnabled) }
    }

    companion object {
        const val MAX_STEPS = 4
        const val BASIC_INFORMATION_INDEX = 0
        const val SELECT_IMAGE_INDEX = 1
        const val SELECT_LOCATION_INDEX = 2
        const val SELECT_STYLE_INDEX = 3
    }
}
