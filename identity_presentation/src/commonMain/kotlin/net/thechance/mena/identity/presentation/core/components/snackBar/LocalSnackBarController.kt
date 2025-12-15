package net.thechance.mena.identity.presentation.core.components.snackBar

import androidx.compose.runtime.staticCompositionLocalOf

val LocalSnackBarController = staticCompositionLocalOf<IdentitySnackBarController> {
    error("No SnackBarController provided")
}