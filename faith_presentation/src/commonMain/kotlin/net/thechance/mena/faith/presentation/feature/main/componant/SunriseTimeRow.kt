package net.thechance.mena.faith.presentation.feature.main.componant


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import net.thechance.mena.designsystem.presentation.component.icon.Icon
import net.thechance.mena.designsystem.presentation.component.text.Text
import net.thechance.mena.designsystem.presentation.theme.theme.Theme


@Composable
fun SunriseTimeRow(
    icon: Painter,
    title: String,
    time: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = Theme.colorScheme.primary.primary,
                modifier = Modifier.size(20.dp)
            )

            Text(
                text = title,
                color = Theme.colorScheme.shadePrimary,
                style = Theme.typography.label.medium
            )
        }

        Text(
            text = time,
            color = Theme.colorScheme.shadePrimary,
            style = Theme.typography.label.medium
        )
    }
}