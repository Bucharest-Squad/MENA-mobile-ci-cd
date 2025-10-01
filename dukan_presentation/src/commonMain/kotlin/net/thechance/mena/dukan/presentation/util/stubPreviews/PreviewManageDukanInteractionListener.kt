package net.thechance.mena.dukan.presentation.util.stubPreviews

import net.thechance.mena.dukan.domain.entity.Product
import net.thechance.mena.dukan.presentation.viewModel.manageDukan.ManageDukanInteractionListener
import net.thechance.mena.dukan.presentation.viewModel.manageDukan.ShelfUiState

object PreviewManageDukanInteractionListener : ManageDukanInteractionListener {
    override fun onBackButtonClicked() {}
    override fun onDismissSnackBar() {}
    override fun onDismissDeleteShelfConfirmationDialog(){}
    override fun onShowDeleteShelfConfirmationDialog() {}
    override fun deleteShelf(shelfId: String) {}
    override fun onAddProductClicked() {}
    override fun onEditShelfClicked() {}
    override fun onAddShelfClicked() {}
    override fun onProductClick(product: Product) {}
    override fun onShelfAddedSuccessfully() {}
    override fun isShelfSelected(): (ShelfUiState) -> Boolean = { false }
    override fun onShelfSelected(shelf: ShelfUiState): Boolean = true
    override fun onShelfDeselected(shelf: ShelfUiState): Boolean = true
    override fun onShelfEnabled(shelf: ShelfUiState): Boolean = true
}
