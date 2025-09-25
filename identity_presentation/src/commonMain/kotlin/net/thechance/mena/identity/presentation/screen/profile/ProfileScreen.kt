package net.thechance.mena.identity.presentation.screen.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import mena.identity_presentation.generated.resources.Res
import mena.identity_presentation.generated.resources.error
import mena.identity_presentation.generated.resources.ic_close_circle
import mena.identity_presentation.generated.resources.profile_title
import net.thechance.mena.designsystem.presentation.component.appBar.AppBar
import net.thechance.mena.designsystem.presentation.component.dialog.Dialog
import net.thechance.mena.designsystem.presentation.component.scaffold.Scaffold
import net.thechance.mena.designsystem.presentation.component.snackbar.SnackBar
import net.thechance.mena.designsystem.presentation.component.text.Text
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import net.thechance.mena.identity.presentation.base.BaseScreen
import net.thechance.mena.identity.presentation.screen.profile.components.AccountSettingsSection
import net.thechance.mena.identity.presentation.screen.profile.components.AppSettingsSection
import net.thechance.mena.identity.presentation.screen.profile.components.InviteFriendsCard
import net.thechance.mena.identity.presentation.screen.profile.components.OtherSettingsSection
import net.thechance.mena.identity.presentation.screen.profile.components.ProfileInfoContainer
import net.thechance.mena.identity.presentation.screen.profile.components.ShareIcon
import net.thechance.mena.identity.presentation.screen.register.RegisterScreen
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

class ProfileScreen :
    BaseScreen<ProfileScreenModel, ProfileScreenUIState, ProfileScreenUIEffect, ProfileScreenInteractionListener>() {
    @Composable
    override fun Content() {
        InitScreen(getScreenModel())
    }

    @Composable
    override fun OnRender(
        state: ProfileScreenUIState,
        listener: ProfileScreenInteractionListener,
    ) {
        Scaffold(overlays = {
            bottomSheet(
                isVisible = state.showShareBottomSheet
            ){
                Box(Modifier.fillMaxSize()){
                    Text(text = "BOTTOM SHEET NOT YET IMPLEMENTED"
                    , style = Theme.typography.label.small
                    )
                }
            }
            dialog(state.showLanguageDialog) {
                Dialog(
                    title = "HI",
                    message = "Not Yet Implemented",
                    buttonText = "OK",
                    onDismiss = listener::onDismissLanguageDialog,
                    onCancelClick = listener::onDismissLanguageDialog
                )
            }
            dialog(state.showThemeDialog) {
                Dialog(
                    title = "HI",
                    message = "Not Yet Implemented",
                    buttonText = "OK",
                    onDismiss = listener::onDismissThemeDialog,
                    onCancelClick = listener::onDismissThemeDialog
                )
            }
        }) {
            Box(
                modifier = Modifier.fillMaxSize().systemBarsPadding()
            ) {
                Column(
                    Modifier.fillMaxSize()
                        .background(Theme.colorScheme.background.surface)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    AppBar(
                        title = stringResource(Res.string.profile_title),
                        trailingContent = { ShareIcon(onClick = listener::onShareClicked) }
                    )
                    ProfileInfoContainer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        profilePicture = "https://images.unsplash.com/photo-1743701168206-bd617221b559?q=80&w=814&auto=format&fit=crop&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
                        fullName = state.fullName,
                        userName = state.userName,
                    )
                    InviteFriendsCard(onCLick = listener::onInviteFriendsClicked)
                    AccountSettingsSection(listener)
                    AppSettingsSection(listener)
                    OtherSettingsSection(listener)
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                            .padding(vertical = 16.dp),
                        text = "version 1.0",
                        style = Theme.typography.label.small,
                        color = Theme.colorScheme.shadeSecondary,
                    )
                }
                AnimatedVisibility(
                    visible = state.errorMessage?.isNotEmpty() ?: false,
                    enter = slideInHorizontally(initialOffsetX = { it }),
                    exit = slideOutHorizontally(targetOffsetX = { it }),
                    modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter)
                ) {
                    SnackBar(
                        title = stringResource(Res.string.error),
                        message = state.errorMessage ?: "",
                        leadingIcon = painterResource(Res.drawable.ic_close_circle),
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                            .padding(horizontal = 16.dp)
                    )
                }
            }
        }
    }

    override fun onEffect(
        effect: ProfileScreenUIEffect, navigator: Navigator
    ) {
        when (effect) {
            ProfileScreenUIEffect.NavigateEditProfileScreen -> {navigator.push(RegisterScreen())}
            ProfileScreenUIEffect.NavigateToLocationPickerScreen -> {navigator.push(RegisterScreen())}
            ProfileScreenUIEffect.NavigateContactUsScreen -> {navigator.push(RegisterScreen())}
            ProfileScreenUIEffect.NavigateToChangePasswordScreen -> {navigator.push(RegisterScreen())}
            ProfileScreenUIEffect.NavigateToPrivacyAndPolicyScreen -> {navigator.push(RegisterScreen())}

        }
    }
}
