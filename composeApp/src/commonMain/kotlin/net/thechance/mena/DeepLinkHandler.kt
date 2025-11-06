package net.thechance.mena

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import net.thechance.mena.appEntryPoint.DeepLink

object DeepLinkHandler {
    var currentDeepLink: DeepLink? by mutableStateOf(null)
        private set

    fun saveDeepLink(deepLink: DeepLink) {
        currentDeepLink = deepLink
    }

}