package net.thechance.mena.designsystem.presentation.component.button

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import net.thechance.mena.designsystem.presentation.component.button.content.BaseButtonContent
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import androidx.compose.material3.TextButton
import androidx.compose.ui.graphics.Shape

@Composable
fun NegativeTextButton(
    text: String,
    trailingIcon: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconSize: Dp = 16.dp,
    contentDescription: String? = null,
    isEnabled: Boolean = true,
    contentColor: Color = Theme.colorScheme.error,
    disabledContentColor: Color = Theme.colorScheme.disabled,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    iconStartPadding: Dp = Theme.spacing._4,
    fontSize: TextUnit = TextUnit.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    shape: Shape = RoundedCornerShape(Theme.radius.md)
) {
    Button(
        onClick = onClick,
        isEnabled = isEnabled,
        contentPadding = contentPadding,
        contentColor = contentColor,
        disabledContentColor = disabledContentColor,
        shape = shape,
        modifier = modifier.defaultMinSize(minWidth = 1.dp, minHeight = 1.dp),
    ) {
        BaseButtonContent(
            text = text,
            trailingIcon = trailingIcon,
            contentDescription = contentDescription,
            iconSize = iconSize,
            iconStartPadding = iconStartPadding,
            isLoading = false,
            fontSize = fontSize,
            lineHeight = lineHeight,
            letterSpacing = letterSpacing,
            overflow = overflow,
            contentColor = it,
            loadingColors = emptyList()
        )
    }
}

@Composable
fun NegativeTextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconSize: Dp = 16.dp,
    contentDescription: String? = null,
    isEnabled: Boolean = true,
    contentColor: Color = Theme.colorScheme.error,
    disabledContentColor: Color = Theme.colorScheme.disabled,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    iconStartPadding: Dp = Theme.spacing._4,
    fontSize: TextUnit = TextUnit.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    shape: Shape = RoundedCornerShape(Theme.radius.md)
) {
    Button(
        onClick = onClick,
        isEnabled = isEnabled,
        contentPadding = contentPadding,
        contentColor = contentColor,
        disabledContentColor = disabledContentColor,
        shape = shape,
        modifier = modifier.defaultMinSize(minWidth = 1.dp, minHeight = 1.dp),
    ) {
        BaseButtonContent(
            text = text,
            trailingIcon = null,
            contentDescription = contentDescription,
            iconSize = iconSize,
            iconStartPadding = iconStartPadding,
            isLoading = false,
            fontSize = fontSize,
            lineHeight = lineHeight,
            letterSpacing = letterSpacing,
            overflow = overflow,
            contentColor = it,
            loadingColors = emptyList()
        )
    }
}

@Composable
fun NegativeTextButton(
    icon: Painter,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconSize: Dp = 16.dp,
    contentDescription: String? = null,
    isEnabled: Boolean = true,
    contentColor: Color = Theme.colorScheme.error,
    disabledContentColor: Color = Theme.colorScheme.disabled,
    contentPadding: PaddingValues = PaddingValues(0.dp),
    iconStartPadding: Dp = Theme.spacing._4,
    fontSize: TextUnit = TextUnit.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    shape: Shape = RoundedCornerShape(Theme.radius.md)
) {
    Button(
        onClick = onClick,
        isEnabled = isEnabled,
        contentPadding = contentPadding,
        contentColor = contentColor,
        disabledContentColor = disabledContentColor,
        shape = shape,
        modifier = modifier.defaultMinSize(minWidth = 1.dp, minHeight = 1.dp),
    ) {
        BaseButtonContent(
            text = null,
            trailingIcon = icon,
            contentDescription = contentDescription,
            iconSize = iconSize,
            iconStartPadding = iconStartPadding,
            isLoading = false,
            fontSize = fontSize,
            lineHeight = lineHeight,
            letterSpacing = letterSpacing,
            overflow = overflow,
            contentColor = it,
            loadingColors = emptyList()
        )
    }
}