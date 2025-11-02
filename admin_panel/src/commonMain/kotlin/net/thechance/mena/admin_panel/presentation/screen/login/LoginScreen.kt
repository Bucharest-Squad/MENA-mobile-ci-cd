package net.thechance.mena.admin_panel.presentation.screen.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import net.thechance.mena.admin_panel.navigation.nav_host.LocalAdminPanelNavController
import net.thechance.mena.admin_panel.navigation.routes.AdminPanel
import net.thechance.mena.admin_panel.navigation.routes.Login
import net.thechance.mena.designsystem.presentation.component.button.PrimaryButton

@Composable
fun LoginScreen(
) {
    val navController = LocalAdminPanelNavController.current
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        PrimaryButton(
            onClick = {
                navController.navigate(AdminPanel) {
                    popUpTo(Login) { inclusive = true }
                }
            },
            text = "Login"
        )
    }
}