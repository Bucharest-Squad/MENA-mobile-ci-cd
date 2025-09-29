package net.thechance.mena.identity.presentation.screen.resetPassword

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import kotlinx.coroutines.delay
import mena.identity_presentation.generated.resources.Res
import mena.identity_presentation.generated.resources.confirm_password_label
import mena.identity_presentation.generated.resources.error
import mena.identity_presentation.generated.resources.ic_arrow_left
import mena.identity_presentation.generated.resources.ic_close_circle
import mena.identity_presentation.generated.resources.ic_close_eye
import mena.identity_presentation.generated.resources.ic_lock
import mena.identity_presentation.generated.resources.ic_open_eye
import mena.identity_presentation.generated.resources.login_background
import mena.identity_presentation.generated.resources.new_password_title
import mena.identity_presentation.generated.resources.reset
import mena.identity_presentation.generated.resources.reset_password
import mena.identity_presentation.generated.resources.reset_password_description
import net.thechance.mena.designsystem.presentation.component.appBar.AppBar
import net.thechance.mena.designsystem.presentation.component.button.PrimaryButton
import net.thechance.mena.designsystem.presentation.component.icon.Icon
import net.thechance.mena.designsystem.presentation.component.scaffold.Scaffold
import net.thechance.mena.designsystem.presentation.component.snackbar.SnackBar
import net.thechance.mena.designsystem.presentation.component.text.Text
import net.thechance.mena.designsystem.presentation.component.textField.TextField
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import net.thechance.mena.identity.domain.entity.PhoneNumber
import net.thechance.mena.identity.presentation.base.BaseScreen
import net.thechance.mena.identity.presentation.components.AuthScreenContainer
import net.thechance.mena.identity.presentation.components.PageDescription
import net.thechance.mena.identity.presentation.screen.login.LoginScreen
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

class ResetPasswordScreen(phoneNumber: PhoneNumber) :
    BaseScreen<ResetPasswordScreenViewModel, ResetPasswordScreenUIState, ResetPasswordScreenUIEffect, ResetPasswordScreenInteractionListener>() {
    @Composable
    override fun Content() {
        InitScreen(getScreenModel())
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun OnRender(
        state: ResetPasswordScreenUIState, listener: ResetPasswordScreenInteractionListener
    ) {
        Scaffold(
            topBar = {
                AppBar(
                    modifier = Modifier,
                    title = stringResource(Res.string.reset_password),
                    contentPadding = PaddingValues(
                        horizontal = Theme.spacing._12, vertical = Theme.spacing._8
                    ),
                    leadingContent = {
                        Icon(
                            painter = painterResource(Res.drawable.ic_arrow_left),
                            modifier = Modifier.size(20.dp),
                            contentDescription = null,
                            tint = Theme.colorScheme.primary.primary,
                        )
                    },
                    onLeadingClick = listener::onBackClicked
                )
            }) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Theme.colorScheme.background.surface)
            ) {
                Image(
                    painter = painterResource(Res.drawable.login_background),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                )

                AuthScreenContainer()
                {
                    PageDescription(
                        title = stringResource(Res.string.new_password_title),
                        subtitle = stringResource(Res.string.reset_password_description),
                    )

                    Text(
                        text = stringResource(Res.string.new_password_title),
                        style = Theme.typography.title.small,
                        color = Theme.colorScheme.shadePrimary,
                        modifier = Modifier.padding(bottom = Theme.spacing._4)
                    )

                    TextField(
                        value = state.newPassword,
                        onValueChanged = listener::onNewPasswordChanged,
                        hint = "",
                        trailingIcon = painterResource(
                            if (state.isNewPasswordVisible) Res.drawable.ic_open_eye
                            else Res.drawable.ic_close_eye
                        ),
                        leadingIcon = painterResource(Res.drawable.ic_lock),
                        showTrailingDivider = false,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = Theme.spacing._16),
                        visualTransformation = if (state.isNewPasswordVisible) VisualTransformation.None
                        else PasswordVisualTransformation(),
                        onTrailingIconClick = listener::onNewPasswordVisibilityToggled
                    )

                    Text(
                        text = stringResource(Res.string.confirm_password_label),
                        style = Theme.typography.title.small,
                        color = Theme.colorScheme.shadePrimary,
                        modifier = Modifier.padding(bottom = Theme.spacing._4)
                    )

                    TextField(
                        value = state.confirmPassword,
                        onValueChanged = listener::onConfirmPasswordChanged,
                        hint = "",
                        trailingIcon = painterResource(
                            if (state.isConfirmPasswordVisible) Res.drawable.ic_open_eye
                            else Res.drawable.ic_close_eye
                        ),
                        leadingIcon = painterResource(Res.drawable.ic_lock),
                        showTrailingDivider = false,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = if (state.isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        onTrailingIconClick = listener::onConfirmPasswordVisibilityToggled
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    PrimaryButton(
                        text = stringResource(Res.string.reset),
                        onClick = listener::onResetPasswordClicked,
                        isEnabled = state.isResetEnabled,
                        isLoading = state.isLoading,
                        contentPadding = PaddingValues(vertical = Theme.spacing._12),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = Theme.spacing._24)
                    )
                }

                AnimatedVisibility(
                    visible = state.errorMessage != null,
                    enter = slideInHorizontally(initialOffsetX = { it }),
                    exit = slideOutHorizontally(targetOffsetX = { it }),
                    modifier = Modifier.align(Alignment.TopCenter)
                ) {
                    SnackBar(
                        title = stringResource(Res.string.error),
                        message = state.errorMessage ?: "",
                        leadingIcon = painterResource(Res.drawable.ic_close_circle),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = Theme.spacing._12)
                            .padding(horizontal = Theme.spacing._16)
                    )
                }

                LaunchedEffect(state.errorMessage) {
                    if (state.errorMessage != null) {
                        delay(3000)
                        listener.clearErrorMessage()
                    }
                }
            }
        }
    }

    override fun onEffect(
        effect: ResetPasswordScreenUIEffect, navigator: Navigator
    ) {
        when (effect) {
            ResetPasswordScreenUIEffect.NavigateBackToLogin -> navigator.push(LoginScreen())
        }
    }
}