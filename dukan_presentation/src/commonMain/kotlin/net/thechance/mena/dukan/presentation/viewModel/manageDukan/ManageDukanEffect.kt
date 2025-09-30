package net.thechance.mena.dukan.presentation.viewModel.manageDukan

sealed interface ManageDukanEffect {
    object NavigateBack : ManageDukanEffect
    object NavigateToAddShelf : ManageDukanEffect
    object NavigateToEditShelf : ManageDukanEffect
    object NavigateToAddProduct : ManageDukanEffect
    object NavigateToProductDetails : ManageDukanEffect
}
