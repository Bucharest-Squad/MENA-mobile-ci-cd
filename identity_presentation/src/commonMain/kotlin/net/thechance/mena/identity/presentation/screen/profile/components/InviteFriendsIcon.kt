package net.thechance.mena.identity.presentation.screen.profile.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import mena.identity_presentation.generated.resources.Res
import mena.identity_presentation.generated.resources.ic_invite_friends
import mena.identity_presentation.generated.resources.profile_invite_friends_icon_content_description
import net.thechance.mena.designsystem.presentation.component.icon.Icon
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun InviteFriendsIcon() {
    Icon(
        modifier = Modifier
            .clip(RoundedCornerShape(Theme.radius.md))
            .background(Theme.colorScheme.background.surfaceLow.copy(alpha = .12f))
            .padding(12.dp)
            .size(24.dp)
        ,
        painter = painterResource(Res.drawable.ic_invite_friends),
        contentDescription = stringResource(Res.string.profile_invite_friends_icon_content_description),
    )
}

@Preview
@Composable
fun PreviewInviteFriendsIcon() {
    MenaTheme {
        InviteFriendsIcon()
    }
}
