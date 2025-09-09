package net.thechance.mena.designsystem.presentation.component.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import mena.design_system.generated.resources.Res
import mena.design_system.generated.resources.ic_cheese_cake
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun FabButton(
    painter: Painter,
    contentDescription: String? = null,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconSize: Dp = 24.dp,
    containerColor: Color = Theme.colorScheme.primary.primary,
    contentColor: Color = Theme.colorScheme.primary.onPrimary,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    shape: Shape = RoundedCornerShape(Theme.radius.md)
) {
    Button(
        onClick = onClick,
        containerColor = containerColor,
        contentColor = contentColor,
        shape = shape,
        modifier = modifier,
        contentPadding = contentPadding,
    ) {
        Icon(
            painter = painter,
            tint = it,
            contentDescription = contentDescription,
            modifier = Modifier
                .padding(contentPadding)
                .size(iconSize)
        )
    }
}


@Preview
@Composable
private fun PreviewFabButton() {
    MenaTheme {
        FabButton(
            painter = painterResource(resource = Res.drawable.ic_cheese_cake),
            onClick = {},
            modifier = Modifier
        )
    }
}