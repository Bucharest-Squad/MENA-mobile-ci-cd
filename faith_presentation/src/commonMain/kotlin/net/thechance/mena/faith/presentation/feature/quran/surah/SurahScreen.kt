package net.thechance.mena.faith.presentation.feature.quran.surah

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.buildAnnotatedString
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import net.thechance.mena.designsystem.presentation.component.scaffold.Scaffold
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import net.thechance.mena.faith.domain.entity.Ayah
import net.thechance.mena.faith.presentation.base.ObserveAsEffect
import net.thechance.mena.faith.presentation.base.snackbar.SnackBarState
import net.thechance.mena.faith.presentation.components.FaithSnackBar
import net.thechance.mena.faith.presentation.designSystem.theme.QuranTheme
import net.thechance.mena.faith.presentation.feature.quran.surah.component.AnimatedAyahActionButtons
import net.thechance.mena.faith.presentation.feature.quran.surah.component.BasmalaHeader
import net.thechance.mena.faith.presentation.feature.quran.surah.component.SurahAppBar
import net.thechance.mena.faith.presentation.feature.quran.surah.component.UnifiedChunkText
import net.thechance.mena.faith.presentation.navigation.LocalNavController
import net.thechance.mena.faith.presentation.navigation.Route
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SurahScreen(
    viewModel: SurahViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackBarState by viewModel.snackBarState.collectAsStateWithLifecycle()
    val navController = LocalNavController.current

    ObserveAsEffect(viewModel.uiEffect) { effect ->
        when (effect) {
            is SurahScreenEffect.NavigateBack -> navController.navigateUp()
            //TODO("Navigate to chat screen")
            is SurahScreenEffect.ShareAyah -> {}
            is SurahScreenEffect.NavigateToSearchScreen -> {
                navController.navigate(
                    Route.SearchRoute(
                        effect.surahId,
                        effect.surahName
                    )
                )
            }
        }
    }

    Content(
        state = uiState,
        listener = viewModel,
        snackBarState = snackBarState
    )
}

@Composable
private fun Content(
    state: SurahUiState,
    listener: SurahInteractionListener,
    snackBarState: SnackBarState,
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {
            SurahAppBar(
                surahName = state.surahName,
                onBackClick = listener::onBackClick,
                onSearchClick = listener::onSearchClick
            )
        },
        snakeBar = {
            FaithSnackBar(
                message = snackBarState.message,
                status = snackBarState.status,
                isVisible = snackBarState.isVisible,
                modifier = modifier.fillMaxWidth()
            )
        }
    ) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Theme.colorScheme.background.surface)
        ) {
            AyatOfSurah(
                listener = listener,
                state = state
            )

            AnimatedAyahActionButtons(
                state = state,
                listener = listener,
                modifier = Modifier.fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(Theme.spacing._16)
            )
        }
    }
}

@Composable
private fun AyatOfSurah(
    listener: SurahInteractionListener,
    state: SurahUiState,
    modifier: Modifier = Modifier
) {
    val lazyListState = rememberLazyListState()
    val ayahChunks = remember(state.ayatOfSurah) {
        state.ayatOfSurah.chunked(AYAT_PER_PAGE)
    }
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
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
    var chunkAyat by remember { mutableStateOf(listOf<Ayah>()) }
    TrackFirstVisibleAyah(
        lazyListState = lazyListState,
        ayahChunks = ayahChunks,
        isBasmalaVisible = state.isBasmalaVisible,
        listener = listener,
        textLayoutResult = textLayoutResult
    )
    HandleInitialScroll(
        initialAyahToScroll = state.initialAyahToScroll,
        selectedAyahIndex = state.selectedAyahNumber,
        isBasmalaVisible = state.isBasmalaVisible,
        lazyListState = lazyListState,
        highlightAyah = listener::highlightAyah,
        chunkAyat = chunkAyat,
        textLayoutResult = textLayoutResult,
        onInitialScrollDone = listener::onInitialAyahScrolled
    )
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        state = lazyListState
    ) {
        if (state.isBasmalaVisible) {
            item {
                BasmalaHeader(
                    selectedAyahIndex = state.selectedAyahNumber,
                    onDismissActionButtons = listener::onDismissActionButtons
                )
            }
        }

        items(preRenderedChunks.size) { chunkIndex ->
            chunkAyat = ayahChunks[chunkIndex]
            UnifiedChunkText(
                chunkAyat = chunkAyat,
                selectedAyahIndex = state.selectedAyahNumber,
                onLongPress = { ayah ->
                    listener.onAyahLongPress(
                        ayahContent = ayah.plainContent,
                        ayahIndex = ayah.number
                    )
                },
                onDismiss = { listener.onDismissActionButtons() },
                textLayoutResult = textLayoutResult,
                onTextLayoutResultChange = { textLayoutResult = it }
            )
        }
    }
    HideAyahActionButtonsOnScroll(lazyListState, state, listener)
    LaunchedEffect(state.isBasmalaVisible) {
        if (state.isBasmalaVisible) {
            listener.onFirstVisibleAyahChanged(state.ayatOfSurah.firstOrNull()?.number ?: 1)
        }
    }
}

