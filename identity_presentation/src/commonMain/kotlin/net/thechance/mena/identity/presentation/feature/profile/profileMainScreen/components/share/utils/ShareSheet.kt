package net.thechance.mena.identity.presentation.feature.profile.profileMainScreen.components.share.utils

import androidx.compose.runtime.Composable

@Composable
expect fun ShareSheet(title: String, message: String, shareLink: String, onDismiss: () -> Unit)
