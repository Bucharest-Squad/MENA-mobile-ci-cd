package net.thechance.mena.faith.presentation.feature.quran.reciter.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import mena.faith_presentation.generated.resources.Res
import mena.faith_presentation.generated.resources.back
import mena.faith_presentation.generated.resources.ic_arrow_left
import mena.faith_presentation.generated.resources.ic_clear
import mena.faith_presentation.generated.resources.ic_outline_search
import net.thechance.mena.designsystem.presentation.component.appBar.AppBar
import net.thechance.mena.designsystem.presentation.component.appBar.AppBarOptionContainer
import net.thechance.mena.designsystem.presentation.component.textField.TextField
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import net.thechance.mena.faith.presentation.designSystem.theme.QuranTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SearchReciter(
    title: String,
    query: String,
    hint: String,
    onQueryChange: (String) -> Unit,
    clearQuery: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isSearchMode by remember { mutableStateOf(false) }

    if (isSearchMode) {
        Row(
            modifier = modifier
                .padding(horizontal = Theme.spacing._16, vertical = Theme.spacing._4),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(Theme.spacing._8)
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(Theme.radius.md))
                    .background(Theme.colorScheme.background.surfaceLow)
                    .clickable {
                        isSearchMode = false
                        clearQuery()
                    },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(Res.drawable.ic_arrow_left),
                    contentDescription = stringResource(Res.string.back)
                )
            }

            TextField(
                modifier = Modifier
                    .weight(1f),
                value = query,
                hint = hint,
                onValueChanged = onQueryChange,
                leadingIcon = painterResource(Res.drawable.ic_outline_search),
                leadingIconTint = Theme.colorScheme.shadeSecondary,
                trailingIcon = if (query.isNotBlank()) painterResource(Res.drawable.ic_clear) else null,
                onTrailingIconClick = clearQuery
            )
        }
    } else {
        AppBar(
            modifier = modifier,
            title = title,
            leadingContent = {
                Image(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(Res.drawable.ic_arrow_left),
                    contentDescription = stringResource(Res.string.back)
                )
            },
            onLeadingClick = onBackClick,
            trailingContent = {
                AppBarOptionContainer(
                    onClick = { isSearchMode = true }
                ) {
                    Image(
                        modifier = Modifier.size(20.dp),
                        painter = painterResource(Res.drawable.ic_outline_search),
                        contentDescription = "Search"
                    )
                }
            }
        )
    }
}

@Preview
@Composable
private fun SearchHeaderPreview() {
    MenaTheme {
        QuranTheme {
            SearchReciter(
                query = "",
                hint = "Search in Reciter...",
                onQueryChange = {},
                clearQuery = {},
                onBackClick = {},
                title = "Reciter"
            )
        }
    }
}

@Preview
@Composable
private fun SearchHeaderSearchModePreview() {
    MenaTheme {
        QuranTheme {
            SearchReciter(
                query = "Abdul",
                hint = "Search in Reciter...",
                onQueryChange = {},
                clearQuery = {},
                onBackClick = {},
                title = "Reciter"
            )
        }
    }
}