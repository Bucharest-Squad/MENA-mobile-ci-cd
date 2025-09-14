package net.thechance.mena.core_chat.presentation.screen.contacts.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mena.core_chat_presentation.generated.resources.Res
import mena.core_chat_presentation.generated.resources.contacts_title
import mena.core_chat_presentation.generated.resources.ic_arrow_left
import mena.core_chat_presentation.generated.resources.ic_resync
import net.thechance.mena.designsystem.presentation.component.appBar.AppBar
import net.thechance.mena.designsystem.presentation.component.appBar.AppBarOptionContainer
import net.thechance.mena.designsystem.presentation.component.icon.MenaIcon
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ContactsAppBar(
    onResyncClick: () -> Unit,
    onNavigateBack: () -> Unit,
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
}
