package net.thechance.mena.faith.presentation.feature.prayertime.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import net.thechance.mena.designsystem.presentation.component.button.PrimaryButton
import net.thechance.mena.designsystem.presentation.component.dialog.BasicDialog
import net.thechance.mena.designsystem.presentation.component.scaffold.ScaffoldScope
import net.thechance.mena.designsystem.presentation.component.text.Text
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import net.thechance.mena.faith.presentation.components.IslamicDate
import net.thechance.mena.faith.presentation.components.IslamicDatePicker

@Composable
fun ScaffoldScope.IslamicDatePickerDialog(
    isVisible: Boolean,
    selectedDate: IslamicDate?,
    onDateChange: (Int, Int, Int) -> Unit,
    onDismiss: () -> Unit,
) {
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
                text = "Select date",
                style = Theme.typography.title.medium,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            IslamicDatePicker(
                selectedDate = selectedDate,
                onDateChange = onDateChange,
                modifier = Modifier
            )

            PrimaryButton(
                text = "OK",
                onClick = onDismiss,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
            )
        }
    }
}
