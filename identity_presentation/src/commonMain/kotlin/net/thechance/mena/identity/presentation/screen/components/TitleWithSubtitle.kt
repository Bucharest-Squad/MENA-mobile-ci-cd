package net.thechance.mena.identity.presentation.screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.thechance.mena.designsystem.presentation.component.text.MenaText
import net.thechance.mena.designsystem.presentation.theme.theme.Theme

@Composable
fun TitleWithSubtitle(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 12.dp,bottom = 24.dp)
    ) {
        MenaText(
            text = title,
            style = Theme.typography.title.medium,
            color = Theme.colorScheme.shadePrimary,
        )
        MenaText(
            text = subtitle,
            style = Theme.typography.body.small,
            color = Theme.colorScheme.shadeSecondary,
        )

    }
}