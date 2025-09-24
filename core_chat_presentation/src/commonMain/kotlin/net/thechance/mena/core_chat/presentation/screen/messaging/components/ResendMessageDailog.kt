package net.thechance.mena.core_chat.presentation.screen.messaging.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import mena.core_chat_presentation.generated.resources.*
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import net.thechance.mena.designsystem.presentation.component.icon.Icon
import net.thechance.mena.designsystem.presentation.component.text.Text
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ResendMessageDialog(
    onDeleteMessageClick: () -> Unit,
    onResendClick: () -> Unit,
    scrimColor: Color = Theme.colorScheme.primary.primary.copy(0.55f),
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(scrimColor)
                .clickable(
                    onClick = onDismiss,
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                )
        )

        DialogContent(
            onDeleteMessageClick = onDeleteMessageClick,
            onResendClick = onResendClick,
            onCancelClick = onDismiss
        )

        BackHandler(enabled = true) {
            onDismiss()
        }
    }
}

@Composable
private fun DialogContent(
    onDeleteMessageClick: () -> Unit,
    onResendClick: () -> Unit,
    onCancelClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = Theme.spacing._16)
            .background(
                Theme.colorScheme.background.surfaceLow,
                shape = RoundedCornerShape(Theme.radius.xl)
            )
    ) {
        // Cancel icon
        Icon(
            painter = painterResource(Res.drawable.cancel),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = Theme.spacing._12, top = Theme.spacing._12)
                .clip(RoundedCornerShape(Theme.radius.full))
                .clickable(onClick = onCancelClick)
                .background(Theme.colorScheme.background.surface)
                .padding(PaddingValues(Theme.spacing._8)),
            tint = Theme.colorScheme.primary.primary
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = Theme.spacing._12, vertical = Theme.spacing._24)
        ) {
            Text(
                text = stringResource(Res.string.actions),
                style = Theme.typography.title.small,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            // Resend row
            Row(
                modifier = Modifier
                    .padding(top = Theme.spacing._24)
                    .fillMaxWidth()
                    .background(
                        color = Theme.colorScheme.background.surface,
                        shape = RoundedCornerShape(Theme.radius.md)
                    )
                    .clip(RoundedCornerShape(Theme.radius.md))
                    .clickable(onClick = onResendClick)
                    .padding(Theme.spacing._12),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(Res.drawable.refresh),
                    contentDescription = null,
                    tint = Theme.colorScheme.shadePrimary
                )
                Text(
                    text = stringResource(Res.string.re_send),
                    style = Theme.typography.label.medium,
                    color = Theme.colorScheme.shadePrimary,
                    modifier = Modifier.padding(start = Theme.spacing._8)
                )
            }

            // Delete row
            Row(
                modifier = Modifier
                    .padding(top = Theme.spacing._8)
                    .fillMaxWidth()
                    .background(
                        color = Theme.colorScheme.background.surface,
                        shape = RoundedCornerShape(Theme.radius.md)
                    )
                    .clip(RoundedCornerShape(Theme.radius.md))
                    .clickable(onClick = onDeleteMessageClick)
                    .padding(Theme.spacing._12),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(Res.drawable.delete),
                    contentDescription = null,
                    tint = Theme.colorScheme.error
                )
                Text(
                    text = stringResource(Res.string.delete_message),
                    style = Theme.typography.label.medium,
                    color = Theme.colorScheme.error,
                    modifier = Modifier.padding(start = Theme.spacing._8)
                )
            }
        }
    }
}
