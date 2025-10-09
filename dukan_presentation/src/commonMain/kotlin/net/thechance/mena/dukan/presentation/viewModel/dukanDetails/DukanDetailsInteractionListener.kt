package net.thechance.mena.dukan.presentation.viewModel.dukanDetails

import net.thechance.mena.dukan.presentation.util.pagination.Pager

interface DukanDetailsInteractionListener {
    fun onBackClicked()
    fun onShelfClicked(id: String): Pager<Int, DukanDetailsUiState.ProductUiState>
    fun onViewAllShelfProductsClicked(id: String, name: String)
    fun onViewDukanOnMapClicked(latitude: Double, longitude: Double)

    fun productsShelfView(id: String): Pager<Int, DukanDetailsUiState.ProductUiState>
}