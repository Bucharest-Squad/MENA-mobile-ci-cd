package net.thechance.mena.faith.presentation.feature.quran.reciter.manageDownloadsReciters

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import mena.faith_presentation.generated.resources.Res
import mena.faith_presentation.generated.resources.reciters
import mena.faith_presentation.generated.resources.search_reciter
import net.thechance.mena.designsystem.presentation.component.scaffold.Scaffold
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import net.thechance.mena.faith.presentation.base.ObserveAsEffect
import net.thechance.mena.faith.presentation.components.ReciterItem
import net.thechance.mena.faith.presentation.designSystem.theme.QuranTheme
import net.thechance.mena.faith.presentation.feature.quran.reciter.component.SearchReciter
import net.thechance.mena.faith.presentation.feature.quran.search.ayah.component.SearchEmptyState
import net.thechance.mena.faith.presentation.navigation.LocalNavController
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import kotlin.time.ExperimentalTime

@Composable
fun ManageDownloadsRecitersScreen(
    viewModel: ManageDownloadsRecitersViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navController = LocalNavController.current

    ObserveAsEffect(viewModel.uiEffect) { effect ->
        when (effect) {
            ManageDownloadsRecitersEffect.NavigateBack -> navController.navigateUp()
            ManageDownloadsRecitersEffect.NavigateToSearch -> {}
        }
    }

    Content(uiState = uiState, listener = viewModel)
}

@OptIn(ExperimentalTime::class)
@Composable
private fun Content(
    uiState: ManageDownloadsRecitersUiState,
    listener: ManageDownloadsRecitersListener,
) {
    Scaffold(
        topBar = {
            SearchReciter(
                title = stringResource(Res.string.reciters),
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

        if (uiState.query.isNotBlank() && uiState.reciters.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                SearchEmptyState(
                    subtitle = Res.string.search_reciter,
                    isStartState = false,
                    isResultsState = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Theme.spacing._16)
                )
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(bottom = Theme.spacing._16),
            ) {
                items(uiState.reciters) { reciter ->
                    ReciterItem(
                        reciterId = reciter.id,
                        reciter = reciter.name,
                        recitingType = reciter.recitingType,
                        isDownloaded = false,
                        onSelect = {
                            listener.onSelectReciterClick(reciter.id)
                        },
                        onDownloadClick = {},
                        isSelectReciter = reciter.id == uiState.selectedReciterId,
                        isSwipeable = uiState.isSwipeable,
                        downloadedIcon = false
                    )
                }
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
                uiState = ManageDownloadsRecitersUiState(
                    reciters = listOf(
                        ManageDownloadsRecitersUi(
                            id = 1,
                            name = "Mishary Rashid Alafasy",
                            recitingType = "Murattal",
                            isDownloaded = true
                        ),
                        ManageDownloadsRecitersUi(
                            id = 2,
                            name = "Abdul Basit Abdul Samad",
                            recitingType = "Mujawwad",
                            isDownloaded = false
                        ),
                        ManageDownloadsRecitersUi(
                            id = 3,
                            name = "Saad Al Ghamdi",
                            recitingType = "Murattal",
                            isDownloaded = false
                        )
                    ),
                    selectedReciterId = 1,
                    surahId = 1
                ),
                listener = object : ManageDownloadsRecitersListener {
                    override fun onBackClick() {}
                    override fun onSearchClick() {}
                    override fun onQueryChange(query: String) {}
                    override fun onClearQueryClick() {}
                    override fun onSelectReciterClick(reciterId: Int) {}
                }
            )
        }
    }
}
