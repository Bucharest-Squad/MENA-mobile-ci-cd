package net.thechance.mena.core_chat.presentation.navigation

import androidx.navigation.NavOptions

sealed class ChatEffect {
    data class Navigate(val route: ChatRoute, val navOptions: NavOptions? = null) : ChatEffect()
    data class PopBackStack(val arguments: Map<String, Any> = emptyMap()) : ChatEffect()
    data class ShowSnackBar(val message: String) : ChatEffect()
}