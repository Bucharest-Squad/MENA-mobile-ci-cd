package net.thechance.mena.core_chat.presentation.screen.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import net.thechance.mena.designsystem.presentation.component.text.Text
import net.thechance.mena.designsystem.presentation.theme.theme.Theme

@Composable
fun InfoCard(
    leadingIcon: Painter,
    leadingText: String,
    trailingText: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier.size(28.dp).clip(RoundedCornerShape(Theme.radius.sm))
                .background(Color.White.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = leadingIcon,
                contentDescription = null,
                modifier = Modifier
            )
        }
        Text(
            text = leadingText,
            style = Theme.typography.label.small,
            color = Theme.colorScheme.primary.onPrimaryBody,
            modifier = Modifier.padding(start = Theme.spacing._4)
        )

        Spacer(Modifier.weight(1f))
        Text(
            text = trailingText,
            style = Theme.typography.label.medium,
            color = Theme.colorScheme.primary.onPrimary,
            modifier = Modifier.padding(end = Theme.spacing._12)
        )
    }
}