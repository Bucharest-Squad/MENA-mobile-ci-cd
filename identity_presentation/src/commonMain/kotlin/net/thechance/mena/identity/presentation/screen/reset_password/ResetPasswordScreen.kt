package net.thechance.mena.identity.presentation.screen.reset_password

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
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import net.thechance.mena.identity.presentation.base.BaseScreen
import net.thechance.mena.identity.presentation.components.PageDescription
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

class ResetPasswordScreen :
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
                modifier = Modifier.fillMaxSize().background(Theme.colorScheme.background.surface)
            ) {
                Image(
                    painter = painterResource(Res.drawable.login_background),
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxWidth().align(Alignment.BottomCenter)
                )

                AuthScreenContainer(
                    modifier = Modifier
                        .padding(top = Theme.spacing._24)
                        .padding(horizontal = Theme.spacing._16)
                ) {
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
                        hint = stringResource(Res.string.new_password_title),

                        leadingIcon = painterResource(Res.drawable.ic_lock),
                        trailingIcon = painterResource(
                            if (state.isNewPasswordVisible) Res.drawable.ic_open_eye
                            else Res.drawable.ic_close_eye
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password
                        ),
                        visualTransformation = if (state.isNewPasswordVisible) VisualTransformation.None
                        else PasswordVisualTransformation(),
                        onTrailingIconClick = listener::onNewPasswordVisibilityToggled,
                        modifier = Modifier.fillMaxWidth().padding(bottom = Theme.spacing._16)
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
                        hint = stringResource(Res.string.confirm_password_label),
                        leadingIcon = painterResource(Res.drawable.ic_lock),
                        trailingIcon = painterResource(
                            if (state.isConfirmPasswordVisible) Res.drawable.ic_open_eye
                            else Res.drawable.ic_close_eye
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        visualTransformation = if (state.isConfirmPasswordVisible) VisualTransformation.None
                        else PasswordVisualTransformation(),
                        onTrailingIconClick = listener::onConfirmPasswordVisibilityToggled,
                        modifier = Modifier.fillMaxWidth().padding(bottom = Theme.spacing._8)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    PrimaryButton(
                        text = stringResource(Res.string.reset),
                        onClick = listener::onResetPasswordClicked,
                        isEnabled = state.isResetEnabled,
                        isLoading = state.isLoading,
                        contentPadding = PaddingValues(vertical = Theme.spacing._12),
                        modifier = Modifier.fillMaxWidth().padding(bottom = Theme.spacing._32)
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
                        modifier = Modifier.fillMaxWidth().padding(top = Theme.spacing._12)
                            .padding(horizontal = Theme.spacing._16)
                    )
                }

                LaunchedEffect(state.errorMessage) {
                    if (state.errorMessage != null) {
                        delay(3000)
                    }
                }
            }
        }
    }

    override fun onEffect(
        effect: ResetPasswordScreenUIEffect, navigator: Navigator
    ) {
        when (effect) {
            ResetPasswordScreenUIEffect.NavigateBackToLogin -> navigator.pop()
        }
    }
}

@Composable
fun AuthScreenContainer(
    modifier: Modifier = Modifier, content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(), content = content
    )
}

val MockState = ResetPasswordScreenUIState(
    newPassword = "Password123",
    confirmPassword = "Password123",
    isResetEnabled = true,
    errorMessage = null
)

@Preview
@Composable
fun PreviewResetPasswordScreen() {
    MenaTheme {
        ResetPasswordScreen().OnRender(
            state = MockState, listener = object : ResetPasswordScreenInteractionListener {
                override fun onNewPasswordChanged(password: String) {
                    TODO("Not yet implemented")
                }

                override fun onConfirmPasswordChanged(password: String) {
                    TODO("Not yet implemented")
                }

                override fun onNewPasswordVisibilityToggled() {
                    TODO("Not yet implemented")
                }

                override fun onConfirmPasswordVisibilityToggled() {
                    TODO("Not yet implemented")
                }

                override fun onResetPasswordClicked() {
                    TODO("Not yet implemented")
                }

                override fun onBackClicked() {
                    TODO("Not yet implemented")
                }
            })
    }
}