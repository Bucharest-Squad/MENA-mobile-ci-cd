package net.thechance.mena.core_chat.presentation.screen.contacts.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.thechance.mena.core_chat.presentation.screen.contacts.ContactUi
import net.thechance.mena.designsystem.presentation.theme.theme.Theme

@Composable
fun ContactsList(
    contacts: List<ContactUi>
) {
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