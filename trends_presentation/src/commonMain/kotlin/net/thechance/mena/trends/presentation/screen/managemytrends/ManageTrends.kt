package net.thechance.mena.trends.presentation.screen.managemytrends

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import mena.trends_presentation.generated.resources.Res
import mena.trends_presentation.generated.resources.ic_arrow_left
import net.thechance.mena.designsystem.presentation.component.appBar.AppBar
import net.thechance.mena.designsystem.presentation.component.icon.MenaIcon
import net.thechance.mena.designsystem.presentation.component.segment.Segment
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import kotlin.math.roundToInt

@Composable
fun ManageTrendsScreen(
    viewModel: ManageTrendsViewModel = koinViewModel(),
    onBack: () -> Unit,
    onTrend: (Int) -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is ManageTrendsUiEffect.NavigateBack -> onBack()
                is ManageTrendsUiEffect.NavigateToTrend -> onTrend(effect.reelId)
            }
        }
    }

    ManageTrendsContent(
        state = state,
        listener = viewModel,
    )
}

@Composable
fun ManageTrendsContent(
    state: ManageTrendsUiState,
    listener: ManageTrendsInteractionListener,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .background(Theme.colorScheme.background.surfaceLow)
            .verticalScroll(rememberScrollState()),
    ) {
        AppBar(
            leadingContent = {
                MenaIcon(
                    modifier = Modifier.clickable { listener.onBackClick() },
                    painter = painterResource(Res.drawable.ic_arrow_left),
                    contentDescription = null
                )
            },
            title = "Manage my trends",
        )

        AsyncImage(
            model = if (state.profileImageUrl.isNotEmpty()) state.profileImageUrl else "placeholder_url",
            contentDescription = "Profile Image",
            modifier = Modifier
                .padding(top = 32.dp)
                .size(100.dp)
                .clip(CircleShape)
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.Crop,
        )
        Text(
            text = state.userName,
            style = Theme.typography.label.medium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 8.dp, bottom = 32.dp)
                .align(Alignment.CenterHorizontally)
        )

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            SegmentSection(
                reels = state.reels,
                onTrendClick = listener::onRealTrendClick,
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
        modifier = Modifier.padding(16.dp)
    ) {
        Segment(
            modifier = Modifier.padding(bottom = 8.dp),
            contentPadding = PaddingValues(top = 16.dp)
        ) {
            item("My Trends") {
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
                                            contentDescription = "Trend Image",
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
            item("Favorite") {
                // TODO: Implement favorites , empty for now
            }
        }
    }
}
//@Preview
//@Composable
//private fun ManageTrendsContentPreview() {
//    ManageTrendsContent(
//        state = ManageTrendsUiState.preview(),
//        listener = object : ManageTrendsInteractionListener {
//            override fun onRealTrendClick(reelId: Int) { }
//            override fun onBackClick() {  }
//        }
//    )
//}
//
