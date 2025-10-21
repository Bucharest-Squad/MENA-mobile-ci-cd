package net.thechance.mena.faith.presentation.feature.quran.search.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import mena.faith_presentation.generated.resources.Res
import mena.faith_presentation.generated.resources.ic_empty_search
import mena.faith_presentation.generated.resources.ic_search_warning
import mena.faith_presentation.generated.resources.icon_shadow
import mena.faith_presentation.generated.resources.no_results_found_subtitle
import mena.faith_presentation.generated.resources.no_results_found_title
import mena.faith_presentation.generated.resources.shadow
import mena.faith_presentation.generated.resources.start_searching_subtitle
import mena.faith_presentation.generated.resources.start_searching_title
import net.thechance.mena.designsystem.presentation.component.text.Text
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import net.thechance.mena.faith.presentation.designSystem.theme.QuranTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun SearchEmptyState(
    showStartState: Boolean,
    showNoResultsState: Boolean,
    modifier: Modifier = Modifier
) {
    if (showStartState || showNoResultsState) {
        val isStartState = showStartState

        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            EmptyStateIcon(isStartState)

            Text(
                text = stringResource(getTitleResource(isStartState)),
                style = Theme.typography.title.small,
                color = Theme.colorScheme.shadePrimary,
            )

            Text(
                text = stringResource(getSubtitleResource(isStartState)),
                style = Theme.typography.body.small,
                textAlign = TextAlign.Center,
                color = Theme.colorScheme.shadeSecondary,
            )
        }
    }
}

@Composable
private fun EmptyStateIcon(isStartState: Boolean) {
    Box(
        modifier = Modifier.size(128.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.align(Alignment.BottomCenter),
            painter = painterResource(Res.drawable.shadow),
            contentDescription = stringResource(Res.string.icon_shadow),
        )
        Image(
            modifier = Modifier.padding(bottom = Theme.spacing._12),
            painter = painterResource(getIconResource(isStartState)),
            contentDescription = stringResource(getTitleResource(isStartState))
        )
    }
}

private fun getIconResource(isStartState: Boolean): DrawableResource {
    return if (isStartState) Res.drawable.ic_empty_search else Res.drawable.ic_search_warning
}

private fun getTitleResource(isStartState: Boolean): StringResource {
    return if (isStartState) Res.string.start_searching_title else Res.string.no_results_found_title
}

private fun getSubtitleResource(isStartState: Boolean): StringResource {
    return if (isStartState) Res.string.start_searching_subtitle else Res.string.no_results_found_subtitle
}

@Preview
@Composable
private fun SearchEmptyStateStartPreview() {
    QuranTheme {
        SearchEmptyState(
            showStartState = true,
            showNoResultsState = false,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview
@Composable
private fun SearchEmptyStateNoResultsPreview() {
    QuranTheme {
        SearchEmptyState(
            showStartState = false,
            showNoResultsState = true,
            modifier = Modifier.fillMaxSize()
        )
    }
}