package net.thechance.mena

import androidx.compose.runtime.Composable
import net.thechance.mena.appEntryPoint.DeepLink
import net.thechance.mena.appEntryPoint.EntryPoint
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App(deepLink: DeepLink) {
    MenaTheme {
        SetStatusBarIconsDark()
        EntryPoint(deepLink = deepLink)
    }
}
