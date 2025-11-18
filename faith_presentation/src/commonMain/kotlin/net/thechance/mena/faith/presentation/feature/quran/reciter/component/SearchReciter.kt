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
import net.thechance.mena.designsystem.presentation.component.text.Text
import net.thechance.mena.designsystem.presentation.component.textField.TextField
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import net.thechance.mena.faith.presentation.designSystem.theme.QuranTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SearchReciter(
    title : String,
    query: String,
    hint: String,
    onQueryChange: (String) -> Unit,
    clearQuery: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isSearchMode by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(Theme.radius.md))
                .background(Theme.colorScheme.background.surfaceLow)
                .clickable {
                    if (isSearchMode) {
                        isSearchMode = false
                        clearQuery()
                    } else {
                        onBackClick()
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(20.dp),
                painter = painterResource(Res.drawable.ic_arrow_left),
                contentDescription = stringResource(Res.string.back)
            )
        }

        if (isSearchMode) {

            TextField(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = Theme.spacing._8),
                value = query,
                hint = hint,
                onValueChanged = onQueryChange,
                leadingIcon = painterResource(Res.drawable.ic_outline_search),
                leadingIconTint = Theme.colorScheme.shadeSecondary,
                trailingIcon = if (query.isNotBlank()) painterResource(Res.drawable.ic_clear) else null,
                onTrailingIconClick = clearQuery
            )
        } else {
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = Theme.spacing._8),
                text = title,
                color = Theme.colorScheme.shadePrimary,
                style = Theme.typography.title.medium
            )

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(Theme.radius.md))
                    .background(Theme.colorScheme.background.surfaceLow)
                    .clickable { isSearchMode = true },
                contentAlignment = Alignment.Center
            ) {

                Image(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(Res.drawable.ic_outline_search),
                    contentDescription = "Search"
                )
            }
        }
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
            title = "Select Reciter"
        )
    }
    }
}