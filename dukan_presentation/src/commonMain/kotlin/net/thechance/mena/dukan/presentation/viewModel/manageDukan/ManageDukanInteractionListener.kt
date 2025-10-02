package net.thechance.mena.dukan.presentation.viewModel.manageDukan

import net.thechance.mena.dukan.domain.entity.Product
import net.thechance.mena.dukan.presentation.component.SnackBarType
import org.jetbrains.compose.resources.StringResource

interface ManageDukanInteractionListener {
    fun onBackButtonClicked()
    fun onDismissSnackBar()
    fun onShowSnackBar(message: StringResource, type: SnackBarType)
    fun onDismissDeleteShelfConfirmationDialog()
    fun onShowDeleteShelfDailog(shelfId: String)
    fun onDeleteConfirmed(shelfId: String)
    fun onAddProductClicked()
    fun onProductClick(product: Product)
    fun onEditShelfClicked()
    fun onAddShelfClicked()
    fun onShelfAddedSuccessfully()
    fun isShelfSelected(shelf: ShelfUiState): Boolean
    fun onShelfSelected(shelf: ShelfUiState)
}
