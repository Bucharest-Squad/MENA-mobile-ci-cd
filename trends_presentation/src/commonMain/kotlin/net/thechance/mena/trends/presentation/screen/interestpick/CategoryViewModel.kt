package net.thechance.mena.trends.presentation.screen.interestpick

import net.thechance.mena.trends.domain.entity.Category
import net.thechance.mena.trends.domain.repository.CategoryRepository
import net.thechance.mena.trends.presentation.shared.base.BaseViewModel
import net.thechance.mena.trends.presentation.shared.util.toggleCategory

class CategoryViewModel(
    private val repository: CategoryRepository
) : BaseViewModel<CategoryScreenUiState, CategoryUiEffect>(
    initialState = CategoryScreenUiState()
), CategoryInteractionListener {

    init {
        loadCategories()
    }

    private fun loadCategories() {
        tryToExecute(
            block = { repository.getAllCategories() },
            onSuccess = ::handleLoadCategoriesSuccess,
            onError = {},
            onStart = ::startLoading,
            onEnd = ::endLoading
        )
    }

    private fun handleLoadCategoriesSuccess(categories: List<Category>) {
        updateState { copy(categories = categories.toUiStates()) }
    }

    override fun onCategoryClick(categoryId: Int) = updateState {
        copy(categories = categories.toggleCategory(categoryId))
    }

    override fun onSaveClick() {
        tryToExecute(
            block = { saveSelectedCategories() },
            onSuccess = { sendEffect(CategoryUiEffect.NavigateToTrends) },
            onStart = ::startSaving,
            onEnd = ::endSaving,
            onError = {},
        )
    }

    private suspend fun saveSelectedCategories() {
        val selectedIds = state.value.categories
            .filter { it.isSelected }
            .mapNotNull { it.value.id }
        repository.updateUserInterestedCategories(selectedIds)
    }

    override fun onBackClick() {
        sendEffect(CategoryUiEffect.NavigateBack)
    }

    private fun startLoading() = updateState { copy(isLoading = true) }
    private fun endLoading() = updateState { copy(isLoading = false) }

    private fun startSaving() = updateState { copy(isSaveButtonLoading = true) }
    private fun endSaving() = updateState { copy(isSaveButtonLoading = false) }
}
