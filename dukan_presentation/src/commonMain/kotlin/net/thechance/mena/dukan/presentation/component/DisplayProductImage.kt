package net.thechance.mena.dukan.presentation.component

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import mena.dukan_presentation.generated.resources.Res
import mena.dukan_presentation.generated.resources.ic_cancel
import mena.dukan_presentation.generated.resources.upload_dukan_image
import net.thechance.mena.designsystem.presentation.component.icon.Icon
import net.thechance.mena.designsystem.presentation.component.progressBar.ProgressBar
import net.thechance.mena.designsystem.presentation.component.text.Text
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DisplayProductImage(
    modifier: Modifier = Modifier,
    image: ImageBitmap,
    imageSizeInMegaByte: Double,
    productImageState: ProductImageState = ProductImageState.LOADING,
    errorMessage: String? = null,
    onCancelClick: (image: ImageBitmap) -> Unit
) {
    Box(
        modifier = modifier
            .height(104.dp)
            .width(94.dp)
            .background(color = Transparent),
        contentAlignment = Alignment.Center
    )
    {
        AnimatedContent(
            targetState = productImageState,
            label = "display product Image",
            transitionSpec = { fadeTransitionSpec() },
            modifier = Modifier.size(size = 88.dp).align(Alignment.TopCenter)
        ) { currentState ->
            when (currentState) {
                ProductImageState.LOADING -> LoadingContentImage(imageSize = imageSizeInMegaByte)
                ProductImageState.SUCCESS -> SuccessContentImage(image = image)
                ProductImageState.ERROR -> ErrorContentImage(image = image)
            }
        }

        CancelImageButton(
            modifier = Modifier.align(Alignment.TopEnd),
            productImageState = productImageState,
            onCancelClick = { onCancelClick(image) },
        )

        errorMessage?.let { error ->
            if (productImageState == ProductImageState.ERROR) {
                Text(
                    modifier = Modifier.fillMaxWidth().padding(start = 4.dp)
                        .align(Alignment.BottomCenter),
                    text = error,
                    style = Theme.typography.label.extraSmall,
                    color = Theme.colorScheme.error,
                    maxLines = 1,
                )
            }
        }
    }
}

@Composable
private fun CancelImageButton(
    modifier: Modifier,
    productImageState: ProductImageState,
    onCancelClick: () -> Unit,
) {
    val color = if (productImageState == ProductImageState.SUCCESS) {
        Theme.colorScheme.primary.primary
    } else {
        Theme.colorScheme.error
    }

    val tintIconColor = animateColorAsState(
        targetValue = color,
        animationSpec = tween(durationMillis = 500),
        label = "Icon Tint",
    )

    val isIconVisible = productImageState == ProductImageState.SUCCESS ||
            productImageState == ProductImageState.ERROR


    if (isIconVisible) {
        Box(
            modifier = modifier
                .size(size = 24.dp)
                .offset(x= 2.dp, y = (-4).dp)
                .background(
                    color = Theme.colorScheme.background.surfaceLow,
                    shape = RoundedCornerShape(Theme.radius.full)
                )
                .clip(RoundedCornerShape(Theme.radius.full))
                .clickable(onClick = onCancelClick)
            ,
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(resource = Res.drawable.ic_cancel),
                contentDescription = "cancel add Image",
                tint = tintIconColor.value,
            )
        }
    }
}


@Composable
private fun LoadingContentImage(imageSize: Double) {
    Column(
        modifier = Modifier.fillMaxSize()
            .background(
                color = Theme.colorScheme.background.surfaceLow,
                shape = RoundedCornerShape(Theme.radius.md)
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "${imageSize}MB",
            style = Theme.typography.label.small
        )
        ProgressBar(
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 8.dp)
                .fillMaxWidth()
                .height(height = 4.dp),
        )
    }
}

@Composable
private fun SuccessContentImage(image: ImageBitmap) {
    Box(
        modifier = Modifier.size(88.dp)
            .background(
                color = Theme.colorScheme.background.surfaceHigh,
                shape = RoundedCornerShape(size = Theme.radius.md)
            ),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(size = Theme.radius.md)),
            bitmap = image,
            contentDescription = stringResource(resource = Res.string.upload_dukan_image),
            contentScale = ContentScale.Crop
        )

    }
}

@Composable
private fun ErrorContentImage(image: ImageBitmap) {
    val dashPattern = floatArrayOf(15f, 5f)
    val phase = 0f
    val strokeWidth = 1.dp
    val borderColor = Theme.colorScheme.error
    val cornerRadiusValue = Theme.radius.md

    Box(
        modifier = Modifier.size(88.dp)
            .background(
                color = Theme.colorScheme.background.surfaceHigh,
                shape = RoundedCornerShape(size = Theme.radius.md)
            ).drawWithContent {
                drawContent()
                drawRoundRect(
                    color = borderColor,
                    topLeft = Offset(
                        x = strokeWidth.toPx() / 2,
                        y = strokeWidth.toPx() / 2
                    ),
                    size = Size(
                        width = size.width - strokeWidth.toPx(),
                        height = size.height - strokeWidth.toPx()
                    ),
                    cornerRadius = CornerRadius(
                        x = cornerRadiusValue.toPx(),
                        y = cornerRadiusValue.toPx()
                    ),
                    style = Stroke(
                        width = strokeWidth.toPx(),
                        pathEffect = PathEffect.dashPathEffect(intervals = dashPattern, phase),
                    )
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(size = Theme.radius.md)),
            bitmap = image,
            contentDescription = stringResource(resource = Res.string.upload_dukan_image),
            contentScale = ContentScale.Crop
        )
    }

}

private fun fadeTransitionSpec(): ContentTransform {
    return fadeIn(
        animationSpec = tween(
            durationMillis = 500,
            delayMillis = 100,
            easing = EaseIn
        )
    ) togetherWith fadeOut(
        animationSpec = tween(
            durationMillis = 500,
            delayMillis = 100,
            easing = EaseOut
        )
    )
}

enum class ProductImageState {
    SUCCESS, LOADING, ERROR
}

@Preview()
@Composable
fun LoadingProductImagePreview() {
    MenaTheme {
        Box(
            modifier = Modifier.size(150.dp)
                .background(color = Theme.colorScheme.background.surfaceHigh),
            contentAlignment = Alignment.Center
        ) {
            val image = ImageBitmap(100, 100)

            DisplayProductImage(
                image = image,
                imageSizeInMegaByte = 2.0,
                productImageState = ProductImageState.LOADING,
                onCancelClick = {}
            )
        }
    }
}
@Preview()
@Composable
fun SuccessProductImagePreview() {
    MenaTheme {
        Box(
            modifier = Modifier.size(150.dp)
                .background(color = Theme.colorScheme.background.surfaceHigh),
            contentAlignment = Alignment.Center
        ) {
            val image = ImageBitmap(100, 100)

            DisplayProductImage(
                image = image,
                imageSizeInMegaByte = 2.0,
                productImageState = ProductImageState.SUCCESS,
                onCancelClick = {}
            )
        }
    }
}
@Preview()
@Composable
fun ErrorProductImagePreview() {
    MenaTheme {
        Box(
            modifier = Modifier.size(150.dp)
                .background(color = Theme.colorScheme.background.surfaceHigh),
            contentAlignment = Alignment.Center
        ) {
            val image = ImageBitmap(100, 100)

            DisplayProductImage(
                image = image,
                imageSizeInMegaByte = 0.0,
                productImageState = ProductImageState.ERROR,
                errorMessage = "Uploading Failed",
                onCancelClick = {}
            )
        }
    }
}



