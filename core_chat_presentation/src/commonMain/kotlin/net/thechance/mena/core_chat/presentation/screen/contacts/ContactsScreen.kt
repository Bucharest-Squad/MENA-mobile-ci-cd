package net.thechance.mena.core_chat.presentation.screen.contacts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import mena.core_chat_presentation.generated.resources.Res
import mena.core_chat_presentation.generated.resources.contacts_title
import mena.core_chat_presentation.generated.resources.ic_arrow_left
import mena.core_chat_presentation.generated.resources.ic_resync
import net.thechance.mena.core_chat.presentation.navigation.LocalNavController
import net.thechance.mena.core_chat.presentation.navigation.SyncContactsRoute
import net.thechance.mena.core_chat.presentation.screen.contacts.components.ContactsList
import net.thechance.mena.designsystem.presentation.component.appBar.AppBar
import net.thechance.mena.designsystem.presentation.component.appBar.AppBarOptionContainer
import net.thechance.mena.designsystem.presentation.component.icon.MenaIcon
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ContactsScreen(viewModel: ContactsViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()
    val navController = LocalNavController.current

    LaunchedEffect(Unit) {
        snapshotFlow { navController.currentBackStackEntry }
            .collect { backStackEntry ->
                val isSync =
                    backStackEntry?.savedStateHandle?.get<Boolean>("is_sync") == true
                if (isSync) {
                    viewModel.getContacts()
                    backStackEntry?.savedStateHandle?.remove<Boolean>("is_sync")
                }
            }
    }
    ContactsContent(
        onNavigateBack = { navController.popBackStack() },
        onResyncClick = { navController.navigate(SyncContactsRoute(false)) },
        contacts = state.contacts.collectAsLazyPagingItems()
    )
}

@Composable
private fun ContactsContent(
    onNavigateBack: () -> Unit,
    onResyncClick: () -> Unit,
    contacts: LazyPagingItems<ContactUiModel>
) {
    Column(
        modifier = Modifier.fillMaxSize()
            .background(color = Theme.colorScheme.background.surface)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        AppBar(
            modifier = Modifier,
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
            onLeadingClick = onNavigateBack,
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
        ContactsList(contacts = contacts)
    }
}


@Composable
@Preview()
private fun ContactsScreenPreview() {
    MenaTheme {
        ContactsScreen()
    }
}