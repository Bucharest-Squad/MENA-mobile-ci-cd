package net.thechance.mena.admin_panel.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import net.thechance.mena.admin_panel.presentation.screen.logout.LogoutInteractionListener
import net.thechance.mena.admin_panel.presentation.screen.logout.LogoutScreen
import net.thechance.mena.designsystem.presentation.component.button.Button
import net.thechance.mena.designsystem.presentation.component.text.Text
import net.thechance.mena.designsystem.presentation.theme.theme.Theme

@Composable
fun ExampleScreen(){
    var isLogoutDialogHidden by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .background(Theme.colorScheme.background.surface)
            .safeContentPadding()
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(onClick = {isLogoutDialogHidden = false}) {
            Text("Click me", style = Theme.typography.body.large)
        }
    }

    if (!isLogoutDialogHidden) {
        LogoutScreen(
            interactionListener = object : LogoutInteractionListener {
                override fun onDismissLogoutDialog() {
                    isLogoutDialogHidden = true
                }

                override fun onConfirmLogout() {
                    isLogoutDialogHidden = true
                }
            }
        )
    }
}
