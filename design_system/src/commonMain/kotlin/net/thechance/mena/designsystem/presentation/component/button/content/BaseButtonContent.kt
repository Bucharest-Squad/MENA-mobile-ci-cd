package net.thechance.mena.designsystem.presentation.component.button.content

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import net.thechance.mena.designsystem.presentation.component.icon.MenaIcon
import net.thechance.mena.designsystem.presentation.component.indicator.DotsProgressIndicator
import net.thechance.mena.designsystem.presentation.component.text.MenaText
import net.thechance.mena.designsystem.presentation.theme.theme.Theme

@Composable
internal fun BaseButtonContent(
    text: String?,
    trailingIcon: Painter?,
    contentDescription: String? = null,
    iconSize: Dp,
    loadingColors: List<Color>,
    iconStartPadding: Dp,
    isLoading: Boolean = false,
    fontSize: TextUnit = TextUnit.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    contentColor: Color
) {
    if (isLoading) {
        DotsProgressIndicator(colors = loadingColors)
    } else {
        text?.let {
            MenaText(
                text = text,
                fontSize = fontSize,
                color = contentColor,
                lineHeight = lineHeight,
                letterSpacing = letterSpacing,
                overflow = overflow,
                style = Theme.typography.label.medium,
            )
        }

        trailingIcon?.let {
            MenaIcon(
                painter = trailingIcon,
                tint = contentColor,
                contentDescription = contentDescription,
                modifier = Modifier
                    .padding(start = iconStartPadding)
                    .size(iconSize)
            )
        }
    }
}