package net.thechance.mena.core_chat.presentation.navigation

import androidx.navigation.NavOptions
import kotlinx.coroutines.flow.Flow
import net.thechance.mena.core_chat.presentation.components.SnackBarData

interface ChatEffector {
    val chatEffect: Flow<ChatEffect>
    suspend fun navigate(route: ChatRoute, navOptions: NavOptions? = null, forceNavigate: Boolean = false)
    suspend fun popBackStack(vararg arguments: Pair<String, Any>)
    suspend fun showSnackBar(snackBarData: SnackBarData)
}