@Composable
private fun HideAyahActionButtonsOnScroll(
    lazyListState: LazyListState,
    state: SurahUiState,
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

@Composable
private fun HandleInitialScroll(
    initialAyahToScroll: Int?,
    selectedAyahIndex: Int?,
    isBasmalaVisible: Boolean,
    lazyListState: LazyListState,
    chunkAyat: List<Ayah>,
    textLayoutResult: TextLayoutResult?,
    highlightAyah: (Int) -> Unit,
    onInitialScrollDone: () -> Unit
) {
    val navController = LocalNavController.current
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(
        navController.currentBackStackEntry?.savedStateHandle?.get<Int>("ayahNumber"),
        selectedAyahIndex
    ) {
        navController.currentBackStackEntry?.savedStateHandle?.get<Int>("ayahNumber")?.let {
            highlightAyah(it)
        }
        if (selectedAyahIndex == null) {
            navController.currentBackStackEntry?.savedStateHandle?.remove<Int>("ayahNumber")
        }
    }
    LaunchedEffect(initialAyahToScroll) {
        initialAyahToScroll?.let { ayahNumber ->
            val chunkIndex = (ayahNumber - 1) / AYAT_PER_PAGE
            val scrollIndex = if (isBasmalaVisible) chunkIndex + 1 else chunkIndex
            lazyListState.scrollToItem(scrollIndex)
        }
    }
    LaunchedEffect(textLayoutResult, initialAyahToScroll) {
        if (textLayoutResult == null || initialAyahToScroll == null) return@LaunchedEffect
        val targetAyah = chunkAyat.find { it.number == initialAyahToScroll }
        if (targetAyah != null) {
            val ayahIndexInChunk = chunkAyat.indexOf(targetAyah)
            val startCharOffset = chunkAyat.take(ayahIndexInChunk).sumOf { it.content.length + 1 }
            val lineIndex = textLayoutResult.getLineForOffset(startCharOffset)
            val lineTopOffset = textLayoutResult.getLineTop(lineIndex).toInt()
            val chunkIndex = ((targetAyah.number - 1) / AYAT_PER_PAGE)
            val scrollIndex = if (isBasmalaVisible) chunkIndex + 1 else chunkIndex
            coroutineScope.launch {
                lazyListState.animateScrollToItem(
                    index = scrollIndex,
                    scrollOffset = lineTopOffset
                )
            }
            onInitialScrollDone()
        }
    }
}

@Composable
private fun TrackFirstVisibleAyah(
    lazyListState: LazyListState,
    ayahChunks: List<List<Ayah>>,
    isBasmalaVisible: Boolean,
    listener: SurahInteractionListener,
    textLayoutResult: TextLayoutResult?
) {
    var lastReportedAyahNumber by remember { mutableStateOf<Int?>(null) }

    LaunchedEffect(lazyListState, textLayoutResult) {
        snapshotFlow {
            lazyListState.firstVisibleItemIndex to lazyListState.firstVisibleItemScrollOffset
        }
            .collect { (firstVisibleIndex, scrollOffset) ->
                val chunkIndex = if (isBasmalaVisible) firstVisibleIndex - 1
                else firstVisibleIndex

                if (chunkIndex >= 0 && chunkIndex < ayahChunks.size && textLayoutResult != null) {
                    val chunk = ayahChunks[chunkIndex]

                    val firstVisibleAyahNumber = getFirstVisibleAyahInChunk(
                        chunk = chunk,
                        textLayoutResult = textLayoutResult,
                        scrollOffset = scrollOffset
                    )

                    if (firstVisibleAyahNumber != null && firstVisibleAyahNumber != lastReportedAyahNumber) {
                        lastReportedAyahNumber = firstVisibleAyahNumber
                        listener.onFirstVisibleAyahChanged(firstVisibleAyahNumber)
                    }
                }
            }
    }
}

private fun getFirstVisibleAyahInChunk(
    chunk: List<Ayah>,
    textLayoutResult: TextLayoutResult,
    scrollOffset: Int
): Int? {
    var currentCharPosition = 0

    chunk.forEach { ayah ->
        val ayahLength = ayah.content.length
        val lineForOffset = textLayoutResult.getLineForOffset(currentCharPosition)
        val lineTop = textLayoutResult.getLineTop(lineForOffset)

        if (lineTop.toInt() >= scrollOffset) return ayah.number

        currentCharPosition += ayahLength + 1
    }

    return chunk.firstOrNull()?.number
}

private const val AYAT_PER_PAGE = 70

@Composable
@Preview
private fun SurahScreenPreview() {
    QuranTheme {
        CompositionLocalProvider(LocalNavController provides rememberNavController()) {
            Content(
                state = SurahUiState(
                    surahId = 1,
                    surahName = "Al-Fatiha",
                    ayatOfSurah = listOf(
                        Ayah(
                            number = 1,
                            surahId = 1,
                            content = "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ",
                            plainContent = "بسم الله الرحمن الرحيم"
                        ),
                        Ayah(
                            number = 2,
                            surahId = 1,
                            content = "الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ",
                            plainContent = "الحمد لله رب العالمين"
                        ),
                        Ayah(
                            number = 3,
                            surahId = 1,
                            content = "الرَّحْمَٰنِ الرَّحِيمِ",
                            plainContent = "الرحمن الرحيم"
                        ),
                        Ayah(
                            number = 4,
                            surahId = 1,
                            content = "مَالِكِ يَوْمِ الدِّينِ",
                            plainContent = "مالك يوم الدين"
                        ),
                        Ayah(
                            number = 5,
                            surahId = 1,
                            content = "إِيَّاكَ نَعْبُدُ وَإِيَّاكَ نَسْتَعِينُ",
                            plainContent = "إياك نعبد وإياك نستعين"
                        ),
                        Ayah(
                            number = 6,
                            surahId = 1,
                            content = "اهْدِنَا الصِّرَاطَ الْمُسْتَقِيمَ",
                            plainContent = "اهدنا الصراط المستقيم"
                        ),
                        Ayah(
                            number = 7,
                            surahId = 1,
                            content = "صِرَاطَ الَّذِينَ أَنْعَمْتَ عَلَيْهِمْ غَيْرِ الْمَغْضُوبِ عَلَيْهِمْ وَلَا الضَّالِّينَ",
                            plainContent = "صراط الذين أنعمت عليهم غير المغضوب عليهم ولا الضالين"
                        )
                    ),
                    isBasmalaVisible = true,
                    selectedAyahNumber = null,
                    isAyahActionButtonsVisible = false,
                    initialAyahToScroll = null
                ),
                listener = object : SurahInteractionListener {
                    override fun onBackClick() {}
                    override fun onDismissActionButtons() {}
                    override fun onShareClick(ayahContent: String) {}
                    override fun onBookmarkClick(ayahNumber: Int) {}
                    override fun onAyahLongPress(ayahContent: String, ayahIndex: Int) {}
                    override fun onSearchClick() {}
                    override fun onCopyClick(ayahContent: String) {}
                    override fun onInitialAyahScrolled() {}
                    override fun highlightAyah(ayahNumber: Int) {}
                    override fun onFirstVisibleAyahChanged(ayahNumber: Int) {}

                },
                snackBarState = SnackBarState()
            )
        }
    }
}
