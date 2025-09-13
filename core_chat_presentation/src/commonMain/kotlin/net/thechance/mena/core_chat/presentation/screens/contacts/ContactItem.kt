package net.thechance.mena.core_chat.presentation.screens.contacts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.resources.painterResource


@Composable
fun ContactItem(
    contact: ContactUi,
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
            contactImageUri = contact.imageUri,
            contactInitials = contact.initials,
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
                text = contact.phoneNumbers,
                style = Theme.typography.label.medium,
                color = Theme.colorScheme.shadeTertiary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(top = Theme.spacing._2)
            )

        }

        if (hasImage) {
            if (hasAccount) {
                val iconRes =
                    if (hasImage) Res.drawable.image_enabled else Res.drawable.image_disabled
                Icon(
                    painter = painterResource(iconRes),
                    contentDescription = null,
                    modifier = Modifier.padding(start = Theme.spacing._8).size(24.dp),
                    tint = Color.Unspecified
                )
            }
        }
    }
}