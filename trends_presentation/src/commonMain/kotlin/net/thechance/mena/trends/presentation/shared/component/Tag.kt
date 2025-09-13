package net.thechance.mena.trends.presentation.shared.component

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun Tag(
    text: String,
    emoji: String,
    selected: Boolean,
    modifier: Modifier = Modifier,
    onClick: (Boolean) -> Unit = {}
) {
    val textColor by animateColorAsState(
        targetValue = if (selected) Theme.colorScheme.shadePrimary else Theme.colorScheme.shadeSecondary
    )
    Row(
        horizontalArrangement = Arrangement.spacedBy(Theme.spacing._8),
        modifier = modifier
            .clip(RoundedCornerShape(Theme.radius.full))
            .background(color = Theme.colorScheme.primary.onPrimary)
            .clickable {
                onClick(!selected)
            }
            .padding(10.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(Theme.spacing._4)
        ) {
            Text(
                text = emoji
            )
            Text(
                text = text,
                color = textColor
            )
        }
        net.thechance.mena.designsystem.presentation.component.button.radioButton.RadioButton(
            isSelected = selected,
            onClick = {
                onClick(!selected)
            },
        )
    }
}

@Preview
@Composable
fun TagPreview() {
    var selected by remember { mutableStateOf(false) }
    Tag(
        text = "Hello",
        emoji = "\uD83D\uDE00",
        selected = selected,
        onClick = {
            selected = it
        }
    )
}