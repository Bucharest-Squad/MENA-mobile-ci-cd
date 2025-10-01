package net.thechance.mena.faith.presentation.feature.quran.surah

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import net.thechance.mena.faith.presentation.base.FaithScaffold
import net.thechance.mena.faith.presentation.base.ObserveAsEffect
import net.thechance.mena.faith.presentation.component.FaithSnackBar
import net.thechance.mena.faith.presentation.feature.quran.surah.component.AnimatedAyahActionButtons
import net.thechance.mena.faith.presentation.feature.quran.surah.component.BasmalaHeader
import net.thechance.mena.faith.presentation.feature.quran.surah.component.SurahAppBar
import net.thechance.mena.faith.presentation.feature.quran.surah.component.UnifiedChunkText
import net.thechance.mena.faith.presentation.navigation.LocalNavController
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun SurahScreen(
    surahId: Int,
    surahName: String,
    viewModel: SurahViewModel = koinViewModel(parameters = { parametersOf(surahId, surahName) })
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackBarState by viewModel.snackBarState.collectAsStateWithLifecycle()
    val navController = LocalNavController.current

    ObserveAsEffect(viewModel.uiEffect) { effect ->
        when (effect) {
            is SurahScreenEffect.NavigateBack -> navController.navigateUp()
            is SurahScreenEffect.ShareAyah -> {}
        }
    }

    FaithScaffold(
        backgroundColor = Theme.colorScheme.background.surface,
        topBar = {
            SurahAppBar(surahName = uiState.surahName, onBackClick = { viewModel.onBackClick() })
        },
        snackBar = {
            FaithSnackBar(
                message = stringResource(snackBarState.message),
                isVisible = snackBarState.isVisible,
                status = snackBarState.status,
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) {
        Box(Modifier.fillMaxWidth()) {
            AyatOfSurahLazy(
                listener = viewModel,
                state = uiState
            )

            AnimatedAyahActionButtons(
                state = uiState,
                listener = viewModel,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(Theme.spacing._16)
            )
        }
    }
}

@Composable
private fun AyatOfSurahLazy(
    listener: SurahInteractionListener,
    state: SurahScreenState,
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberLazyListState()
    HideAyahActionButtonsOnScroll(lazyListState, state, listener)

    val allAyat = remember(state.ayatOfSurah) { state.ayatOfSurah }
    val ayahChunks = remember(allAyat) { allAyat.chunked(AYAT_PER_PAGE) }

    val preRenderedChunks = remember(ayahChunks) {
        ayahChunks.map { chunk ->
            buildAnnotatedString {
                chunk.forEachIndexed { index, ayah ->
                    append(ayah.content)
                    if (index < chunk.lastIndex) append(" ")
                }
            }
        }
    }

    LazyColumn(
        state = lazyListState,
        modifier = modifier.fillMaxWidth()
    ) {

        if (state.isBasmalaVisible)
            item {
                BasmalaHeader(
                    selectedAyahIndex = state.selectedAyahIndex,
                    onDismissActionButtons = { listener.onDismissActionButtons() }
                )
            }

        items(preRenderedChunks.size) { chunkIndex ->
            val chunkAyat = ayahChunks[chunkIndex]

            UnifiedChunkText(
                chunkAyat = chunkAyat,
                selectedAyahIndex = state.selectedAyahIndex,
                onLongPress = { ayah ->
                    listener.onAyahLongPress(
                        ayahContent = ayah.plainContent,
                        ayahIndex = ayah.number
                    )
                },
                onDismiss = { listener.onDismissActionButtons() }
            )
        }
    }
}

@Composable
private fun HideAyahActionButtonsOnScroll(
    lazyListState: LazyListState,
    state: SurahScreenState,
    listener: SurahInteractionListener
) {
    LaunchedEffect(
        lazyListState,
        state.isAyahActionButtonsVisible
    ) {
        lazyListState.let { listState ->
            snapshotFlow { listState.isScrollInProgress }.collect { isScrolling ->
                if (isScrolling && state.isAyahActionButtonsVisible) {
                    listener.onDismissActionButtons()
                }
            }
        }
    }
}

private const val AYAT_PER_PAGE = 70
