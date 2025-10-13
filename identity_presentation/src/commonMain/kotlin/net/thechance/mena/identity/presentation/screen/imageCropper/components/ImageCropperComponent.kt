package net.thechance.mena.identity.presentation.screen.imageCropper.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.dp
import mena.identity_presentation.generated.resources.Res
import mena.identity_presentation.generated.resources.img_cat
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ImageCropperComponent(
    image: Painter,
    contentDescription: String,
    onCropImage: (imageBitmap: ImageBitmap) -> Unit,
    onUploadAnotherImage: (ImageBitmap) -> Unit,
    modifier: Modifier = Modifier,
    imagCropperUiState: ImageCropperUiState = rememberImageCropState(),
    contentPadding: PaddingValues = PaddingValues(16.dp)
) {
    val density = LocalDensity.current
    val direction = LocalLayoutDirection.current
    val containerSize = LocalWindowInfo.current.containerSize

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = Theme.colorScheme.background.surface)
            .padding(contentPadding)
            .clipToBounds()
    ) {
        ImageCropperSection(
            image = image,
            contentDescription = contentDescription,
            scale = imagCropperUiState.state.scale,
            translation = imagCropperUiState.state.translation,
            onTransformation = imagCropperUiState::zoomBy,
            updateImageSize = imagCropperUiState::updateImageSize,
            modifier = Modifier
                .clip(RoundedCornerShape(Theme.radius.lg))
                .align(Alignment.CenterHorizontally)
                .then(
                    if (containerSize.width < containerSize.height) {
                        Modifier.fillMaxWidth()
                            .weight(1f)
                    } else {
                        Modifier
                            .fillMaxWidth(0.64f)
                            .aspectRatio(1f)
                    }
                )
        )

        ZoomOptionsSection(
            onResetClick = imagCropperUiState::reset,
            isZoomInEnabled = imagCropperUiState.isZoomInEnabled,
            isZoomOutEnabled = imagCropperUiState.isZoomOutEnabled,
            onZoomInClicked = { imagCropperUiState.zoomIn(0.1f) },
            onZoomOutClicked = { imagCropperUiState.zoomOut(0.1f) },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 12.dp)
                .clip(RoundedCornerShape(Theme.radius.full))
                .background(Theme.colorScheme.background.surfaceLow)
                .padding(horizontal = 12.dp, vertical = 6.dp)
        )

        SaveButton(
            onClick = {
                val imageBitmap = imagCropperUiState.cropToBitmap(
                    painter = image,
                    density = density,
                    layoutDirection = direction
                )

                onCropImage(imageBitmap)
            },
            modifier = Modifier
                .padding(top = 12.dp)
                .align(Alignment.CenterHorizontally)
        )

        UploadAnotherImageButton(
            onClick = onUploadAnotherImage,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp, bottom = 18.dp)
        )
    }
}

@Preview
@Composable
private fun ImageCropperPreview() {
    MenaTheme {
        ImageCropperComponent(
            image = painterResource(Res.drawable.img_cat),
            contentDescription = "image",
            onCropImage = {},
            onUploadAnotherImage = {}
        )
    }
}