package net.thechance.mena.dukan.presentation.screen.dukanDetails.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import net.thechance.mena.dukan.presentation.util.pagination.LoadMoreOnScroll
import net.thechance.mena.dukan.presentation.util.pagination.Pager
import net.thechance.mena.dukan.presentation.util.pagination.PagingData
import net.thechance.mena.dukan.presentation.viewModel.dukanDetails.DukanDetailsUiState


@Composable
fun ProductsList(
    products: PagingData<DukanDetailsUiState.ProductUiState>,
    pager: Pager<Int, DukanDetailsUiState.ProductUiState>,
    modifier: Modifier = Modifier,
) {
    val lazyListState = rememberLazyListState()

    lazyListState.LoadMoreOnScroll(pager)

    LazyColumn(
        modifier = modifier.fillMaxWidth()
            .padding(top = Theme.spacing._8),
        verticalArrangement = Arrangement.spacedBy(Theme.spacing._8),
        contentPadding = PaddingValues(
            horizontal = Theme.spacing._16,
            vertical = Theme.spacing._8
        ),
        state = lazyListState
    ) {
        items(products.items) { product ->
            ProductCard(
                modifier = Modifier,
                productUiState = product,
            )
        }
    }
}


