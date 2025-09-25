package net.thechance.mena.identity.presentation.screen.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import mena.identity_presentation.generated.resources.Res
import mena.identity_presentation.generated.resources.ic_address
import mena.identity_presentation.generated.resources.ic_edit_profile_info
import mena.identity_presentation.generated.resources.ic_password_lock
import mena.identity_presentation.generated.resources.ic_privacy_settings
import mena.identity_presentation.generated.resources.profile_account_settings_header
import mena.identity_presentation.generated.resources.profile_addresses
import mena.identity_presentation.generated.resources.profile_change_password
import mena.identity_presentation.generated.resources.profile_edit_profile_info
import mena.identity_presentation.generated.resources.profile_privacy_settings
import net.thechance.mena.designsystem.presentation.component.text.Text
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import net.thechance.mena.identity.presentation.screen.profile.ProfileScreenInteractionListener
import net.thechance.mena.identity.presentation.screen.profile.ProfileScreenModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun AccountSettingsSection(listener: ProfileScreenInteractionListener) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(Res.string.profile_account_settings_header),
            style = Theme.typography.label.medium,
            color = Theme.colorScheme.shadeSecondary,
            textAlign = TextAlign.Center,
        )
        SettingItem(
            title = stringResource(Res.string.profile_edit_profile_info),
            leadingIcon = painterResource(Res.drawable.ic_edit_profile_info),
            onClick = listener::onEditProfileInfoClicked,
        )
        SettingItem(
            title = stringResource(Res.string.profile_change_password),
            leadingIcon = painterResource(Res.drawable.ic_password_lock),
            onClick = listener::onChangePasswordClicked,
        )
        SettingItem(
            title = stringResource(Res.string.profile_addresses),
            leadingIcon = painterResource(Res.drawable.ic_address),
            onClick = listener::onAddressesClicked,
        )
        SettingItem(
            title = stringResource(Res.string.profile_privacy_settings),
            leadingIcon = painterResource(Res.drawable.ic_privacy_settings),
            onClick = listener::onPrivacySettingsClicked,
        )
    }
}

@Preview
@Composable
fun PreviewAccountSettingsSection() {
    MenaTheme {
        val fakeListener = ProfileScreenModel()
        AccountSettingsSection(listener = fakeListener)
    }
}
