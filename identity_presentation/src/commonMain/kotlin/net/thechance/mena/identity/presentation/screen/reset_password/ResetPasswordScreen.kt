package net.thechance.mena.identity.presentation.screen.reset_password

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import cafe.adriel.voyager.navigator.Navigator
import kotlinx.coroutines.delay
import mena.identity_presentation.generated.resources.Res
import mena.identity_presentation.generated.resources.error
import mena.identity_presentation.generated.resources.ic_close_circle
import mena.identity_presentation.generated.resources.ic_close_eye
import mena.identity_presentation.generated.resources.ic_lock
import mena.identity_presentation.generated.resources.ic_open_eye
import mena.identity_presentation.generated.resources.mena_logo
import mena.identity_presentation.generated.resources.reset
import mena.identity_presentation.generated.resources.reset_password
import net.thechance.mena.designsystem.presentation.component.button.PrimaryButton
import net.thechance.mena.designsystem.presentation.component.button.TextButton
import net.thechance.mena.designsystem.presentation.component.scaffold.Scaffold
import net.thechance.mena.designsystem.presentation.component.snackbar.SnackBar
import net.thechance.mena.designsystem.presentation.component.text.Text
import net.thechance.mena.designsystem.presentation.component.textField.TextField
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import net.thechance.mena.identity.presentation.base.BaseScreen
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

class ResetPasswordScreen : BaseScreen<
        ResetPasswordScreenModel,
        ResetPasswordScreenUIState,
        ResetPasswordScreenUIEffect,
        ResetPasswordScreenInteractionListener>() {
    @Composable
    override fun Content() {
        InitScreen(getScreenModel())
    }

    @Composable
    fun CustomAppBar(onBack: () -> Unit, title: String, modifier: Modifier = Modifier) {
        Box(modifier = modifier.fillMaxWidth().height(56.dp)) {
            TextButton(
                text = "Back",
                onClick = onBack,
                contentColor = Theme.colorScheme.shadePrimary,
                modifier = Modifier.align(Alignment.CenterStart).padding(start = 16.dp)
            )
            Text(
                text = title,
                style = Theme.typography.title.medium,
                color = Theme.colorScheme.shadePrimary,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }

    @Composable
    fun AuthScreenContainer(
        modifier: Modifier = Modifier,
        content: @Composable ColumnScope.() -> Unit
    ) {
        Column(
            modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp),
            content = content
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun OnRender(
        state: ResetPasswordScreenUIState,
        listener: ResetPasswordScreenInteractionListener
    ) {
        Scaffold { ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 8.dp)
                    .systemBarsPadding()
            ) {
                CustomAppBar(
                    onBack = listener::onBackClicked,
                    title = stringResource(Res.string.reset_password),
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .zIndex(1f)
                )

                AuthScreenContainer(
                    modifier = Modifier.padding(top = 56.dp)
                ) {
                    Spacer(modifier = Modifier.height(16.dp))

                    Image(
                        painter = painterResource(Res.drawable.mena_logo),
                        contentDescription = "MENA Logo",
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(vertical = 32.dp)
                    )

                    Text(
                        text = "New password",
                        style = Theme.typography.headline.medium,
                        color = Theme.colorScheme.shadePrimary,
                        modifier = Modifier.fillMaxWidth().padding(top = 16.dp)
                    )
                    Text(
                        text = "Please enter your new password and make sure you remember it in the next time",
                        style = Theme.typography.body.medium,
                        color = Theme.colorScheme.shadeTertiary,
                        modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                    )

                    TextField(
                        value = state.newPassword,
                        onValueChanged = listener::onNewPasswordChanged,
                        hint = "New password",
                        leadingIcon = painterResource(Res.drawable.ic_lock),
                        trailingIcon = painterResource(
                            if (state.isNewPasswordVisible) Res.drawable.ic_open_eye
                            else Res.drawable.ic_close_eye
                        ),
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Password
                        ),
                        visualTransformation = if (state.isNewPasswordVisible) VisualTransformation.None
                        else PasswordVisualTransformation(),
                        onTrailingIconClick = listener::onNewPasswordVisibilityToggled,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                    )

                    TextField(
                        value = state.confirmPassword,
                        onValueChanged = listener::onConfirmPasswordChanged,
                        hint = "Confirm password",
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
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    PrimaryButton(
                        text = stringResource(Res.string.reset),
                        onClick = listener::onResetPasswordClicked,
                        isEnabled = state.isResetEnabled,
                        isLoading = state.isLoading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp)
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
                        message = state.errorMessage ?: "Invalid phone number.",
                        leadingIcon = painterResource(Res.drawable.ic_close_circle),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                            .padding(horizontal = 16.dp)
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
        effect: ResetPasswordScreenUIEffect,
        navigator: Navigator
    ) {
        when (effect) {
            ResetPasswordScreenUIEffect.NavigateBackToLogin -> navigator.pop()
        }
    }
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
            state = MockState,
            listener = object : ResetPasswordScreenInteractionListener {
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

                override fun clearErrorMessage() {
                    TODO("Not yet implemented")
                }
            }
        )
    }
}