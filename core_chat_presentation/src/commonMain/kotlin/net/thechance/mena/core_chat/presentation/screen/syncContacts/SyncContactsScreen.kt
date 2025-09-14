package net.thechance.mena.core_chat.presentation.screen.syncContacts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mena.core_chat_presentation.generated.resources.Res
import mena.core_chat_presentation.generated.resources.phone_icon
import net.thechance.mena.core_chat.presentation.navigation.LocalNavController
import net.thechance.mena.core_chat.presentation.screen.syncContacts.components.ContactsSyncedView
import net.thechance.mena.core_chat.presentation.screen.syncContacts.components.NoContactsSyncView
import net.thechance.mena.core_chat.presentation.screen.syncContacts.components.SyncContactsAppBar
import net.thechance.mena.designsystem.presentation.component.image.MenaImage
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
fun SyncContactsScreen(
) {
    var isSyncing by remember { mutableStateOf(false) }
    SyncContactsContent(
        isSyncing = isSyncing,
        onSyncClick = {
            isSyncing = true
        },
    )
}

@Composable
private fun SyncContactsContent(
    isSyncing: Boolean,
    onSyncClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val navController = LocalNavController.current
    Column(
        modifier = modifier.fillMaxSize()
            .background(color = Theme.colorScheme.background.surface)
            .statusBarsPadding()
    ) {
        SyncContactsAppBar(navController = navController)
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(vertical = Theme.spacing._16, horizontal = Theme.spacing._24),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            MenaImage(
                painter = painterResource(resource = Res.drawable.phone_icon),
                contentDescription = "No contacts synced",
                modifier = Modifier.size(128.dp, 122.dp)
            )
            if (isSyncing) {
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
@Preview()
private fun SyncContactsScreenPreview() {
    MenaTheme {
        SyncContactsScreen()
    }
}
