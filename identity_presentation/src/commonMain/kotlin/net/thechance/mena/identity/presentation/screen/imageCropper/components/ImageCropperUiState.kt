package net.thechance.mena.identity.presentation.screen.imageCropper.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.IntSize
import kotlin.math.abs

@Stable
class ImageCropperUiState(
    private val minScale: Float,
    private val maxScale: Float,
    imageSize: IntSize
) {
    private var state = mutableStateOf(ImageCropperUiModel(imageSize = imageSize))

    val scale: Float
        get() {
            return state.value.scale
        }

    val translation: Offset
        get() {
            return state.value.translation
        }

    fun zoomBy(gestureZoom: Float, size: IntSize, pan: Offset) {
        val scale = (scale * gestureZoom).coerceIn(1f, 3f)
        updateState(
            ImageCropperUiModel(
                scale = scale,
                imageSize = size,
                translation = translation + pan
            )
        )
    }

    fun zoomIn(value: Float) {
        val scale = (state.value.scale + value).coerceAtMost(maxScale)
        updateState(
            ImageCropperUiModel(
                scale = scale,
                imageSize = state.value.imageSize,
                translation = state.value.translation
            )
        )
    }

    fun zoomOut(value: Float) {
        val scale = (state.value.scale - value).coerceAtLeast(minScale)
        updateState(
            ImageCropperUiModel(
                scale = scale,
                imageSize = state.value.imageSize,
                translation = state.value.translation
            )
        )
    }

    fun reset() {
        updateState(
            ImageCropperUiModel(
                scale = minScale,
                imageSize = state.value.imageSize,
                translation = Offset.Zero
            )
        )
    }

    fun isZoomInEnabled() = scale < maxScale

    fun isZoomOutEnabled() = scale > minScale

    private fun updateState(newState: ImageCropperUiModel) {
        val (scale, translation, imageSize) = newState
        val horizontalBounds = abs((imageSize.width.toFloat() * (scale - 1f)) / 2)
        val initialVerticalBounds = (imageSize.height - imageSize.width) / 2
        val newAddedVerticalBounds = abs((imageSize.height.toFloat() * (scale - 1f)) / 2)
        val verticalBounds = newAddedVerticalBounds + initialVerticalBounds
        val maxOffsetOfX = translation.x.coerceIn(-horizontalBounds, horizontalBounds)
        val maxOffsetOfY = translation.y.coerceIn(-verticalBounds, verticalBounds)
        state.value = state.value.copy(
            scale = scale,
            translation = translation.copy(maxOffsetOfX, maxOffsetOfY),
            imageSize = imageSize
        )
    }

    data class ImageCropperUiModel(
        val scale: Float = 1f,
        val translation: Offset = Offset.Zero,
        val imageSize: IntSize
    )
}

@Composable
fun rememberImageCropState(
    minScale: Float = 1f,
    maxScale: Float = 3f,
    imageSize: IntSize = LocalWindowInfo.current.containerSize
) = remember { ImageCropperUiState(minScale, maxScale, imageSize) }