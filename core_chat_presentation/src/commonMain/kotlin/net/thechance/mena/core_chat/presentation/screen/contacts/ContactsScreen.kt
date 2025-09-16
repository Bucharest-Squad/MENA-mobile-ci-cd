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
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.cash.paging.compose.LazyPagingItems
import app.cash.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
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
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ContactsScreen(viewModel: ContactsViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()
    val navController = LocalNavController.current

    val stateFlow = navController.currentBackStackEntry
        ?.savedStateHandle
        ?.getStateFlow("is_sync_success", false)

    val isSyncedState = stateFlow?.collectAsState(initial = false)
    val isSynced = isSyncedState?.value == true

    LaunchedEffect(isSynced) {
        if (isSynced) {
            viewModel.getContacts()
            navController.currentBackStackEntry
                ?.savedStateHandle
                ?.set("is_sync_success", false)
        }
    }

    ContactsEffectsHandler(effects = viewModel.effect)

    ContactsContent(
        contacts = state.contacts.collectAsLazyPagingItems(),
        interactionListener = viewModel
    )
}

@Composable
private fun ContactsContent(
    contacts: LazyPagingItems<ContactUiModel>,
    interactionListener: ContactsScreenInteractionListener
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
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
            onLeadingClick = interactionListener::onBackClick,
            trailingContent = {
                AppBarOptionContainer(
                    badgeColor = Theme.colorScheme.primary.primary,
                    onClick = interactionListener::onResyncClick
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
        ContactsList(
            contacts = contacts,
            onContactClick = interactionListener::onContactClick
        )
    }
}

@Composable
private fun ContactsEffectsHandler(
    effects: Flow<ContactsScreenEffect>
) {
    val navController = LocalNavController.current
    val currentNavController by rememberUpdatedState(navController)

    LaunchedEffect(Unit) {
        effects.collectLatest { effect ->
            when (effect) {
                is ContactsScreenEffect.NavigateBack -> {
                    currentNavController.popBackStack()
                }

                is ContactsScreenEffect.NavigateToSyncContacts -> {
                    currentNavController.navigate(SyncContactsRoute(forceSync = true))
                }

                is ContactsScreenEffect.NavigateToChatScreen -> {
                    currentNavController.navigate((effect.contactId))
                }
            }
        }
    }
}