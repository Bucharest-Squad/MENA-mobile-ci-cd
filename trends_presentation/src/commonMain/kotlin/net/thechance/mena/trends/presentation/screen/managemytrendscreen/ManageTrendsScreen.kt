package net.thechance.mena.trends.presentation.screen.managemytrendscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil3.compose.AsyncImage
import mena.trends_presentation.generated.resources.Res
import mena.trends_presentation.generated.resources.ic_arrow_left
import mena.trends_presentation.generated.resources.manage_trends_title
import mena.trends_presentation.generated.resources.profile_image_desc
import mena.trends_presentation.generated.resources.trend_image_desc
import net.thechance.mena.designsystem.presentation.component.appBar.AppBar
import net.thechance.mena.designsystem.presentation.component.icon.MenaIcon
import net.thechance.mena.designsystem.presentation.component.segment.Segment
import net.thechance.mena.designsystem.presentation.component.text.MenaText
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import kotlin.math.roundToInt

@Composable
fun ManageTrendsScreen(
    viewModel: ManageTrendsViewModel = koinViewModel(),
    navController: NavHostController
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ManageTrendsUiEffect.NavigateBack -> navController.popBackStack()
                is ManageTrendsUiEffect.NavigateToTrend -> navController.navigate("${effect.reelId}")
            }
        }
    }

    ManageTrendsScreenContent(
        state = state,
        listener = viewModel,
    )
}

@Composable
private fun ManageTrendsScreenContent(
    state: ManageTrendsScreenState,
    listener: ManageTrendsInteractionListener,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colorScheme.background.surface),
    ) {
        AppBar(
            leadingContent = {
                MenaIcon(
                    modifier = Modifier.clickable { listener.onBackClick() },
                    painter = painterResource(Res.drawable.ic_arrow_left),
                    contentDescription = "Back arrow"
                )
            },
            title = stringResource(Res.string.manage_trends_title),
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            AsyncImage(
                model = state.profileImageUrl,
                contentDescription = stringResource(Res.string.profile_image_desc),
                modifier = Modifier
                    .padding(top = 32.dp)
                    .size(100.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterHorizontally),
                contentScale = ContentScale.Crop,
            )
            MenaText(
                text = state.userName,
                style = Theme.typography.label.medium,
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 32.dp)
                    .align(Alignment.CenterHorizontally)
            )
            SegmentSection(
                reels = state.reels,
                onTrendClick = listener::onReelItemClick,
            )
        }
    }
}

@Composable
private fun SegmentSection(
    reels: List<ReelUiState>,
    onTrendClick: (Int) -> Unit,
) {
    Column(
        modifier = Modifier.padding(Theme.spacing._16)
    ) {
        Segment(
            modifier = Modifier.padding(bottom = Theme.spacing._8),
            contentPadding = PaddingValues(top = Theme.spacing._16)
        ) {
            item( "My Trends") {
                BoxWithConstraints(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    val itemWidth = 106.dp
                    val numColumns = (maxWidth / itemWidth).roundToInt().coerceAtLeast(1)
                    val chunks = reels.chunked(numColumns)
                    Column(
                        modifier = Modifier,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        chunks.forEach { rowItems ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                rowItems.forEach { item ->
                                    Box(
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(164.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                            .clickable { onTrendClick(item.id) }
                                            .background(Color.Gray)
                                    ) {
                                        AsyncImage(
                                            model = item.thumbnailUrl,
                                            contentDescription =  stringResource(Res.string.trend_image_desc),
                                            modifier = Modifier.fillMaxSize(),
                                            contentScale = ContentScale.Crop
                                        )
                                    }
                                }
                                repeat(numColumns - rowItems.size) {
                                    Spacer(
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(164.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
            item("Favorite" ) {
                // TODO: Implement favorites , empty for now
            }
        }
    }
}