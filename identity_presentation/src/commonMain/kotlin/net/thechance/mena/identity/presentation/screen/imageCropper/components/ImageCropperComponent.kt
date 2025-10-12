package net.thechance.mena.identity.presentation.screen.imageCropper.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
    onSave: () -> Unit,
    onUploadAnotherImage: (ImageBitmap) -> Unit,
    modifier: Modifier = Modifier,
    imagCropperUiState: ImageCropperUiState = rememberImageCropState(),
    contentPadding: PaddingValues = PaddingValues(16.dp)
) {
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
            scale = imagCropperUiState.scale,
            translation = imagCropperUiState.translation,
            onTransformation = imagCropperUiState::zoomBy,
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterHorizontally)
                .clip(RoundedCornerShape(Theme.radius.lg))
                .fillMaxWidth()
        )

        ZoomOptionsSection(
            isZoomInEnabled = imagCropperUiState.isZoomInEnabled(),
            isZoomOutEnabled = imagCropperUiState.isZoomOutEnabled(),
            onZoomInClicked = { imagCropperUiState.zoomIn(0.1f) },
            onZoomOutClicked = { imagCropperUiState.zoomOut(0.1f) },
            onResetClick = imagCropperUiState::reset
        )

        SaveButton(
            onClick = onSave,
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
            onSave = {},
            onUploadAnotherImage = {}
        )
    }
}