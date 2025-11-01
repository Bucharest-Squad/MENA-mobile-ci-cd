package net.thechance.mena.admin_panel.presentation.screen.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import net.thechance.mena.admin_panel.navigation.AdminPanelRoute
import net.thechance.mena.admin_panel.navigation.LocalAdminNavController
import net.thechance.mena.designsystem.presentation.component.button.PrimaryButton

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit
) {
    val navController = LocalAdminNavController.current
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        PrimaryButton(
            onClick = {
                navController.navigate(AdminPanelRoute.AdminRoot.route) {
                    popUpTo(AdminPanelRoute.Login.route) { inclusive = true }
                }
            },
            text = "Login"
        )
    }
}