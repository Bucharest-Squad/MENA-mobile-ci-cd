package net.thechance.mena.admin_panel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.rememberNavController
import net.thechance.mena.admin_panel.navigation.LocalNavController
import net.thechance.mena.admin_panel.presentation.screen.mainContainer.MainContainerScreen
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme

@Composable
fun App() {
    val navController = rememberNavController()

    MenaTheme {
        CompositionLocalProvider(LocalNavController provides navController) {
            MainContainerScreen(navController)
        }
    }
}