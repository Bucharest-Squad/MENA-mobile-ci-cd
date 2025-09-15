package net.thechance.mena.wallet.presentation.screen.wallet.component

import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

class TopNotchShape(
    private val offset: Float,
    private val circleRadius: Dp,
    private val cornerRadius: Dp,
    private val circleGap: Dp = 5.dp,
) : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(getPath(size, density))
    }

    private fun getPath(size: Size, density: Density): Path {
        val cutoutCenterX = offset
        val cutoutRadius = density.run { (circleRadius + circleGap).toPx() }
        val cornerRadiusPx = density.run { cornerRadius.toPx() }
        val cornerDiameter = cornerRadiusPx * 2
        return Path().apply {
            val cutoutEdgeOffset = cutoutRadius * 2.5f
            val cutoutLeftX = cutoutCenterX - cutoutEdgeOffset
            val cutoutRightX = cutoutCenterX + cutoutEdgeOffset

            moveTo(x = 0F, y = size.height)

            if (cutoutLeftX > 0) {
                drawTopLeftEdge(cutoutLeftX, cornerRadiusPx, cornerDiameter)
            }

            lineTo(cutoutLeftX, 0f)

            drawCutout(cutoutCenterX, cutoutRadius, cutoutRightX)

            if (cutoutRightX < size.width) {
                drawTopRightEdge(cutoutRightX, size, cornerRadiusPx, cornerDiameter)
            }

            lineTo(x = size.width, y = size.height)
            close()
        }
    }

    private fun Path.drawTopLeftEdge(
        cutoutLeftX: Float,
        cornerRadiusPx: Float,
        cornerDiameter: Float
    ) {
        val realLeftCornerDiameter = if (cutoutLeftX >= cornerRadiusPx) {
            cornerDiameter
        } else {
            cutoutLeftX * 2
        }
        arcTo(
            rect = Rect(
                left = 0f,
                top = 0f,
                right = realLeftCornerDiameter,
                bottom = realLeftCornerDiameter
            ),
            startAngleDegrees = 180.0f,
            sweepAngleDegrees = 90.0f,
            forceMoveTo = false
        )
    }

    private fun Path.drawCutout(
        cutoutCenterX: Float,
        cutoutRadius: Float,
        cutoutRightX: Float
    ) {
        cubicTo(
            x1 = cutoutCenterX - cutoutRadius,
            y1 = 0f,
            x2 = cutoutCenterX - cutoutRadius,
            y2 = cutoutRadius,
            x3 = cutoutCenterX,
            y3 = cutoutRadius,
        )
        cubicTo(
            x1 = cutoutCenterX + cutoutRadius,
            y1 = cutoutRadius,
            x2 = cutoutCenterX + cutoutRadius,
            y2 = 0f,
            x3 = cutoutRightX,
            y3 = 0f,
        )
    }

    private fun Path.drawTopRightEdge(
        cutoutRightX: Float,
        size: Size,
        cornerRadiusPx: Float,
        cornerDiameter: Float
    ) {
        val realRightCornerDiameter = if (cutoutRightX <= size.width - cornerRadiusPx) {
            cornerDiameter
        } else {
            (size.width - cutoutRightX) * 2
        }
        arcTo(
            rect = Rect(
                left = size.width - realRightCornerDiameter,
                top = 0f,
                right = size.width,
                bottom = realRightCornerDiameter
            ),
            startAngleDegrees = -90.0f,
            sweepAngleDegrees = 90.0f,
            forceMoveTo = false
        )
    }
}