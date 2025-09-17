package net.thechance.mena.core_chat.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.thechance.mena.core_chat.presentation.screen.chats.ChatsScreen
import net.thechance.mena.core_chat.presentation.screen.contacts.ContactsScreen
import net.thechance.mena.core_chat.presentation.screen.syncContacts.SyncContactsScreen
import org.koin.compose.koinInject
import kotlin.collections.component1
import kotlin.collections.component2

@Composable
fun ChatNavHost(
    chatEffector: ChatEffector = koinInject(),
) {

    val navController = rememberNavController()

    EffectHandler(chatEffector.chatEffect) { effect ->
        when (effect) {
            is ChatEffect.Navigate -> navController.navigate(
                route = effect.route, navOptions = effect.navOptions
            )

            is ChatEffect.PopBackStack -> {
                effect.arguments.forEach { (key, value) ->
                    navController.previousBackStackEntry
                        ?.savedStateHandle
                        ?.set(key, value)
                }
                navController.popBackStack()
            }

            is ChatEffect.ShowSnackBar -> {}
        }
    }

    CompositionLocalProvider(
        LocalNavController provides navController
    ) {
        NavHost(
            modifier = Modifier.fillMaxSize(),
            navController = navController,
            startDestination = ChatsRoute,
        ) {
            composable<ChatsRoute> { ChatsScreen() }
            composable<ContactsRoute> { ContactsScreen() }
            composable<SyncContactsRoute> { SyncContactsScreen() }
        }
    }
}