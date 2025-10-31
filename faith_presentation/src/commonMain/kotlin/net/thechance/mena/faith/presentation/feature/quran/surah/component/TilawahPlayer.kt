package net.thechance.mena.faith.presentation.feature.quran.surah.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import net.thechance.mena.faith.presentation.designSystem.theme.QuranTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun TilawahPlayer(
    reciterName: String,
    surahName: String,
    ayahNumber: Int,
    isPlaying: Boolean,
    onReciterClick: () -> Unit,
    onPreviousClick: () -> Unit,
    onPlayPauseClick: () -> Unit,
    onNextClick: () -> Unit,
    onRepeatClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Theme.spacing._16),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ReciterBox(reciterName)
        TilawahButtons(
            surahName = surahName,
            ayahNumber = ayahNumber,
            isPlaying = isPlaying,
            onReciterClick = onReciterClick,
            onPreviousClick = onPreviousClick,
            onPlayPauseClick = onPlayPauseClick,
            onNextClick = onNextClick,
            onRepeatClick = onRepeatClick
        )

    }
}

@Preview
@Composable
fun ReciterBoxPreview() {
    QuranTheme {
        TilawahPlayer(
            reciterName = "Reciter Name",
            surahName = "Surah Name",
            ayahNumber = 1,
            isPlaying = true,
            onReciterClick = {},
            onPreviousClick = {},
            onPlayPauseClick = {},
            onNextClick = {},
            onRepeatClick = {}
        )
    }
}
