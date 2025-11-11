package net.thechance.mena.faith.presentation.feature.prayertime.component

import androidx.compose.foundation.layout.Column
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
import net.thechance.mena.designsystem.presentation.component.button.PrimaryButton
import net.thechance.mena.designsystem.presentation.component.dialog.BasicDialog
import net.thechance.mena.designsystem.presentation.component.scaffold.ScaffoldScope
import net.thechance.mena.designsystem.presentation.component.text.Text
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import net.thechance.mena.faith.presentation.components.IslamicDatePicker
import net.thechance.mena.faith.presentation.utils.IslamicDate

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
        isVisible = isVisible,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "اختر التاريخ الهجري",
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
                modifier = Modifier
            )

            PrimaryButton(
                text = "موافق",
                onClick = {
                    onDateChange(tempSelectedDay, tempSelectedMonth, tempSelectedYear)
                    onDismiss()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
        }
    }
}