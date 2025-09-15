package net.thechance.mena.trends.presentation.screen.interestpick

import net.thechance.mena.trends.domain.entity.Category
import net.thechance.mena.trends.domain.repository.CategoryRepository
import net.thechance.mena.trends.presentation.shared.base.BaseViewModel

class CategoryPickViewModel(
    private val repository: CategoryRepository
) : BaseViewModel<CategoryPickScreenUiState, CategoryPickUiEffect>(
    initialState = CategoryPickScreenUiState()
), CategoryPickInteractionListener {

    init {
        loadCategories()
    }

    private fun loadCategories() {
        tryToExecute(
            block = { repository.getAllCategories() },
            onSuccess = ::handleLoadCategoriesSuccess,
            onError = TODO(),
            onStart = ::startLoading,
            onEnd = ::endLoading
        )
    }

    private fun handleLoadCategoriesSuccess(categories: List<Category>) {
        val uiModels = categories.toUiStates()
        updateState { copy(categories = uiModels) }
    }

    override fun onCategoryClick(categoryId: Int) {
        updateState {
            val updated = categories.map {
                if (it.uiState.id == categoryId) {
                    it.copy(isSelected = !it.isSelected)
                } else {
                    it
                }
            }
            copy(categories = updated)
        }
    }

    override fun onSaveClick() {
        tryToExecute(
            block = { saveSelectedCategories() },
            onSuccess = { sendEffect(CategoryPickUiEffect.NavigateToSave) },
            onStart = ::startSaving,
            onEnd = ::endSaving,
            onError = TODO(),
        )
    }

    private suspend fun saveSelectedCategories() {
        val selectedIds = state.value.categories
            .filter { it.isSelected }
            .mapNotNull { it.uiState.id }
        repository.updateUserInterestedCategories(selectedIds)
    }

    override fun onBackClick() {
        sendEffect(CategoryPickUiEffect.NavigateBack)
    }

    private fun startLoading() = updateState { copy(isLoading = true) }
    private fun endLoading() = updateState { copy(isLoading = false) }

    private fun startSaving() = updateState { copy(isSaveButtonLoading = true) }
    private fun endSaving() = updateState { copy(isSaveButtonLoading = false) }
}
