package net.thechance.mena.dukan.presentation.viewModel.manageDukan

import mena.dukan_presentation.generated.resources.Res
import mena.dukan_presentation.generated.resources.delete
import mena.dukan_presentation.generated.resources.dismiss
import net.thechance.mena.dukan.domain.entity.Product
import net.thechance.mena.dukan.presentation.component.SnackBarUiState
import org.jetbrains.compose.resources.StringResource

data class ManageDukanUiState(
    val shelves: List<ShelfUiState> = emptyList(),
    val selectedShelf: ShelfUiState? = null,
    val products: List<Product> = emptyList(),
    val totalProducts: Int = 0,
    val isLoading: Boolean = false,
    val isLoadingProducts: Boolean = false,
    val snackBarState: SnackBarUiState? = null,
    val deleteShelfConfirmationDialogUiState: DeleteShelfConfirmationDialogUiState? = null,
    val showDeleteConfirmationDialog: Boolean = false,
)

data class DeleteShelfConfirmationDialogUiState(
    val title: StringResource,
    val description: StringResource,
    val type: ConfirmDialogType
)

enum class ConfirmDialogType(val text: StringResource) {
    DELETE(text = Res.string.delete),
    DISMISS(text = Res.string.dismiss)
}

data class ShelfUiState(
    val id: String,
    val name: String
)