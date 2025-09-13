package net.thechance.mena.trends.presentation.shared.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import net.thechance.mena.designsystem.presentation.component.button.radioButton.RadioButton
import net.thechance.mena.designsystem.presentation.component.text.MenaText
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import net.thechance.mena.trends.presentation.shared.component.modifier.noRippleClickable
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun CategoryItem(
    text: String,
    emoji: String,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val textColor by animateColorAsState(
        targetValue = if (isSelected) Theme.colorScheme.shadePrimary else Theme.colorScheme.shadeSecondary
    )
    Row(
        horizontalArrangement = Arrangement.spacedBy(Theme.spacing._8),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(RoundedCornerShape(Theme.radius.full))
            .background(color = Theme.colorScheme.primary.onPrimary)
            .noRippleClickable { onClick() }
            .padding(10.dp)
    ) {
        CategoryNameAndEmoji(
            emoji = emoji,
            text = text,
            textColor = textColor
        )
        RadioButton(
            isSelected = isSelected,
            onClick = onClick,
        )
    }
}

@Composable
private fun CategoryNameAndEmoji(
    emoji: String,
    text: String,
    textColor: Color
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(Theme.spacing._4),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        MenaText(
            text = emoji,
            style = Theme.typography.label.extraSmall
        )
        MenaText(
            text = text,
            color = textColor,
            style = Theme.typography.label.extraSmall // TODO: Should be Label/XSmall
        )
    }
}

@Preview
@Composable
private fun CategoryItemPreview() {
    var isSelected by remember { mutableStateOf(false) }
    CategoryItem(
        text = "Hello",
        emoji = "\uD83D\uDE00",
        isSelected = isSelected,
        onClick = {
            isSelected = isSelected.not()
        }
    )
}