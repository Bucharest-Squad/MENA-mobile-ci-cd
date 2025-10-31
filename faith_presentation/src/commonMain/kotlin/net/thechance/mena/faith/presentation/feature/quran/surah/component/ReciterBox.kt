package net.thechance.mena.faith.presentation.feature.quran.surah.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import net.thechance.mena.designsystem.presentation.component.text.Text
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import net.thechance.mena.faith.presentation.designSystem.theme.QuranTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ReciterBox(reciterName: String) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .clip(
                RoundedCornerShape(
                    topStart = Theme.radius.sm,
                    topEnd = Theme.radius.sm
                )
            )
            .background(Theme.colorScheme.background.surfaceLow)
    ) {
        Text(
            text = reciterName,
            style = Theme.typography.label.medium,
            color = Theme.colorScheme.shadePrimary,
            modifier = Modifier.padding(
                vertical = Theme.spacing._4,
                horizontal = Theme.spacing._16
            )
        )
    }
}

@Preview
@Composable
private fun ReciterPreview() {
    QuranTheme {
        ReciterBox(reciterName = "Maytham Al-Tammar")
    }
}