package net.thechance.mena.faith.presentation.feature.quran.reciter.downloadedSurahRecitersScreen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import mena.faith_presentation.generated.resources.search_reciter
import net.thechance.mena.designsystem.presentation.component.scaffold.Scaffold
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import net.thechance.mena.faith.presentation.base.ObserveAsEffect
import net.thechance.mena.faith.presentation.components.ReciterItem
import net.thechance.mena.faith.presentation.designSystem.theme.QuranTheme
import net.thechance.mena.faith.presentation.feature.quran.reciter.SearchReciter
import net.thechance.mena.faith.presentation.feature.quran.search.ayah.component.SearchEmptyState
import net.thechance.mena.faith.presentation.navigation.LocalNavController
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.ExperimentalTime

@Composable
fun DownloadedSurahRecitersScreen(
    viewModel: DownloadedSurahRecitersViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navController = LocalNavController.current

    ObserveAsEffect(viewModel.uiEffect) { effect ->
        when (effect) {
            DownloadedSurahRecitersScreenEffect.NavigateBack -> navController.navigateUp()
            DownloadedSurahRecitersScreenEffect.NavigateToSearch -> {}
        }
    }

    Content(uiState = uiState, listener = viewModel)
}

@OptIn(ExperimentalTime::class)
@Composable
private fun Content(
    uiState: DownloadedSurahRecitersUiState,
    listener: DownloadedSurahRecitersListener,
) {
    Scaffold(
        topBar = {
            SearchReciter(
                query = uiState.query,
                hint = uiState.queryHint,
                onQueryChange = listener::onQueryChange,
                clearQuery = listener::onClearQueryClick,
                onBackClick = listener::onBackClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Theme.spacing._16, vertical = Theme.spacing._4)
            )
        }
    ) {

        LazyColumn(
            contentPadding = PaddingValues(bottom = Theme.spacing._16),
        ) {

            if (uiState.query.isNotBlank() && uiState.reciters.isEmpty()) {
                item {
                    SearchEmptyState(
                        subtitle = mena.faith_presentation.generated.resources.Res.string.search_reciter,
                        isStartState = false,
                        isResultsState = true,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
            }

            items(uiState.reciters) { reciter ->
                ReciterItem(
                    reciterId = reciter.id,
                    reciter = reciter.name,
                    recitingType = reciter.recitingType,
                    isDownloaded = reciter.isDownloaded,
                    onSelect = { listener.onSelectReciterClick(reciter.id) },
                    onDownloadClick = { listener.onDownloadClick(reciter.id) },
                    isSelectReciter = reciter.id == uiState.selectedReciterId,
                    isSwipeable = uiState.isSwipeable,
                )
            }
        }
    }
}



@Preview
@Composable
private fun Preview() {
    MenaTheme {
        QuranTheme {
            Content(
                uiState = DownloadedSurahRecitersUiState(
                    reciters = listOf(
                        DownloadedSurahRecitersUi(
                            id = 1,
                            name = "Mishary Rashid Alafasy",
                            recitingType = "Murattal",
                            isDownloaded = true
                        ),
                        DownloadedSurahRecitersUi(
                            id = 2,
                            name = "Abdul Basit Abdul Samad",
                            recitingType = "Mujawwad",
                            isDownloaded = false
                        ),
                        DownloadedSurahRecitersUi(
                            id = 3,
                            name = "Saad Al Ghamdi",
                            recitingType = "Murattal",
                            isDownloaded = true
                        )
                    ),
                    selectedReciterId = 1,
                    surahId = 1
                ),
                listener = object : DownloadedSurahRecitersListener {
                    override fun onBackClick() {}
                    override fun onSearchClick() {}
                    override fun onDownloadClick(reciterId: Int) {}
                    override fun onSelectReciterClick(reciterId: Int) {}
                    override fun onQueryChange(newQuery: String) {}
                    override fun onClearQueryClick() {}
                }
            )
        }
    }
}
