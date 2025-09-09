package net.thechance.mena.designsystem.presentation.component.button

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
fun PrimaryButton(
    text: String,
    trailingIcon: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 14.sp,
    lineHeight: TextUnit = 22.sp,
    iconSize: Dp = 20.dp,
    iconStartPadding: Dp = Theme.spacing._8,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    containerColor: Color = Theme.colorScheme.primary.primary,
    disabledContainerColor: Color = Theme.colorScheme.disabled,
    contentColor: Color = Theme.colorScheme.primary.onPrimary,
    disabledContentColor: Color = Theme.colorScheme.textDisabled,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = Theme.spacing._16,
        vertical = Theme.spacing._8
    ),
    shape: Shape = RoundedCornerShape(Theme.radius.md)
) {
    Button(
        isEnabled = isEnabled,
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContentColor = disabledContentColor,
        disabledContainerColor = disabledContainerColor,
        contentPadding = contentPadding,
        shape = shape,
        onClick = onClick,
        modifier = modifier
    ) {
        BaseButtonContent(
            text = text,
            contentColor = it,
            trailingIcon = trailingIcon,
            iconSize = iconSize,
            iconStartPadding = iconStartPadding,
            isLoading = isLoading,
            fontSize = fontSize,
            lineHeight = lineHeight,
            loadingColors = listOf(
                Theme.colorScheme.primary.onPrimaryHint,
                Theme.colorScheme.primary.onPrimaryBody,
                Theme.colorScheme.primary.onPrimary
            )
        )
    }
}


@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 14.sp,
    lineHeight: TextUnit = 22.sp,
    iconSize: Dp = 20.dp,
    iconStartPadding: Dp = Theme.spacing._8,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    containerColor: Color = Theme.colorScheme.primary.primary,
    disabledContainerColor: Color = Theme.colorScheme.disabled,
    contentColor: Color = Theme.colorScheme.primary.onPrimary,
    disabledContentColor: Color = Theme.colorScheme.textDisabled,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = Theme.spacing._16,
        vertical = Theme.spacing._8
    ),
    shape: Shape = RoundedCornerShape(Theme.radius.md)
) {
    Button(
        isEnabled = isEnabled,
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContentColor = disabledContentColor,
        disabledContainerColor = disabledContainerColor,
        contentPadding = contentPadding,
        shape = shape,
        onClick = onClick,
        modifier = modifier
    ) {
        BaseButtonContent(
            text = text,
            trailingIcon = null,
            contentColor = it,
            iconSize = iconSize,
            iconStartPadding = iconStartPadding,
            isLoading = isLoading,
            fontSize = fontSize,
            lineHeight = lineHeight,
            loadingColors = listOf(
                Theme.colorScheme.primary.onPrimaryHint,
                Theme.colorScheme.primary.onPrimaryBody,
                Theme.colorScheme.primary.onPrimary
            )
        )
    }
}


@Composable
fun PrimaryButton(
    icon: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = 14.sp,
    lineHeight: TextUnit = 22.sp,
    iconSize: Dp = 20.dp,
    iconStartPadding: Dp = Theme.spacing._8,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    containerColor: Color = Theme.colorScheme.primary.primary,
    disabledContainerColor: Color = Theme.colorScheme.disabled,
    contentColor: Color = Theme.colorScheme.primary.onPrimary,
    disabledContentColor: Color = Theme.colorScheme.textDisabled,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = Theme.spacing._16,
        vertical = Theme.spacing._8
    ),
    shape: Shape = RoundedCornerShape(Theme.radius.md)
) {
    Button(
        isEnabled = isEnabled,
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContentColor = disabledContentColor,
        disabledContainerColor = disabledContainerColor,
        contentPadding = contentPadding,
        shape = shape,
        onClick = onClick,
        modifier = modifier
    ) {
        BaseButtonContent(
            trailingIcon = icon,
            text = null,
            contentColor = it,
            iconSize = iconSize,
            iconStartPadding = iconStartPadding,
            isLoading = isLoading,
            fontSize = fontSize,
            lineHeight = lineHeight,
            loadingColors = listOf(
                Theme.colorScheme.primary.onPrimaryHint,
                Theme.colorScheme.primary.onPrimaryBody,
                Theme.colorScheme.primary.onPrimary
            )
        )
    }
}

@Preview
@Composable
private fun PreviewPrimaryButton() {
    MenaTheme {
        PrimaryButton(
            text = "Button",
            trailingIcon = painterResource(resource = Res.drawable.ic_cheese_cake),
            onClick = {},
            modifier = Modifier
        )
    }
}