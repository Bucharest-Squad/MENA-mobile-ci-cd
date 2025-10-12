package net.thechance.mena.identity.presentation.screen.imageCropper.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import mena.identity_presentation.generated.resources.Res
import mena.identity_presentation.generated.resources.ic_add
import mena.identity_presentation.generated.resources.ic_remove
import mena.identity_presentation.generated.resources.reset
import net.thechance.mena.designsystem.presentation.component.button.TextButton
import net.thechance.mena.designsystem.presentation.component.icon.Icon
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ColumnScope.ZoomOptionsSection(
    onResetClick: () -> Unit,
    isZoomInEnabled: Boolean,
    isZoomOutEnabled: Boolean,
    onZoomInClicked: () -> Unit = {},
    onZoomOutClicked: () -> Unit = {}
) {
    val zoomOutButtonBackground by animateColorAsState(
        targetValue = when (isZoomOutEnabled) {
            true -> Theme.colorScheme.background.surface
            false -> Theme.colorScheme.textDisabled
        },
        label = "zoomOutButtonBackground"
    )
    val zoomInButtonBackground by animateColorAsState(
        targetValue = when (isZoomInEnabled) {
            true -> Theme.colorScheme.background.surface
            false -> Theme.colorScheme.textDisabled
        },
        label = "zoomInButtonBackground"
    )

    Row(
        Modifier
            .align(Alignment.CenterHorizontally)
            .padding(top = 12.dp)
            .clip(RoundedCornerShape(Theme.radius.full))
            .background(Theme.colorScheme.background.surfaceLow)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        ZoomButton(
            painter = painterResource(Res.drawable.ic_remove),
            contentDescription = "zoom out",
            backgroundColor = zoomOutButtonBackground,
            isEnabled = isZoomOutEnabled,
            onZoomClicked = onZoomOutClicked
        )

        ZoomButton(
            painter = painterResource(Res.drawable.ic_add),
            contentDescription = "zoom in",
            backgroundColor = zoomInButtonBackground,
            isEnabled = isZoomInEnabled,
            onZoomClicked = onZoomInClicked,
            modifier = Modifier.padding(start = 4.dp)
        )

        Divider()

        TextButton(
            text = stringResource(Res.string.reset),
            onClick = onResetClick,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@Composable
private fun ZoomButton(
    painter: Painter,
    contentDescription: String,
    backgroundColor: Color,
    onZoomClicked: () -> Unit,
    modifier: Modifier = Modifier,
    isEnabled: Boolean
) {
    val disabledColor = Theme.colorScheme.disabled
    val enabledColor = Theme.colorScheme.primary.primary

    val iconTint by animateColorAsState(
        targetValue = if (isEnabled) enabledColor else disabledColor
    )

    Icon(
        painter = painter,
        contentDescription = contentDescription,
        tint = iconTint,
        modifier = modifier
            .clip(RoundedCornerShape(Theme.radius.full))
            .clickable(
                onClick = onZoomClicked,
                enabled = isEnabled
            )
            .size(24.dp)
            .background(color = backgroundColor)
            .padding(4.dp)
    )
}

@Composable
private fun RowScope.Divider() {
    Box(
        modifier = Modifier
            .align(Alignment.CenterVertically)
            .padding(horizontal = 8.dp)
            .height(20.dp)
            .width(1.dp)
            .background(Theme.colorScheme.stroke)
    )
}