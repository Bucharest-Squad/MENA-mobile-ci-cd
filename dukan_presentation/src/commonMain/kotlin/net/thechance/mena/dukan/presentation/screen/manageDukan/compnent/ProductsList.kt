package net.thechance.mena.dukan.presentation.screen.manageDukan.compnent

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import net.thechance.mena.dukan.domain.util.PagedResult
import net.thechance.mena.dukan.presentation.component.productCard.EditProductIcon
import net.thechance.mena.dukan.presentation.component.productCard.ProductCard
import net.thechance.mena.dukan.presentation.screen.productLayout.ProductUiState
import net.thechance.mena.dukan.presentation.util.pagination.LoadMoreOnScroll
import net.thechance.mena.dukan.presentation.util.pagination.Pager
import net.thechance.mena.dukan.presentation.util.pagination.PagingConfig
import net.thechance.mena.dukan.presentation.util.pagination.base.BasePagingSource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun ProductsList(
    products: List<ProductUiState>,
    pager: Pager<Int, ProductUiState>,
    modifier: Modifier = Modifier,
    onProductClick: (ProductUiState) -> Unit = {},
) {
    val lazyListState = rememberLazyListState()

    lazyListState.LoadMoreOnScroll(pager)

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(Theme.spacing._8),
        modifier = modifier,
        state = lazyListState
    ) {
        items(products) { product ->
            ProductCard(
                productUiState = product,
                productAction = {
                    EditProductIcon(onClick = {
                        onProductClick(product)
                    })
                }
            )
        }
    }
}

@Preview
@Composable
private fun ProductsLayoutPreview() {
    MenaTheme {
        ProductsList(
            fakeProducts(),
            pager = Pager(
                config = PagingConfig()
            ) {
                object : BasePagingSource<ProductUiState>() {
                    override suspend fun onFetchPage(pageNumber: Int): PagedResult<ProductUiState> {
                        return PagedResult(
                            items = fakeProducts(),
                            hasPrevious = false,
                            hasNext = true,
                            currentPage = pageNumber,
                            totalPages = 1000,
                        )
                    }
                }
            },
        )
    }
}

private fun fakeProducts(): List<ProductUiState> {
    return listOf(
        ProductUiState(
            id = "1",
            name = "Wireless Bluetooth Headphones",
            description = "Girls Crochet Tank Top description text here for this product",
            price = 79.99,
            imageUrl = "https://example.com/images/headphones1.jpg"
        ),
        ProductUiState(
            id = "2",
            name = "Smartphone Case",
            description = "Durable protective case for all models",
            price = 19.99,
            imageUrl = "https://example.com/images/case1.jpg"
        ),
        ProductUiState(
            id = "3",
            name = "Stainless Steel Water Bottle",
            price = 24.99,
            imageUrl = "https://example.com/images/bottle1.jpg"
        ),
        ProductUiState(
            id = "4",
            name = "Girls Crochet Tank Top",
            description = "Girls Crochet Tank Top description text here for this product",
            price = 15.99,
            imageUrl = "https://example.com/images/coffee1.jpg"
        ),
        ProductUiState(
            id = "5",
            name = "Girls Crochet Tank Top",
            description = "Girls Crochet Tank Top description text here for this product",
            price = 23.70,
            imageUrl = "https://example.com/images/mat1.jpg"
        )
    )
}