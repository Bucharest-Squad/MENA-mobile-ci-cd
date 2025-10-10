package net.thechance.mena.dukan.presentation.util.stubPreviews

import net.thechance.mena.dukan.presentation.util.pagination.Pager
import net.thechance.mena.dukan.presentation.util.pagination.PagingConfig
import net.thechance.mena.dukan.presentation.util.pagination.PagingSource
import net.thechance.mena.dukan.presentation.viewModel.dukanDetails.DukanDetailsInteractionListener
import net.thechance.mena.dukan.presentation.viewModel.dukanDetails.DukanDetailsUiState

object PreviewDukanDetailsInteractionListener : DukanDetailsInteractionListener {
    override fun onBackClicked() {
    }

    override fun onShelfClicked(id: String): Pager<Int, DukanDetailsUiState.ProductUiState> {
        return pager
    }

    override fun onViewAllShelfProductsClicked(id: String, name: String) {
    }

    override fun onViewDukanOnMapClicked(latitude: Double, longitude: Double) {
    }

    override fun productsShelfView(id: String): Pager<Int, DukanDetailsUiState.ProductUiState> {
        return pager
    }

    val pager = Pager(
        config = PagingConfig(),
        pagingSourceFactory = {
            object : PagingSource<Int, DukanDetailsUiState.ProductUiState>() {
                override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DukanDetailsUiState.ProductUiState> {
                    return LoadResult.Page(emptyList(), null, null)
                }
            }
        },
    )
}