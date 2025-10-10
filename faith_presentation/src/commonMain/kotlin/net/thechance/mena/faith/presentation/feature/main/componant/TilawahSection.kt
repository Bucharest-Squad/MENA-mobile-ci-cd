package net.thechance.mena.faith.presentation.feature.main.componant

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import mena.faith_presentation.generated.resources.Res
import mena.faith_presentation.generated.resources.ayah_number
import mena.faith_presentation.generated.resources.continue_tilawah
import mena.faith_presentation.generated.resources.ic_arrow_right
import mena.faith_presentation.generated.resources.ic_column_mosque
import mena.faith_presentation.generated.resources.ic_kaaba
import mena.faith_presentation.generated.resources.ic_mosque
import mena.faith_presentation.generated.resources.ic_quran
import mena.faith_presentation.generated.resources.ic_quran_play
import mena.faith_presentation.generated.resources.mosque_image_description
import mena.faith_presentation.generated.resources.navigate_next
import mena.faith_presentation.generated.resources.nearby_mosques
import mena.faith_presentation.generated.resources.qiblah_direction
import mena.faith_presentation.generated.resources.quran_kareem
import mena.faith_presentation.generated.resources.surah_al_fatiha
import mena.faith_presentation.generated.resources.tilawah
import net.thechance.mena.designsystem.presentation.component.icon.Icon
import net.thechance.mena.designsystem.presentation.component.text.Text
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import net.thechance.mena.faith.presentation.feature.main.TilawahUiState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TilawahSection(
    tilawahUiState: TilawahUiState?,
    onContinueTilawahClick: () -> Unit,
    onQuranClick: () -> Unit,
    onQiblahClick: () -> Unit,
    onMosquesClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Theme.spacing._16),
        verticalArrangement = Arrangement.spacedBy(Theme.spacing._12)
    ) {
        TilawahHeader(tilawahUiState, onContinueTilawahClick)

        TilawahFeatureCards(
            onQuranClick = onQuranClick,
            onQiblahClick = onQiblahClick,
            onMosquesClick = onMosquesClick
        )
    }
}

@Composable
private fun TilawahHeader(
    tilawahUiState: TilawahUiState?,
    onContinueTilawahClick: () -> Unit
) {
    Text(
        text = stringResource(Res.string.tilawah),
        style = Theme.typography.title.medium,
        color = Theme.colorScheme.shadePrimary,
        modifier = Modifier.padding(bottom = Theme.spacing._8)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(Theme.radius.md))
            .clickable { onContinueTilawahClick() }
            .background(Theme.colorScheme.background.surfaceLow)

    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding( Theme.spacing._8),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(Theme.radius.md))
                    .background(Theme.colorScheme.background.surfaceHigh),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_quran_play),
                    contentDescription = stringResource(Res.string.quran_kareem),
                    tint = Theme.colorScheme.primary.primary,
                    modifier = Modifier.size(22.dp)
                )
            }
            Column(
                modifier = Modifier.padding(start = Theme.spacing._8),
                verticalArrangement = Arrangement.spacedBy(Theme.spacing._4)
            ) {
                Text(
                    text = tilawahUiState?.surahName ?: stringResource(Res.string.surah_al_fatiha)
                    ,
                    style = Theme.typography.label.medium,
                    color = Theme.colorScheme.shadePrimary
                )
                Text(
                    text = tilawahUiState?.ayahNumber ?: stringResource(Res.string.ayah_number),
                    style = Theme.typography.label.small,
                    color = Theme.colorScheme.shadeSecondary
                )
            }
            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = stringResource(Res.string.continue_tilawah),
                style = Theme.typography.label.medium,
                color = Theme.colorScheme.primary.primary,

                )
            Icon(
                painter = painterResource(Res.drawable.ic_arrow_right),
                contentDescription = stringResource(Res.string.navigate_next),
                tint = Theme.colorScheme.primary.primary,
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

@Composable
private fun TilawahFeatureCards(
    onQuranClick: () -> Unit,
    onQiblahClick: () -> Unit,
    onMosquesClick: () -> Unit
) {
    val featureCards = listOf(
        FeatureItem(
            stringResource(Res.string.quran_kareem),
            painterResource(Res.drawable.ic_quran),
            onQuranClick
        ),
        FeatureItem(
            stringResource(Res.string.qiblah_direction),
            painterResource(Res.drawable.ic_kaaba),
            onQiblahClick
        ),
        FeatureItem(
            stringResource(Res.string.nearby_mosques),
            painterResource(Res.drawable.ic_mosque),
            onMosquesClick
        )
    )

    featureCards.chunked(2).forEach { rowCards ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Theme.spacing._8),
            horizontalArrangement = Arrangement.spacedBy(Theme.spacing._8)
        ) {
            rowCards.forEach { card ->
                SmallFeatureCard(
                    icon = card.icon,
                    title = card.title,
                    onClick = card.onClick,
                    modifier = Modifier.weight(1f)
                )
            }
            repeat(2 - rowCards.size) {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}


@Composable
private fun SmallFeatureCard(
    icon: Painter,
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(Theme.radius.md))
            .background(Theme.colorScheme.background.surfaceLow)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(Res.drawable.ic_column_mosque),
            stringResource(Res.string.mosque_image_description),
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight()
                .width(100.dp),
            contentScale = ContentScale.Fit,
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = Theme.spacing._12),
            verticalArrangement = Arrangement.Center,
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.Start)
                    .clip(RoundedCornerShape(Theme.radius.md))
                    .background(Theme.colorScheme.background.surfaceHigh),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = icon,
                    contentDescription = title,
                    tint = Theme.colorScheme.primary.primary,
                    modifier = Modifier.size(28.dp)
                        .padding(bottom = Theme.spacing._4)
                )
            }
            Text(
                text = title,
                style = Theme.typography.title.small,
                color = Theme.colorScheme.shadePrimary,
            )
        }
    }
}

private data class FeatureItem(
    val title: String,
    val icon: Painter,
    val onClick: () -> Unit
)
