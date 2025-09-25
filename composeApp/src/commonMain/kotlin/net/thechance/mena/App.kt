package net.thechance.mena

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.identity.presentation.screen.login.LoginScreen
import net.thechance.mena.identity.presentation.screen.profile.ProfileScreen
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MenaTheme {
        Navigator(screen = ProfileScreen()) { navigator ->
            SetStatusBarIconsDark()
            FadeTransition(navigator)
        }
    }
}