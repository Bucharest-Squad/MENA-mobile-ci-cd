package net.thechance.mena.identity.presentation.screen.imageCropper.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.toSize
import kotlin.math.abs

@Stable
class ImageCropperUiState(
    private val minScale: Float,
    private val maxScale: Float,
    imageSize: IntSize
) {
    var state by mutableStateOf(ImageCropperUiModel(imageSize = imageSize))

    val isZoomInEnabled by derivedStateOf {
        state.scale < maxScale
    }
    val isZoomOutEnabled by derivedStateOf {
        state.scale > minScale
    }

    fun zoomBy(gestureZoom: Float, pan: Offset) {
        val scale = (state.scale * gestureZoom).coerceIn(1f, 3f)
        updateState(
            state.copy(scale = scale, translation = state.translation + pan)
        )
    }

    fun zoomIn(value: Float) {
        val scale = (state.scale + value).coerceAtMost(maxScale)
        updateState(newState = state.copy(scale = scale))
    }

    fun zoomOut(value: Float) {
        val scale = (state.scale - value).coerceAtLeast(minScale)
        updateState(newState = state.copy(scale = scale))
    }

    fun reset() {
        updateState(newState = state.copy(scale = minScale, translation = Offset.Zero))
    }

    fun updateImageSize(imageSize: IntSize) {
        state = state.copy(imageSize = imageSize)
    }

    fun cropToBitmap(
        painter: Painter,
        density: Density,
        layoutDirection: LayoutDirection,
    ): ImageBitmap {
        val imageBitmap = ImageBitmap(state.imageSize.width, state.imageSize.height)
        val floatSize = IntSize(state.imageSize.width, state.imageSize.height).toSize()
        val radius = floatSize.width / 2
        val canvas = Canvas(imageBitmap)

        CanvasDrawScope().draw(
            density = density,
            layoutDirection = layoutDirection,
            canvas = canvas,
            size = floatSize
        ) {

            val path = Path().apply {
                addOval(
                    Rect(
                        left = center.x - radius,
                        top = center.y - radius,
                        right = center.x + radius,
                        bottom = center.y + radius
                    )
                )
            }

            clipPath(
                path = path
            ) {
                with(painter) {
                    translate(
                        left = state.translation.x,
                        top = state.translation.y
                    ) {
                        scale(
                            scale = state.scale
                        ) {
                            draw(floatSize)
                        }
                    }
                }
            }
        }

        return imageBitmap
    }

    private fun updateState(newState: ImageCropperUiModel) {
        val (scale, translation, imageSize) = newState
        val horizontalBounds = abs((imageSize.width.toFloat() * (scale - 1f)) / 2)
        val initialVerticalBounds = (imageSize.height - imageSize.width) / 2
        val newAddedVerticalBounds = (imageSize.height.toFloat() * (scale - 1f)) / 2
        val verticalBounds = abs(newAddedVerticalBounds + initialVerticalBounds)
        val maxOffsetOfX = translation.x.coerceIn(-horizontalBounds, horizontalBounds)
        val maxOffsetOfY = translation.y.coerceIn(-verticalBounds, verticalBounds)

        state = state.copy(
            scale = scale,
            translation = translation.copy(maxOffsetOfX, maxOffsetOfY),
            imageSize = imageSize
        )
    }

    @Stable
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
) = remember(minScale, maxScale, imageSize) { ImageCropperUiState(minScale, maxScale, imageSize) }