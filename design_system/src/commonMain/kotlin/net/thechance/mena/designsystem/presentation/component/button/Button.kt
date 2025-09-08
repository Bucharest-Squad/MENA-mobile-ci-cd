package net.thechance.mena.designsystem.presentation.component.button

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Composable
fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RectangleShape,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    isEnabled: Boolean = true,
    containerColor: Color = Color.Transparent,
    disabledContainerColor: Color = Color.Transparent,
    contentColor: Color = Color.Transparent,
    disabledContentColor: Color = Color.Transparent,
    borderStroke: BorderStroke? = null,
    content: @Composable RowScope.(contentColor: Color) -> Unit
) {
    val buttonBackgroundColor by animateColorAsState(
        if (isEnabled) containerColor else disabledContainerColor
    )

    val buttonContentColor by animateColorAsState(
        if (isEnabled) contentColor else disabledContentColor
    )

    Row(
        modifier = modifier
            .clip(shape)
            .clickable(onClick = onClick)
            .background(color = buttonBackgroundColor)
            .padding(contentPadding)
            .then(
                if (borderStroke != null) Modifier.border(border = borderStroke, shape = shape)
                else Modifier
            )
    ) {
        content(buttonContentColor)
    }
}