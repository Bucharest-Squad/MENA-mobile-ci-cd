package net.thechance.mena.admin_panel.presentation.screen.logout

import androidx.compose.runtime.Composable
import net.thechance.mena.admin_panel.presentation.component.Dialog
import net.thechance.mena.admin_panel.resources.Res
import net.thechance.mena.admin_panel.resources.confirm_logout_icon
import net.thechance.mena.admin_panel.resources.logout
import net.thechance.mena.admin_panel.resources.logout_dialog_icon
import net.thechance.mena.admin_panel.resources.logout_disc
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun LogoutScreen(
    interactionListener: LogoutInteractionListener
) {
    LogoutContent(
        interactionListener = interactionListener
    )
}

@Composable
private fun LogoutContent(
    interactionListener: LogoutInteractionListener
) {
    Dialog(
        dialogIcon = painterResource(Res.drawable.logout_dialog_icon),
        confirmationIcon = painterResource(Res.drawable.confirm_logout_icon),
        title = stringResource(Res.string.logout),
        description = stringResource(Res.string.logout_disc),
        confirmationButtonText = stringResource(Res.string.logout),
        onDismiss = interactionListener::onDismissLogoutDialog,
        onConfirm = interactionListener::onConfirmLogout
    )
}