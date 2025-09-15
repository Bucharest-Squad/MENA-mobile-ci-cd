package net.thechance.mena

import androidx.compose.runtime.Composable
import net.thechance.mena.core_chat.presentation.navigation.ChatNavHost
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MenaTheme {
        ChatNavHost()
    }
}