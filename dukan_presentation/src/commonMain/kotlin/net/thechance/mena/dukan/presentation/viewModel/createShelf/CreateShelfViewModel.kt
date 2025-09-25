package net.thechance.mena.dukan.presentation.viewModel.createShelf

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import net.thechance.mena.dukan.domain.entity.Shelf
import net.thechance.mena.dukan.domain.repository.ShelfRepository
import net.thechance.mena.dukan.presentation.viewModel.base.BaseViewModel

class CreateShelfViewModel(
    private val shelfRepository: ShelfRepository,
    defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel<CreateShelfUiState, CreateShelfEffect>(
    initialState = CreateShelfUiState(),
    defaultDispatcher = defaultDispatcher
), CreateShelfInteractionListener {

    override fun onTitleChanged(shelfTitle: String) {
        val trimmed = shelfTitle.trim()
        val valid = trimmed.isNotBlank() && validTitleRegex.matches(trimmed)
        updateState {
            copy(
                shelfTitle = trimmed,
                isCreateButtonEnabled = valid
            )
        }
    }

    override fun onBackButtonClicked() {
        emitEffect(CreateShelfEffect.NavigateBack)
    }

    override fun onCreateButtonClicked() {
        val title = state.value.shelfTitle
        if (!validTitleRegex.matches(title) || title.isBlank()) {
            showSnackBar("Shelf name is invalid")
            return
        }

        tryToExecute(
            onStart = { updateState { copy(isLoading = true) } },
            block = {
                val allShelves = shelfRepository.getMyDukanShelves()
                val nameExists = allShelves.any { it.name.equals(title, ignoreCase = true) }

                if (!nameExists) {
                    shelfRepository.createShelf(Shelf(id = "", name = title, dukanId = ""))
                    true
                } else false
            },
            onSuccess = { isCreated ->
                updateState { copy(isLoading = false) }
                if (isCreated) {
                    showSnackBar(
                        "Shelf created successfully",
                        CreateShelfUiState.SnackBarType.SUCCESS
                    )
                    emitEffect(CreateShelfEffect.NavigateBack)
                } else {
                    showSnackBar("Shelf name already exists")
                }
            },
            onError = {
                updateState { copy(isLoading = false) }
                showSnackBar("Failed to create shelf")
            }
        )
    }

    override fun onDismissSnackBar() {
        updateState {
            copy(
                showSnackBar = false,
                snackBarMessage = null,
                snackBarType = CreateShelfUiState.SnackBarType.NONE
            )
        }
    }

     fun showSnackBar(
        message: String,
        type: CreateShelfUiState.SnackBarType = CreateShelfUiState.SnackBarType.ERROR
    ) {
        updateState {
            copy(
                showSnackBar = true,
                snackBarMessage = message,
                snackBarType = type
            )
        }
    }

    companion object {
        private val validTitleRegex = Regex("^[\\p{L}\\s-]+$")
    }
}
