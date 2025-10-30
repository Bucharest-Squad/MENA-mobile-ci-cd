package net.thechance.mena.admin_panel.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import net.thechance.mena.admin_panel.resources.Res
import net.thechance.mena.admin_panel.resources.cancel
import net.thechance.mena.designsystem.presentation.component.button.OutlinedButton
import net.thechance.mena.designsystem.presentation.component.button.PrimaryButton
import net.thechance.mena.designsystem.presentation.component.icon.Icon
import net.thechance.mena.designsystem.presentation.component.text.Text
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.resources.stringResource

@Composable
fun Dialog(
    dialogIcon: Painter,
    confirmationIcon: Painter,
    title: String,
    description: String,
    confirmationButtonText: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(
                    color = Theme.colorScheme.background.surfaceLow,
                    shape = RoundedCornerShape(24.dp)
                )
                .padding(24.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(88.dp)
                    .background(
                        color = Theme.colorScheme.background.bgError,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = dialogIcon,
                    contentDescription = title,
                    modifier = Modifier.size(48.dp),
                    tint = Theme.colorScheme.error
                )
            }
            DialogContent(
                title = title,
                description = description,
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
            )
            DialogActionsRow(
                onDismiss = onDismiss,
                onConfirm = onConfirm,
                confirmationButtonText = confirmationButtonText,
                confirmationIcon = confirmationIcon
            )
        }
    }
}

@Composable
private fun DialogContent(
    title: String,
    description: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title,
            style = Theme.typography.title.medium,
        )
        Text(
            text = description,
            style = Theme.typography.body.medium,
        )
    }
}

@Composable
private fun DialogActionsRow(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    confirmationButtonText: String,
    confirmationIcon: Painter
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        PrimaryButton(
            text = stringResource(Res.string.cancel),
            onClick = onDismiss,
            modifier = Modifier.height(48.dp)
        )

        OutlinedButton(
            text = confirmationButtonText,
            onClick = onConfirm,
            trailingIcon = confirmationIcon,
            modifier = Modifier.height(48.dp)
        )
    }
}