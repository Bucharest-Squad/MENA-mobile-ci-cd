package net.thechance.mena.core_chat.presentation.screens.contacts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mena.core_chat_presentation.generated.resources.Res
import mena.core_chat_presentation.generated.resources.arrow_left
import mena.core_chat_presentation.generated.resources.contacts_title
import mena.core_chat_presentation.generated.resources.resync
import net.thechance.mena.designsystem.presentation.component.appBar.AppBar
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ContactsScreen() {
    ContactsContent(
        onResyncClick = {},
        contacts = emptyList()
    )
}

@Composable
private fun ContactsContent(
    modifier: Modifier = Modifier,
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
            leadingContent = {
                Icon(
                    painter = painterResource(Res.drawable.arrow_left),
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                        .clickable {

                        },
                    tint = Theme.colorScheme.primary.primary,
                )
            },
            trailingContent = {
                Box(
                    modifier = Modifier.size(40.dp)
                        .background(
                            color = Theme.colorScheme.background.surfaceLow,
                            shape = RoundedCornerShape(Theme.radius.md)
                        ).clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            onResyncClick()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.resync),
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
                    hasAccount = true,
                    hasImage = true,
                    onContactClick = {},
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