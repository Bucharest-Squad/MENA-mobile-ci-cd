package net.thechance.mena.faith.presentation.feature.main.componant

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import mena.faith_presentation.generated.resources.continue_tilawah
import mena.faith_presentation.generated.resources.ic_arrow_right
import mena.faith_presentation.generated.resources.ic_column_mosque
import mena.faith_presentation.generated.resources.ic_kaaba
import mena.faith_presentation.generated.resources.ic_mosque
import mena.faith_presentation.generated.resources.ic_quran
import mena.faith_presentation.generated.resources.ic_quran_play
import mena.faith_presentation.generated.resources.nearby_mosques
import mena.faith_presentation.generated.resources.qiblah_direction
import mena.faith_presentation.generated.resources.quran_kareem
import mena.faith_presentation.generated.resources.tilawah
import net.thechance.mena.designsystem.presentation.component.icon.Icon
import net.thechance.mena.designsystem.presentation.component.image.Image
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

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = stringResource(Res.string.tilawah),
            style = Theme.typography.title.medium,
            color = Theme.colorScheme.shadePrimary
        )

        Spacer(modifier = Modifier.height(14.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(Theme.radius.md))
                .clickable { onContinueTilawahClick() }
                .background(Theme.colorScheme.background.surfaceLow)
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(Theme.radius.md))
                            .background(Theme.colorScheme.background.surfaceHigh),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_quran_play),
                            contentDescription = null,
                            tint = Theme.colorScheme.primary.primary,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Column(
                        modifier = Modifier.padding(start = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "Al Baqraa",
                            style = Theme.typography.label.medium,
                            color = Theme.colorScheme.shadePrimary
                        )
                        Text(
                            text = "Aya No 178",
                            style = Theme.typography.label.small,
                            color = Theme.colorScheme.shadeSecondary
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = stringResource(Res.string.continue_tilawah),
                        style = Theme.typography.label.medium,
                        color = Theme.colorScheme.primary.primary
                    )
                    Icon(
                        painter = painterResource(Res.drawable.ic_arrow_right),
                        contentDescription = null,
                        tint = Theme.colorScheme.primary.primary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        featureCards.chunked(2).forEach { rowCards ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
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
            if (rowCards != featureCards.chunked(2).last()) {
                Spacer(modifier = Modifier.height(8.dp))
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
            .aspectRatio(1.4f)
            .clip(RoundedCornerShape(Theme.radius.md))
            .background(Theme.colorScheme.background.surfaceLow)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(Res.drawable.ic_column_mosque),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .fillMaxHeight()
                .width(100.dp),
            contentScale = ContentScale.Fit,
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 12.dp),
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
                    contentDescription = null,
                    tint = Theme.colorScheme.primary.primary,
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
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
