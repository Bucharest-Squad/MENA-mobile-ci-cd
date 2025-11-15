package net.thechance.mena.faith.presentation.feature.prayertime.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mena.faith_presentation.generated.resources.Res
import mena.faith_presentation.generated.resources.clear_selection
import mena.faith_presentation.generated.resources.ok
import mena.faith_presentation.generated.resources.select_date
import net.thechance.mena.designsystem.presentation.component.button.PrimaryButton
import net.thechance.mena.designsystem.presentation.component.dialog.BasicDialog
import net.thechance.mena.designsystem.presentation.component.scaffold.Scaffold
import net.thechance.mena.designsystem.presentation.component.scaffold.ScaffoldScope
import net.thechance.mena.designsystem.presentation.component.text.Text
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import net.thechance.mena.faith.presentation.components.IslamicDatePicker
import net.thechance.mena.faith.presentation.designSystem.theme.QuranTheme
import net.thechance.mena.faith.presentation.utils.IslamicDate
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ScaffoldScope.IslamicDatePickerDialog(
    isVisible: Boolean,
    selectedDate: IslamicDate?,
    onDateChange: (Int, Int, Int) -> Unit,
    onDismiss: () -> Unit,
) {
    // Store temporary selected date locally
    var tempSelectedDay by remember(isVisible, selectedDate) {
        mutableStateOf(selectedDate?.day ?: 1)
    }
    var tempSelectedMonth by remember(isVisible, selectedDate) {
        mutableStateOf(selectedDate?.month ?: 1)
    }
    var tempSelectedYear by remember(isVisible, selectedDate) {
        mutableStateOf(selectedDate?.year ?: 1445)
    }

    BasicDialog(
        onDismiss = onDismiss,
        onCancelClick = onDismiss,
        isVisible = isVisible,
        contentPadding = PaddingValues(
            horizontal = Theme.spacing._12,
            vertical = Theme.spacing._24,
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(Res.string.select_date),
                style = Theme.typography.title.medium,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            IslamicDatePicker(
                selectedDate = IslamicDate(tempSelectedDay, tempSelectedMonth, tempSelectedYear),
                onDateChange = { day, month, year ->
                    tempSelectedDay = day
                    tempSelectedMonth = month
                    tempSelectedYear = year
                },
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = Theme.spacing._12)
                    .padding(horizontal = Theme.spacing._12),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(Res.string.clear_selection),
                    style = Theme.typography.label.medium,
                    color = Theme.colorScheme.disabled,
                )

                Text(
                    text = stringResource(Res.string.ok),
                    style = Theme.typography.label.medium,
                    color = Theme.colorScheme.primary.primary,
                )
            }
        }
    }
}

@Preview
@Composable
fun IslamicDatePickerDialogPreview() {
    MenaTheme {
        QuranTheme {
            Scaffold(
                overlays = {
                    dialog(isVisible = true) {
                        IslamicDatePickerDialog(
                            isVisible = true,
                            selectedDate = IslamicDate(1, 1, 1445),
                            onDateChange = { _, _, _ -> },
                            onDismiss = {}
                        )
                    }
                }
            ) {
                Text(
                    text = "Test",
                    style = Theme.typography.title.medium,
                    modifier = Modifier.padding(16.dp),
                )
            }
        }
    }
}
