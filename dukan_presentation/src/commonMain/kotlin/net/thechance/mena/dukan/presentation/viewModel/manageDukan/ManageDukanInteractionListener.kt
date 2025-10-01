package net.thechance.mena.dukan.presentation.viewModel.manageDukan

import net.thechance.mena.dukan.domain.entity.Product

interface ManageDukanInteractionListener {
    fun onBackButtonClicked()
    fun onDismissSnackBar()
    fun onDismissDeleteShelfConfirmationDialog()
    fun onShowDeleteShelfConfirmationDialog()
    fun deleteShelf(shelfId: String)
    fun onAddProductClicked()
    fun onProductClick(product: Product)
    fun onEditShelfClicked()
    fun onAddShelfClicked()
    fun onShelfAddedSuccessfully()
    fun isShelfSelected(): (ShelfUiState) -> Boolean
    fun onShelfSelected(shelf: ShelfUiState): Boolean
    fun onShelfDeselected(shelf: ShelfUiState): Boolean
    fun onShelfEnabled(shelf: ShelfUiState): Boolean
}
