package net.thechance.mena.wallet.presentation.component.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mena.wallet_presentation.generated.resources.Res
import mena.wallet_presentation.generated.resources.from
import mena.wallet_presentation.generated.resources.ic_calendar
import mena.wallet_presentation.generated.resources.select_date
import mena.wallet_presentation.generated.resources.status
import mena.wallet_presentation.generated.resources.to
import mena.wallet_presentation.generated.resources.type
import net.thechance.mena.designsystem.presentation.component.chip.Chip
import net.thechance.mena.designsystem.presentation.component.text.Text
import net.thechance.mena.designsystem.presentation.component.textField.TextField
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun FilterContent(
    selectedTypes: Set<FilterType>? = null,
    selectedStatus: FilterStatus = FilterStatus.ALL,
    onTypeSelected: (FilterType) -> Unit = {},
    onStatusSelected: (FilterStatus?) -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
    ) {

        Text(
            text = stringResource(Res.string.type),
            style = MaterialTheme.typography.bodySmall,
            color = Theme.colorScheme.shadePrimary
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(FilterType.entries) { type ->
                Chip(
                    text = stringResource(type.labelRes),
                    isSelected = selectedTypes?.contains(type) == true,
                    onClick = { onTypeSelected(type) },
                    isEnabled = true
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(Res.string.status),
            style = MaterialTheme.typography.bodySmall,
            color = Theme.colorScheme.shadePrimary
        )

        Spacer(modifier = Modifier.height(12.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(FilterStatus.entries) { status ->
                Chip(
                    text = stringResource(status.labelRes),
                    isSelected = selectedStatus == status,
                    onClick = {
                        onStatusSelected(if (selectedStatus == status) null else status)
                    },
                    isEnabled = true
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(Res.string.from),
                    style = MaterialTheme.typography.bodySmall,
                    color = Theme.colorScheme.shadePrimary
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = "",
                    hint = stringResource(Res.string.select_date),
                    onValueChanged = {},
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {

                        },
                    trailingIcon = painterResource(Res.drawable.ic_calendar),
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = stringResource(Res.string.to),
                    style = MaterialTheme.typography.bodySmall,
                    color = Theme.colorScheme.shadePrimary
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = "",
                    hint = stringResource(Res.string.select_date),
                    onValueChanged = {},
                    readOnly = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {

                        },
                    trailingIcon = painterResource(Res.drawable.ic_calendar)
                )
            }
        }
    }
}

@Preview
@Composable
fun Preview() {
    MenaTheme {
        Column(
            Modifier.background(Theme.colorScheme.background.surface)
        ) {
            FilterContent(
                selectedTypes = setOf(FilterType.SENT, FilterType.ONLINE_PURCHASE)
            )
        }
    }
}
