package net.thechance.mena.identity.presentation.screen.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import mena.identity_presentation.generated.resources.Iraq_flag
import mena.identity_presentation.generated.resources.Res
import mena.identity_presentation.generated.resources.forget_password
import mena.identity_presentation.generated.resources.ic_eye
import mena.identity_presentation.generated.resources.ic_lock
import mena.identity_presentation.generated.resources.login
import mena.identity_presentation.generated.resources.login_prompt
import mena.identity_presentation.generated.resources.password
import mena.identity_presentation.generated.resources.phone_number
import mena.identity_presentation.generated.resources.register_now
import mena.identity_presentation.generated.resources.register_prompt
import mena.identity_presentation.generated.resources.welcome_back
import net.thechance.mena.designsystem.presentation.component.button.PrimaryButton
import net.thechance.mena.designsystem.presentation.component.button.TextButton
import net.thechance.mena.designsystem.presentation.component.snackbar.SnackBar
import net.thechance.mena.designsystem.presentation.component.text.MenaText
import net.thechance.mena.designsystem.presentation.component.textField.TextField
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import net.thechance.mena.identity.presentation.base.BaseScreen
import net.thechance.mena.identity.presentation.screen.components.AuthPrompt
import net.thechance.mena.identity.presentation.screen.components.AuthScreenContainer
import net.thechance.mena.identity.presentation.screen.components.MenaLogo
import net.thechance.mena.identity.presentation.screen.components.PhoneNumberInput
import net.thechance.mena.identity.presentation.screen.components.TitleWithSubtitle
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

class LoginScreen : BaseScreen<
        LoginScreenModel,
        LoginScreenUIState,
        LoginScreenUIEffect,
        LoginScreenInteractionListener>() {
    @Composable
    override fun Content() {
        InitScreen(getScreenModel())
    }

    @Composable
    override fun OnRender(state: LoginScreenUIState, listener: LoginScreenInteractionListener) {

        AuthScreenContainer {
            MenaLogo()
            TitleWithSubtitle(
                title = stringResource(Res.string.welcome_back),
                subtitle = stringResource(Res.string.login_prompt),
            )
            MenaText(
                text = stringResource(Res.string.phone_number),
                style = Theme.typography.title.small,
                color = Theme.colorScheme.shadePrimary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp)
            )
            PhoneNumberInput(
                state.phoneCode,
                painterResource(Res.drawable.Iraq_flag),
                onCountryClick = listener::onPhoneCodeClicked,
                phoneNumber = state.phoneNumber,
                onPhoneChange = listener::onPhoneChanged
            )
            MenaText(
                text = stringResource(Res.string.password),
                style = Theme.typography.title.small,
                color = Theme.colorScheme.shadePrimary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp, bottom = 4.dp)
            )

            TextField(
                value = state.password,
                onValueChanged = listener::onPasswordChanged,
                placeholder = "",
                trailingIcon = painterResource(Res.drawable.ic_eye),
                leadingIcon = painterResource(Res.drawable.ic_lock),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                visualTransformation = if (state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation()
            )

            ForgetPasswordText(
                onClick = listener::onForgotPasswordClicked
            )

            Spacer(modifier = Modifier.weight(1f))

            PrimaryButton(
                text = stringResource(Res.string.login),
                onClick = listener::onLoginClicked,
                isEnabled = state.isLoginEnabled,
                contentPadding = PaddingValues(vertical = 13.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
            )
            AuthPrompt(
                message = stringResource(Res.string.register_prompt),
                actionLabel = stringResource(Res.string.register_now),
                onActionClick = listener::onRegisterClicked
            )
        }

    }

    override fun onEffect(
        effect: LoginScreenUIEffect,
        navigator: Navigator
    ) {
        when (effect) {
            is LoginScreenUIEffect.NavigateToRegister -> TODO()
            LoginScreenUIEffect.NavigateToForgotPassword -> TODO()
            LoginScreenUIEffect.NavigateToHome -> TODO()
            is LoginScreenUIEffect.ShowErrorMessage -> TODO()

        }

    }
}

@Composable
fun ForgetPasswordText(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 4.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        TextButton(
            text = stringResource(Res.string.forget_password),
            isEnabled = true,
            onClick = onClick,
            contentColor = Theme.colorScheme.shadePrimary,

        )
    }
}


