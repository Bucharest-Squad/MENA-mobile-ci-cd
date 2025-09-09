package net.thechance.mena.designsystem.presentation.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mena.design_system.generated.resources.Res
import mena.design_system.generated.resources.ic_cheese_cake
import net.thechance.mena.designsystem.presentation.component.button.content.BaseButtonContent
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun OutlinedButton(
    text: String,
    trailingIcon: Painter,
    contentDescription: String? = null,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 14.sp,
    lineHeight: TextUnit = 22.sp,
    iconSize: Dp = 20.dp,
    iconStartPadding: Dp = Theme.spacing._8,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    contentColor: Color = Theme.colorScheme.primary.onPrimary,
    disabledContainerColor: Color = Theme.colorScheme.disabled,
    disabledContentColor: Color = Theme.colorScheme.textDisabled,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = Theme.spacing._16,
        vertical = Theme.spacing._8
    ),
    shape: Shape = RoundedCornerShape(Theme.radius.md)
) {
    Button(
        isEnabled = isEnabled,
        shape = shape,
        borderStroke = BorderStroke(width = 1.dp, color = Theme.colorScheme.stroke),
        contentColor = contentColor,
        disabledContentColor = disabledContentColor,
        disabledContainerColor = disabledContainerColor,
        contentPadding = contentPadding,
        onClick = onClick,
        modifier = modifier
    ) {
        BaseButtonContent(
            text = text,
            trailingIcon = trailingIcon,
            iconSize = iconSize,
            iconStartPadding = iconStartPadding,
            isLoading = isLoading,
            fontSize = fontSize,
            lineHeight = lineHeight,
            contentDescription = contentDescription,
            contentColor = it,
            loadingColors = emptyList()
        )
    }
}

@Composable
fun OutlinedButton(
    text: String,
    contentDescription: String? = null,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 14.sp,
    lineHeight: TextUnit = 22.sp,
    iconSize: Dp = 20.dp,
    iconStartPadding: Dp = Theme.spacing._8,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    contentColor: Color = Theme.colorScheme.primary.onPrimary,
    disabledContainerColor: Color = Theme.colorScheme.disabled,
    disabledContentColor: Color = Theme.colorScheme.textDisabled,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = Theme.spacing._16,
        vertical = Theme.spacing._8
    ),
    shape: Shape = RoundedCornerShape(Theme.radius.md)
) {
    Button(
        isEnabled = isEnabled,
        shape = shape,
        borderStroke = BorderStroke(width = 1.dp, color = Theme.colorScheme.stroke),
        contentColor = contentColor,
        disabledContentColor = disabledContentColor,
        disabledContainerColor = disabledContainerColor,
        contentPadding = contentPadding,
        onClick = onClick,
        modifier = modifier
    ) {
        BaseButtonContent(
            text = text,
            trailingIcon = null,
            iconSize = iconSize,
            iconStartPadding = iconStartPadding,
            isLoading = isLoading,
            fontSize = fontSize,
            lineHeight = lineHeight,
            contentDescription = contentDescription,
            contentColor = it,
            loadingColors = emptyList()
        )
    }
}


@Composable
fun OutlinedButton(
    icon: Painter,
    contentDescription: String? = null,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 14.sp,
    lineHeight: TextUnit = 22.sp,
    iconSize: Dp = 20.dp,
    iconStartPadding: Dp = Theme.spacing._8,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    contentColor: Color = Theme.colorScheme.primary.onPrimary,
    disabledContainerColor: Color = Theme.colorScheme.disabled,
    disabledContentColor: Color = Theme.colorScheme.textDisabled,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = Theme.spacing._16,
        vertical = Theme.spacing._8
    ),
    shape: Shape = RoundedCornerShape(Theme.radius.md)
) {
    Button(
        isEnabled = isEnabled,
        shape = shape,
        borderStroke = BorderStroke(width = 1.dp, color = Theme.colorScheme.stroke),
        contentColor = contentColor,
        disabledContentColor = disabledContentColor,
        disabledContainerColor = disabledContainerColor,
        contentPadding = contentPadding,
        onClick = onClick,
        modifier = modifier
    ) {
        BaseButtonContent(
            text = null,
            trailingIcon = icon,
            iconSize = iconSize,
            iconStartPadding = iconStartPadding,
            isLoading = isLoading,
            fontSize = fontSize,
            lineHeight = lineHeight,
            contentDescription = contentDescription,
            contentColor = it,
            loadingColors = emptyList()
        )
    }
}

@Preview
@Composable
private fun PreviewOutlineButton() {
    MenaTheme {
        OutlinedButton(
            text = "Button",
            trailingIcon = painterResource(resource = Res.drawable.ic_cheese_cake),
            onClick = {},
            modifier = Modifier
        )
    }
}