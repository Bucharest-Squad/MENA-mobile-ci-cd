package net.thechance.mena.core_chat.presentation.screens.syncContacts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import mena.core_chat_presentation.generated.resources.Res
import mena.core_chat_presentation.generated.resources.ic_arrow_left
import mena.core_chat_presentation.generated.resources.phone_icon
import mena.core_chat_presentation.generated.resources.sync_contacts
import mena.core_chat_presentation.generated.resources.sync_contacts_desc
import mena.core_chat_presentation.generated.resources.sync_contacts_title
import mena.core_chat_presentation.generated.resources.syncing_contacts_message
import net.thechance.mena.designsystem.presentation.component.appBar.AppBar
import net.thechance.mena.designsystem.presentation.component.button.Button
import net.thechance.mena.designsystem.presentation.component.icon.MenaIcon
import net.thechance.mena.designsystem.presentation.component.image.MenaImage
import net.thechance.mena.designsystem.presentation.component.text.MenaText
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun SyncContactsScreen(
) {
    SyncContactsContent(
        onSyncClick = {},
    )
}

@Composable
private fun SyncContactsContent(
    onSyncClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var isSynced by remember { mutableStateOf(false) }
    Column(
        modifier = modifier.fillMaxSize()
            .background(color = Theme.colorScheme.background.surface)
            .statusBarsPadding()
    ) {
        AppBar(
            title = stringResource(Res.string.sync_contacts),
            leadingContent = {
                MenaIcon(
                    painter = painterResource(resource = Res.drawable.ic_arrow_left),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                        .clickable {},
                    tint = Theme.colorScheme.primary.primary,
                )
            }
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(vertical = Theme.spacing._16),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            if (isSynced) {
                ContactsSyncedView()
            } else {
                NoContactsSyncView(
                    onSyncClick = onSyncClick,
                )
            }
        }
    }
}


@Composable
private fun NoContactsSyncView(
    onSyncClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(horizontal = Theme.spacing._24).fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        MenaImage(
            painter = painterResource(resource = Res.drawable.phone_icon),
            contentDescription = "No contacts synced",
            modifier = Modifier.size(128.dp, 122.dp)
        )
        MenaText(
            text = stringResource(Res.string.sync_contacts_title),
            style = Theme.typography.title.small,
            color = Theme.colorScheme.shadePrimary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = Theme.spacing._12).fillMaxWidth()
        )
        MenaText(
            text = stringResource(Res.string.sync_contacts_desc),
            modifier = Modifier.padding(top = Theme.spacing._2, bottom = Theme.spacing._12),
            textAlign = TextAlign.Center,
            style = Theme.typography.body.small,
            color = Theme.colorScheme.shadeSecondary
        )
        Button(
            onClick = {
                onSyncClick()
            },
            containerColor = Theme.colorScheme.primary.primary,
            shape = RoundedCornerShape(Theme.radius.md),
            modifier = Modifier.fillMaxWidth()
        ) {
            MenaText(
                modifier = Modifier.padding(vertical = 13.dp),
                text = stringResource(Res.string.sync_contacts),
                style = Theme.typography.label.medium,
                color = Theme.colorScheme.primary.onPrimary,
            )
        }
    }
}

@Composable
private fun ContactsSyncedView(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(horizontal = Theme.spacing._24).fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        MenaImage(
            painter = painterResource(resource = Res.drawable.phone_icon),
            contentDescription = "No contacts synced",
            modifier = Modifier.size(128.dp, 122.dp)
        )
        MenaText(
            text = stringResource(Res.string.syncing_contacts_message),
            style = Theme.typography.title.small,
            color = Theme.colorScheme.shadePrimary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(vertical = Theme.spacing._24)
        )
        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth(),
            color = Theme.colorScheme.background.surfaceHigh,
            trackColor = Theme.colorScheme.primary.primary,
        )
    }
}

@Composable
@Preview()
private fun SyncContactsScreenPreview() {
    MenaTheme {
        SyncContactsScreen()
    }
}
