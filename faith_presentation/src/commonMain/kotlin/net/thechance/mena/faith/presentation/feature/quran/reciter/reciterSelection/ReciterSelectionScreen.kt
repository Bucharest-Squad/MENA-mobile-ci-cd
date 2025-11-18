package net.thechance.mena.faith.presentation.feature.quran.reciter.reciterSelection

import androidx.compose.foundation.layout.Column
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
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ReciterSelectionScreen(
    viewModel: ReciterSelectionViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val navController = LocalNavController.current

    ObserveAsEffect(viewModel.uiEffect) { effect ->
        when (effect) {
            ReciterSelectionEffect.NavigateBack -> navController.popBackStack()
            ReciterSelectionEffect.NavigateToSearch -> {
            }
        }
    }
    Content(
        state = state,
        listener = viewModel
    )
}

@Composable
private fun Content(
    state: ReciterSelectionUiState,
    listener: ReciterSelectionListener
) {
    Scaffold(
        topBar = {
            SearchReciter(
                    query = state.query,
                    hint = state.queryHint,
                    onQueryChange = listener::onQueryChange,
                    clearQuery = listener::onClearQueryClick,
                    onBackClick = listener::onBackClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Theme.spacing._16, vertical = Theme.spacing._4)
                )
        })

    {

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            if (state.query.isNotBlank() && state.searchResults.isEmpty()) {
                SearchEmptyState(
                    subtitle = Res.string.search_reciter,
                    isStartState = false,
                    isResultsState = true, 
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                )
            }
            ResultList(
                isNotBlankQuery = state.query.isNotBlank(),
                isNotEmptyResult = state.searchResults.isNotEmpty(),
                results = state.searchResults,
                modifier = Modifier.fillMaxWidth().weight(1f).padding(top = Theme.spacing._16)

            )
        }
    }
}

@Composable
private fun ResultList(
    isNotBlankQuery: Boolean,
    isNotEmptyResult: Boolean,
    results: List<ReciterSelectionUi>,
    modifier: Modifier = Modifier
) {
    val shouldShowResults = isNotBlankQuery && isNotEmptyResult
    if (!shouldShowResults) return

    LazyColumn(
        modifier = modifier,
    ) {
        items(results) { result ->
            ReciterItem(
                reciterId = result.id,
                reciter = result.name,
                recitingType = result.recitingType,
                isDownloaded = false,
                onSelect = {},
                onDownloadClick = {},
                isSelectReciter = false,
                isSwipeable = false,
            )
        }
    }
}


@Composable
@Preview
private fun SearchScreenPreview() {
    MenaTheme {
        QuranTheme {
            Content(
                state = ReciterSelectionUiState(
                    query = "s",
                    searchResults = listOf(
                        ReciterSelectionUi(
                            id = 1,
                            name = "Mishary Rashid Alafasy",
                            recitingType = "Murattal",
                            isDownloaded = false
                        ),
                        ReciterSelectionUi(
                            id = 2,
                            name = "Abdul Basit Abdul Samad",
                            recitingType = "Mujawwad",
                            isDownloaded = false
                        ),
                        ReciterSelectionUi(
                            id = 3,
                            name = "Saad Al Ghamdi",
                            recitingType = "Murattal",
                            isDownloaded = false
                        )
                    ),
                    lastSearchedQuery = "",
                    queryHint = ""
                ),
                listener = object : ReciterSelectionListener {
                    override fun onBackClick() {}
                    override fun onClearQueryClick() {}
                    override fun onQueryChange(query: String) {}
                    override fun onSearchClick() {}
                })
        }
    }
}