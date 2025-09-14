package net.thechance.mena.identity.presentation.screen.login

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import net.thechance.mena.identity.presentation.base.BaseScreen

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

    }

    override fun onEffect(
        effect: LoginScreenUIEffect,
        navigator: Navigator
    ) {

    }
}