package net.thechance.mena.dukan.presentation.screen.dukanDetails.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import mena.dukan_presentation.generated.resources.Res
import mena.dukan_presentation.generated.resources.ic_add_shopping_basket
import mena.dukan_presentation.generated.resources.koin_icon
import mena.dukan_presentation.generated.resources.product_image
import mena.dukan_presentation.generated.resources.silver_tc
import net.thechance.mena.designsystem.presentation.component.icon.Icon
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import net.thechance.mena.dukan.presentation.component.productCard.PriceWithIcon
import net.thechance.mena.dukan.presentation.component.productCard.ProductInfo
import net.thechance.mena.dukan.presentation.viewModel.dukanDetails.DukanDetailsUiState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun ProductCard(
    productUiState: DukanDetailsUiState.ProductUiState,
    modifier: Modifier = Modifier
) {
    var showProductQuantity by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = Theme.colorScheme.background.surfaceLow,
                shape = RoundedCornerShape(size = Theme.radius.md)
            ).height(104.dp)
            .padding(Theme.spacing._4),
    ) {
        Box(
            modifier = Modifier.background(
                color = Theme.colorScheme.background.surfaceLow,
                shape = RoundedCornerShape(
                    topStart = Theme.radius.md,
                    bottomStart = Theme.radius.md
                )
            )
        ) {
            AsyncImage(
                model = productUiState.imageUrl,
                contentDescription = stringResource(Res.string.product_image),
                modifier = Modifier
                    .size(96.dp)
                    .clip(RoundedCornerShape(Theme.radius.sm)),
                contentScale = ContentScale.Crop
            )
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(
                    start = Theme.spacing._8,
                    top = Theme.spacing._4,
                    end = Theme.spacing._4
                ),
        ) {
            ProductInfo(
                name = productUiState.name,
                description = productUiState.description
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = Theme.spacing._4),
                verticalAlignment = Alignment.CenterVertically
            ) {
                PriceWithIcon(
                    price = productUiState.price.toString(),
                    iconRes = Res.drawable.silver_tc,
                    contentDescription = stringResource(Res.string.koin_icon),
                )
                Spacer(modifier = Modifier.weight(1f))

                AnimatedContent(
                    targetState = showProductQuantity,
                    transitionSpec ={ (
                            fadeIn(animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing))
                                    togetherWith fadeOut(animationSpec = tween(durationMillis = 300, easing = FastOutLinearInEasing)))
                        .using(SizeTransform(
                            sizeAnimationSpec = { _, _ ->
                                tween(durationMillis = 300, easing = LinearOutSlowInEasing)
                            }
                        )
                        )
                    } ,
                    label = "CartToQuantity"
                ) {
                    if (it) {
                        SetProductQuantity(
                            onAddProductClick = {},
                            onRemoveProductClick = {}
                        )
                    } else {
                        ProductCart(
                            onClick = {
                                showProductQuantity = true
                            },
                        )
                    }
                }
            }

        }
    }
}

@Composable
private fun ProductCart(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(32.dp)
            .clip(RoundedCornerShape(size = Theme.radius.full))
            .background(
                color = Theme.colorScheme.primary.primary,
            )
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(Res.drawable.ic_add_shopping_basket),
            contentDescription = "cart",
            modifier = modifier
                .size(16.dp)
        )
    }
}

@Preview
@Composable
private fun ProductCardPreview() {
    MenaTheme {
        ProductCard(
            DukanDetailsUiState.ProductUiState(
                id = "1",
                imageUrl = "https://calvinklein.scene7.com/is/image/CalvinKlein/LX001376_100_alternate1?wid=1728&qlt=80%2C0&resMode=sharp2&op_usm=0.9%2C1.0%2C8%2C0&iccEmbed=0&fmt=webp",
                name = "Girls Crochet Tank Top",
                description = "Girls Crochet Tank Top description text here for this product",
                price = 39.5
            ),
            modifier = Modifier.padding(Theme.spacing._12),
        )
    }
}