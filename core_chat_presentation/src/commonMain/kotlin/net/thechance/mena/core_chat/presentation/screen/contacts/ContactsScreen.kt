package net.thechance.mena.core_chat.presentation.screen.contacts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.thechance.mena.core_chat.presentation.navigation.LocalNavController
import net.thechance.mena.core_chat.presentation.navigation.SyncContactsRoute
import net.thechance.mena.core_chat.presentation.screen.contacts.components.ContactsAppBar
import net.thechance.mena.core_chat.presentation.screen.contacts.components.ContactsList
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
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
        ContactsAppBar(
            navController = navController,
            onResyncClick = onResyncClick
        )
        ContactsList(
            contacts = contacts
        )
    }
}


@Composable
@Preview()
private fun ContactsScreenPreview() {
    MenaTheme {
        ContactsScreen()
    }
}