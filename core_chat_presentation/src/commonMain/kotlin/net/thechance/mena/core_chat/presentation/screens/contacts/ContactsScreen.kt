package net.thechance.mena.core_chat.presentation.screens.contacts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import mena.core_chat_presentation.generated.resources.Res
import mena.core_chat_presentation.generated.resources.arrow_left
import mena.core_chat_presentation.generated.resources.contacts_title
import mena.core_chat_presentation.generated.resources.image_disabled
import mena.core_chat_presentation.generated.resources.image_enabled
import mena.core_chat_presentation.generated.resources.resync
import net.thechance.mena.designsystem.presentation.component.appBar.AppBar
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun ContactsScreen() {
    ContactsContent (
        onResyncClick = {}
    )

}

@Composable
fun ContactsContent(
    modifier: Modifier = Modifier,
    onResyncClick: () -> Unit
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
            items(20) {
                ContactItem(
                    contact = Contact(
                        id = "",
                        firstName = "Mona",
                        lastName = "Ayman Elsayed Ahhmed",
                        phoneNumbers = listOf("01224479626"),
                        imageUri = null,
                    ),
                    hasAccount = true,
                    hasImage = true,
                    onContactClick = {},
                )
            }
        }
    }
}

@Composable
private fun ContactItem(
    contact: Contact,
    hasAccount: Boolean,
    hasImage: Boolean,
    onContactClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { onContactClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Avatar(
            imageUri = contact.imageUri,
            initials = contact.initials,
            size = 48
        )
        Column(
            modifier = Modifier.padding(start = Theme.spacing._8).weight(1f)
        ) {
            Text(
                text = contact.displayName,
                style = Theme.typography.label.large,
                color = Theme.colorScheme.shadePrimary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = contact.phoneNumbers.firstOrNull() ?: "",
                style = Theme.typography.label.medium,
                color = Theme.colorScheme.shadeTertiary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = Theme.spacing._2)
            )

        }
        if (hasAccount) {
            if (hasImage) {
                Icon(
                    painter = painterResource(Res.drawable.image_enabled),
                    contentDescription = null,
                    modifier = Modifier.padding(start = Theme.spacing._8).size(24.dp),
                    tint = Color.Unspecified
                )
            } else {
                Icon(
                    painter = painterResource(Res.drawable.image_disabled),
                    contentDescription = null,
                    modifier = Modifier.padding(start = Theme.spacing._8).size(24.dp),
                    tint = Color.Unspecified
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