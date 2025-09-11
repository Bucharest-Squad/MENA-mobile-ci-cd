package net.thechance.mena.designsystem.presentation.component.dialogue

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import mena.design_system.generated.resources.Res
import mena.design_system.generated.resources.cancel_dialog_icon
import mena.design_system.generated.resources.ic_cancel
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Dialog(
    visible: Boolean,
    title: String,
    message: String,
    buttonText: String,
    dismissOnBackPress: Boolean = true,
    dismissOnClickOutside: Boolean = true,
    onDismiss: () -> Unit,
    onActionClick: () -> Unit = {},
    onCancelClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    contentColor: Color = Theme.colorScheme.background.surfaceLow,
    dialogCornerShape: Shape = RoundedCornerShape(Theme.radius.xl),
    cancelBackgroundShape: Shape = RoundedCornerShape(Theme.radius.full),
    contentPadding: PaddingValues = PaddingValues(12.dp),
) {
    if (!visible) return

    BasicAlertDialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            dismissOnBackPress = dismissOnBackPress,
            dismissOnClickOutside = dismissOnClickOutside,
            usePlatformDefaultWidth = false
        ),
        modifier = modifier
            .size(328.dp, 148.dp)
            .background(contentColor, dialogCornerShape)
            .padding(contentPadding)
    ) {
        DialogContent(
            title = title,
            message = message,
            buttonText = buttonText,
            cancelBackgroundShape = cancelBackgroundShape,
            onActionClick = onActionClick,
            onCancelClick = onCancelClick,
            modifier = modifier
        )
    }
}

@Composable
private fun DialogContent(
    title: String,
    message: String,
    buttonText: String,
    onActionClick: () -> Unit = {},
    onCancelClick: () -> Unit = {},
    cancelBackgroundShape: Shape,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        Icon(
            painter = painterResource(Res.drawable.ic_cancel),
            contentDescription = stringResource(Res.string.cancel_dialog_icon),
            modifier = Modifier
                .clip(cancelBackgroundShape)
                .clickable(
                    onClick = onCancelClick,
                    indication = ripple(),
                    interactionSource = remember { MutableInteractionSource() }
                )
                .background(
                    Theme.colorScheme.background.surface,
                    cancelBackgroundShape
                )
                .padding(PaddingValues(8.dp))
                .align(Alignment.TopStart)
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(top = 12.dp)
                .align(Alignment.TopCenter)
        ) {
            Text(
                text = title,
                style = Theme.typography.title.medium,
                color = Theme.colorScheme.primary.primary,
            )
            Text(
                text = message,
                style = Theme.typography.body.small,
                color = Theme.colorScheme.shadeSecondary,
            )
        }
        Text(
            text = buttonText,
            color = Theme.colorScheme.error,
            style = Theme.typography.label.medium,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 12.dp, end = 8.dp)
                .clickable(
                    onClick = onActionClick,
                    role = Role.Button,
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                )
        )
    }
}


@Preview
@Composable
private fun DialoguePreview() {
    MenaTheme {
        var showDialog by remember { mutableStateOf(true) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .clickable(onClick = { showDialog = true })
                .background(Theme.colorScheme.background.surface),
            contentAlignment = Alignment.Center
        ) {
            Dialog(
                visible = showDialog,
                onDismiss = {
                    showDialog = false
                },
                title = "Preview Title",
                message = "This is a preview message.",
                buttonText = "Confirm",
                onActionClick = {
                    showDialog = false
                },
                onCancelClick = {
                    showDialog = false
                }
            )
        }

    }
}
