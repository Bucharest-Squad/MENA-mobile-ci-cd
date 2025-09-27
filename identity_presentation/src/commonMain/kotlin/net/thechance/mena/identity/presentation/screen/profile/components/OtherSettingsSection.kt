package net.thechance.mena.identity.presentation.screen.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import mena.identity_presentation.generated.resources.Res
import mena.identity_presentation.generated.resources.ic_contact_us
import mena.identity_presentation.generated.resources.ic_privacy_and_policies
import mena.identity_presentation.generated.resources.profile_contact_us
import mena.identity_presentation.generated.resources.profile_other_header
import mena.identity_presentation.generated.resources.profile_privacy_and_policy
import net.thechance.mena.designsystem.presentation.component.text.Text
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import net.thechance.mena.identity.presentation.screen.profile.ProfileScreenInteractionListener
import net.thechance.mena.identity.presentation.screen.profile.ProfileScreenViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun OtherSettingsSection(listener: ProfileScreenInteractionListener) {
    Column(
        modifier = Modifier.padding(top = 24.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(Res.string.profile_other_header),
            style = Theme.typography.label.medium,
            color = Theme.colorScheme.shadeSecondary,
            textAlign = TextAlign.Center,
        )
        SettingItem(
            title = stringResource(Res.string.profile_privacy_and_policy),
            leadingIcon = painterResource(Res.drawable.ic_privacy_and_policies),
            onClick = listener::onPrivacyAndPolicyClicked,
        )
        SettingItem(
            title = stringResource(Res.string.profile_contact_us),
            leadingIcon = painterResource(Res.drawable.ic_contact_us),
            onClick = listener::onContactUsClicked,
        )
    }
}

@Preview
@Composable
fun PreviewOtherSettingsSection() {
    MenaTheme {
        val fakeListener = ProfileScreenViewModel()
        OtherSettingsSection(listener = fakeListener)
    }
}
