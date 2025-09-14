package net.thechance.mena.core_chat.presentation.screen.contacts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mena.core_chat_presentation.generated.resources.Res
import mena.core_chat_presentation.generated.resources.contacts_title
import mena.core_chat_presentation.generated.resources.ic_arrow_left
import mena.core_chat_presentation.generated.resources.ic_resync
import net.thechance.mena.core_chat.presentation.navigation.LocalNavController
import net.thechance.mena.core_chat.presentation.navigation.SyncContactsRoute
import net.thechance.mena.designsystem.presentation.component.appBar.AppBar
import net.thechance.mena.designsystem.presentation.component.appBar.AppBarOptionContainer
import net.thechance.mena.designsystem.presentation.component.icon.MenaIcon
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ContactsScreen() {
    val navController = LocalNavController.current
    ContactsContent(
        navController = navController,
        onResyncClick = {
            navController.navigate(SyncContactsRoute)
        },
        contacts = temporaryContacts
    )
}

@Composable
private fun ContactsContent(
    modifier: Modifier = Modifier,
    navController: androidx.navigation.NavController = LocalNavController.current,
    onResyncClick: () -> Unit,
    contacts: List<ContactUi>
) {

    Column(
        modifier = modifier.fillMaxSize()
            .background(color = Theme.colorScheme.background.surface)
            .statusBarsPadding()
    ) {
        AppBar(
            title = stringResource(Res.string.contacts_title),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
            leadingContent = {
                MenaIcon(
                    painter = painterResource(Res.drawable.ic_arrow_left),
                    modifier = Modifier.size(20.dp),
                    contentDescription = null,
                    tint = Theme.colorScheme.primary.primary,
                )
            },
            onLeadingClick = { navController.popBackStack() },
            trailingContent = {
                AppBarOptionContainer(
                    badgeColor = Theme.colorScheme.primary.primary,
                    onClick = { onResyncClick() }
                ) {
                    MenaIcon(
                        painter = painterResource(Res.drawable.ic_resync),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = Theme.colorScheme.shadePrimary,
                    )
                }
            }
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth().padding(horizontal = Theme.spacing._16),
            verticalArrangement = Arrangement.spacedBy(Theme.spacing._16),
            contentPadding = PaddingValues(vertical = Theme.spacing._8)
        ) {
            items(contacts.size) { contact ->
                ContactItem(
                    contact = contacts[contact],
                    onContactClick = {
                        //TODO: navigate to chat screen
                    },
                )
            }
        }
    }
}


@Composable
@Preview()
private fun ContactsScreenPreview() {
    MenaTheme {
        ContactsScreen()
    }
}