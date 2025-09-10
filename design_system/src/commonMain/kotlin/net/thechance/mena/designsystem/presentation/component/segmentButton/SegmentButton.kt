package net.thechance.mena.designsystem.presentation.component.segmentButton

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import net.thechance.mena.designsystem.presentation.component.text.MenaText
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SegmentButton(
    selectedOption: String,
    options: List<String>,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth()
            .clip(RoundedCornerShape(Theme.radius.md))
            .background(Theme.colorScheme.background.surfaceHigh)

    ) {
        options.forEach {
            SegmentOption(it, selectedOption == it)
        }
    }
}

@Composable
private fun RowScope.SegmentOption(option: String, isSelected: Boolean) {
    Box(
        modifier = Modifier
            .height(40.dp)
            .weight(1f)
            .padding(4.dp)
            .shadow(
                if (isSelected) 20.dp else 0.dp,
                clip = false,
                shape = RoundedCornerShape(Theme.radius.md),
                spotColor = Color(0x00000003),
            )
            .clip(RoundedCornerShape(Theme.radius.md))
            .background(
                if (isSelected)
                    Theme.colorScheme.background.surfaceLow
                else
                    Theme.colorScheme.background.surfaceHigh
            ),
        contentAlignment = Alignment.Center,
    ) {
        MenaText(
            text = option,
            style = Theme.typography.label.medium,
            color = if (isSelected) Theme.colorScheme.shadePrimary else Theme.colorScheme.shadeSecondary,
        )
    }
}

@Preview
@Composable
private fun SegmentButtonPreview() {
    val list = listOf(
        "Option1",
        "Option2",
        "Option3",
    )

    val selectedOption = list[0]
    Box(modifier = Modifier.padding(16.dp)) {
        SegmentButton(options = list, selectedOption = selectedOption)
    }
}