package net.thechance.mena.core_chat.presentation.screen.syncContacts.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mena.core_chat_presentation.generated.resources.Res
import mena.core_chat_presentation.generated.resources.ic_arrow_left
import mena.core_chat_presentation.generated.resources.sync_contacts
import net.thechance.mena.designsystem.presentation.component.appBar.AppBar
import net.thechance.mena.designsystem.presentation.component.icon.MenaIcon
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun SyncContactsAppBar(
    onNavigateBack : () -> Unit,
    modifier: Modifier = Modifier,
) {
    AppBar(
        modifier = modifier,
        title = stringResource(Res.string.sync_contacts),
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
    )
}

