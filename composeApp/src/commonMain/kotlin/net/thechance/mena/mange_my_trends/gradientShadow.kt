package net.thechance.mena.mange_my_trends

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.gradientShadow(
    startColor: Color = Color(color = 0x00FFFFFF),
    endColor: Color = Color(color = 0x33FFFFFF),
    spread: Dp = 18.dp,
    offsetY: Dp = 2.dp
): Modifier = this.then(
    other = Modifier.drawBehind {
        val spreadPx = spread.toPx()
        val offsetYPx = offsetY.toPx()

        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(startColor, endColor),
                startY = 0f,
                endY = size.height + spreadPx
            ),
            topLeft = Offset(0f, offsetYPx),
            size = Size(size.width, size.height + spreadPx)
        )
    }
)