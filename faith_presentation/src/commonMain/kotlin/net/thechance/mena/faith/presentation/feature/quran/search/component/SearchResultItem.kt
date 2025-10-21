package net.thechance.mena.faith.presentation.feature.quran.search.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import net.thechance.mena.faith.presentation.feature.quran.search.SearchUiState

@Composable
fun SearchResultItem(
    result: SearchUiState.SearchResult,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    SearchResultCard(
        surahName = result.surahName,
        ayaNumber = result.number,
        ayaText = result.content,
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Theme.radius.md))
            .background(Theme.colorScheme.background.surfaceLow)
            .clickable(onClick = onClick)
            .padding(Theme.spacing._12)
    )
}