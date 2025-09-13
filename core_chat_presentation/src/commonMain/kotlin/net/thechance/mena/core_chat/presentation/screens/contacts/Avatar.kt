package net.thechance.mena.core_chat.presentation.screens.contacts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import net.thechance.mena.designsystem.presentation.theme.theme.Theme

@Composable
fun Avatar(
    contactImageUri: String?,
    contactInitials: String,
    size: Int,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(size.dp)
            .clip(CircleShape)
            .background(
                color = Theme.colorScheme.background.surfaceLow
            ) ,
        contentAlignment = Alignment.Center
    ) {
        if (contactImageUri != null) {
            AsyncImage(
                model = contactImageUri,
                contentDescription = "Contact photo",
                modifier = modifier
            )
        } else {
            Text(
                text = contactInitials,
                color = Theme.colorScheme.shadePrimary,
                style = Theme.typography.label.large,
            )
        }
    }
}


