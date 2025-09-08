package net.thechance.mena.designsystem.presentation.component.button

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.thechance.mena.designsystem.presentation.component.button.content.BaseButtonContent
import net.thechance.mena.designsystem.presentation.theme.theme.Theme

@Composable
fun NegativeButton(
    text: String,
    trailingIcon: Painter,
    contentDescription: String? = null,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconSize: Dp = 20.dp,
    iconStartPadding: Dp = Theme.spacing._8,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    containerColor: Color = Theme.colorScheme.error,
    contentColor: Color = Theme.colorScheme.primary.onPrimary,
    disabledContainerColor: Color = Theme.colorScheme.disabled,
    disabledContentColor: Color = Theme.colorScheme.textDisabled,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = Theme.spacing._16,
        vertical = Theme.spacing._8
    ),
    shape: Shape = RoundedCornerShape(Theme.radius.md),
    fontSize: TextUnit = 14.sp,
    lineHeight: TextUnit = 22.sp
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
            .animateContentSize(
            animationSpec = spring(dampingRatio = 0.85f, stiffness = 100f),
            alignment = Alignment.Center
        )
    ) {
        BaseButtonContent(
            text = text,
            trailingIcon = trailingIcon,
            contentDescription = contentDescription,
            iconSize = iconSize,
            iconStartPadding = iconStartPadding,
            isLoading = isLoading,
            fontSize = fontSize,
            lineHeight = lineHeight,
            contentColor = it,
            loadingColors = listOf(
                Theme.colorScheme.primary.onPrimaryHint,
                Theme.colorScheme.primary.onPrimaryBody,
                Theme.colorScheme.primary.onPrimary
            )
        )
    }
}

@Composable
fun NegativeButton(
    text: String,
    contentDescription: String? = null,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconSize: Dp = 20.dp,
    iconStartPadding: Dp = Theme.spacing._8,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    containerColor: Color = Theme.colorScheme.error,
    contentColor: Color = Theme.colorScheme.primary.onPrimary,
    disabledContainerColor: Color = Theme.colorScheme.disabled,
    disabledContentColor: Color = Theme.colorScheme.textDisabled,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = Theme.spacing._16,
        vertical = Theme.spacing._8
    ),
    shape: Shape = RoundedCornerShape(Theme.radius.md),
    fontSize: TextUnit = 14.sp,
    lineHeight: TextUnit = 22.sp
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
            .animateContentSize(
                animationSpec = spring(dampingRatio = 0.85f, stiffness = 100f),
                alignment = Alignment.Center
            )
    ) {
        BaseButtonContent(
            text = text,
            trailingIcon = null,
            contentDescription = contentDescription,
            iconSize = iconSize,
            iconStartPadding = iconStartPadding,
            isLoading = isLoading,
            fontSize = fontSize,
            lineHeight = lineHeight,
            contentColor = it,
            loadingColors = listOf(
                Theme.colorScheme.primary.onPrimaryHint,
                Theme.colorScheme.primary.onPrimaryBody,
                Theme.colorScheme.primary.onPrimary
            )
        )
    }
}

@Composable
fun NegativeButton(
    icon: Painter,
    contentDescription: String? = null,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconSize: Dp = 20.dp,
    iconStartPadding: Dp = Theme.spacing._8,
    isEnabled: Boolean = true,
    isLoading: Boolean = false,
    containerColor: Color = Theme.colorScheme.error,
    contentColor: Color = Theme.colorScheme.primary.onPrimary,
    disabledContainerColor: Color = Theme.colorScheme.disabled,
    disabledContentColor: Color = Theme.colorScheme.textDisabled,
    contentPadding: PaddingValues = PaddingValues(
        horizontal = Theme.spacing._16,
        vertical = Theme.spacing._8
    ),
    shape: Shape = RoundedCornerShape(Theme.radius.md),
    fontSize: TextUnit = 14.sp,
    lineHeight: TextUnit = 22.sp
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
            .animateContentSize(
                animationSpec = spring(dampingRatio = 0.85f, stiffness = 100f),
                alignment = Alignment.Center
            )
    ) {
        BaseButtonContent(
            trailingIcon = icon,
            text = null,
            contentDescription = contentDescription,
            iconSize = iconSize,
            iconStartPadding = iconStartPadding,
            isLoading = isLoading,
            fontSize = fontSize,
            lineHeight = lineHeight,
            contentColor = it,
            loadingColors = listOf(
                Theme.colorScheme.primary.onPrimaryHint,
                Theme.colorScheme.primary.onPrimaryBody,
                Theme.colorScheme.primary.onPrimary
            )
        )
    }
}