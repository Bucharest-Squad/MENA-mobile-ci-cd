package net.thechance.mena.dukan.presentation.screen.dukanDetails.shimmer.noImageDukanDetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import net.thechance.mena.dukan.presentation.component.productCard.LoadingProductCard
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ShelfWithProductsShimmerNoImageDukan() {
    Column(
        Modifier.fillMaxWidth()
            .padding(top = Theme.spacing._16, start = Theme.spacing._16, end = Theme.spacing._16)
    ) {
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize(),
            columns = GridCells.Fixed(1)
        ) {
            items(20) {
                LoadingProductCard()
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF2F4F7)
@Composable
private fun PreviewShelfWithProductsShimmerNoImageDukan() {
    MenaTheme {
        ShelfWithProductsShimmerNoImageDukan()
    }
}