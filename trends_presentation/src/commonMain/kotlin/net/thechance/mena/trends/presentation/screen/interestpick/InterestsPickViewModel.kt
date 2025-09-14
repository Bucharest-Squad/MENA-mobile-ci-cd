package net.thechance.mena.trends.presentation.screen.interestpick

import net.thechance.mena.trends.domain.entity.Category
import net.thechance.mena.trends.domain.repository.CategoryRepository
import net.thechance.mena.trends.presentation.shared.base.BaseViewModel

class InterestsPickViewModel(
    private val repository: CategoryRepository
) : BaseViewModel<InterestsUiState, InterestsPickUiEffect>(
    initialState = InterestsUiState(isLoading = true)
), InterestsPickInteractionListener {

    init {
        loadCategories()
    }

    private fun loadCategories() {
        tryToExecute(
            block = { repository.getAllCategories() },
            onSuccess = ::handleLoadCategoriesSuccess,
            onError = ::handleError,
            onStart = { updateState { copy(isLoading = true) } },
            onEnd = { updateState { copy(isLoading = false) } }
        )
    }

    private fun handleLoadCategoriesSuccess(categories: List<Category>) {
        val uiModels = categories.map { it.toUiModel() }
        updateState { copy(isLoading = false, categories = uiModels) }
    }

    override fun onCategoryClick(categoryId: Int) {
        updateState {
            val updated = categories.map {
                if (it.id == categoryId) it.copy(isSelected = !it.isSelected) else it
            }
            copy(categories = updated)
        }
    }

    override fun onSaveClick() {
        tryToExecute(
            block = { saveSelectedCategories() },
            onSuccess = { sendEffect(InterestsPickUiEffect.NavigateToSave) },
            onError = ::handleError,
            onStart = { updateState { copy(isSaving = true) } },
            onEnd = { updateState { copy(isSaving = false) } }
        )
    }


    private suspend fun saveSelectedCategories() {
        val selectedIds = state.value.categories
            .filter { it.isSelected }
            .map { it.id }
        repository.updateUserInterestedCategories(selectedIds)
    }

    override fun onBackClick() {
        sendEffect(InterestsPickUiEffect.NavigateBack)
    }

    private fun handleError(throwable: Throwable) {
        val errorRes = when (throwable) {
            //TODO() WILL HANDLE EXCEPTIONS
////            is IOException -> StringResource(R.string.error_network)
////            else -> StringResource(R.string.error_generic)
            else -> {}
       }
////        updateState { copy(errorMessage = errorRes) }
////        sendEffect(InterestsUiEffect.ShowError(errorRes))
    }
}