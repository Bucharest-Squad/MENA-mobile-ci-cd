package net.thechance.mena.dukan.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.dialogs.FileKitType
import io.github.vinceglb.filekit.dialogs.compose.rememberFilePickerLauncher
import kotlinx.coroutines.launch
import mena.dukan_presentation.generated.resources.Res
import mena.dukan_presentation.generated.resources.ic_add_image
import mena.dukan_presentation.generated.resources.upload_dukan_image
import net.thechance.mena.designsystem.presentation.component.image.Image
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun UploadProductImage(
    modifier: Modifier = Modifier,
    onUploadImageClick: (imageFile: PlatformFile) -> Unit = {},
    isUploadingImageEnabled: Boolean = false
) {

    val borderColor = Theme.colorScheme.primary.primary
    val cornerRadiusValue = Theme.radius.md

    val scope = rememberCoroutineScope()

    val filePicker = rememberFilePickerLauncher(type = FileKitType.Image) { file ->
        file?.let { imageFile ->
            scope.launch {
                onUploadImageClick(imageFile)
            }
        }
    }

    Box(
        modifier = modifier
            .size(size = 88.dp)
            .background(
                color = Theme.colorScheme.background.surfaceLow,
                shape = RoundedCornerShape(size = cornerRadiusValue)
            ).clip(RoundedCornerShape(size = cornerRadiusValue))
            .clickable(onClick = { filePicker.launch() }, enabled = isUploadingImageEnabled)
            .drawWithContent {
                drawContent()
                drawBorder(
                    borderColor = borderColor,
                    cornerRadiusValue = cornerRadiusValue.toPx()
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.size(size = 24.dp),
            painter = painterResource(resource = Res.drawable.ic_add_image),
            contentDescription = stringResource(resource = Res.string.upload_dukan_image),
        )
    }
}

private fun DrawScope.drawBorder(
    borderColor: Color,
    cornerRadiusValue: Float
) {
    val dashPattern = floatArrayOf(15f, 5f)
    val phase = 0f
    val strokeWidth = 1.dp
    val strokeWidthPx = strokeWidth.toPx()

    drawRoundRect(
        color = borderColor,
        topLeft = Offset(
            x = strokeWidthPx / 2,
            y = strokeWidthPx / 2
        ),
        size = Size(
            width = size.width - strokeWidthPx,
            height = size.height - strokeWidthPx
        ),
        cornerRadius = CornerRadius(
            x = cornerRadiusValue,
            y = cornerRadiusValue
        ),
        style = Stroke(
            width = strokeWidth.toPx(),
            pathEffect = PathEffect.dashPathEffect(intervals = dashPattern, phase),
        )
    )
}


@Preview
@Composable
private fun DefaultContainerImagePreview() {
    MenaTheme {
        Box(
            modifier = Modifier.size(150.dp)
                .background(color = Theme.colorScheme.background.surfaceHigh),
            contentAlignment = Alignment.Center
        ) {
            UploadProductImage()
        }
    }
}