package net.thechance.mena.core_chat.presentation.screen.messaging.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.BackHandler
import androidx.compose.ui.graphics.Color
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import mena.core_chat_presentation.generated.resources.cancel
import mena.core_chat_presentation.generated.resources.Res
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import net.thechance.mena.designsystem.presentation.component.icon.Icon
import net.thechance.mena.designsystem.presentation.component.text.Text
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import mena.core_chat_presentation.generated.resources.actions
import mena.core_chat_presentation.generated.resources.delete
import mena.core_chat_presentation.generated.resources.refresh
import mena.core_chat_presentation.generated.resources.re_send
import mena.core_chat_presentation.generated.resources.delete_message

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

        DialogContent(onDeleteMessageClick, onResendClick, onDismiss)
        BackHandler(true) {
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
        modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp).background(
            Theme.colorScheme.background.surfaceLow, shape = RoundedCornerShape(
                Theme.radius.xl
            )
        )
    ) {
        Icon(
            painter = painterResource(Res.drawable.cancel),
            contentDescription = null,
            modifier = Modifier.align(Alignment.TopStart).padding(start = 12.dp, top = 12.dp)
                .clip(RoundedCornerShape(Theme.radius.full))
                .clickable(
                    onClick = onCancelClick,
                )
                .background(
                    Theme.colorScheme.background.surface
                )
                .padding(PaddingValues(8.dp)),
            tint = Theme.colorScheme.primary.primary
        )
        Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 24.dp)) {
            Text(
                text = stringResource(Res.string.actions), style = Theme.typography.title.small,
                textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.padding(top = 24.dp).fillMaxWidth().background(
                    color = Theme
                        .colorScheme.background.surface, shape = RoundedCornerShape(12.dp)
                ).padding(12.dp).clickable(onClick = onResendClick),
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
                    modifier = Modifier.padding(start = 8.dp)
                )
            }


            Row(
                modifier = Modifier.padding(top = 8.dp).fillMaxWidth().background(
                    color = Theme
                        .colorScheme.background.surface, shape = RoundedCornerShape(12.dp)
                ).padding(12.dp).clickable(onClick = onDeleteMessageClick),
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
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}
