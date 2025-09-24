package net.thechance.mena.core_chat.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mena.core_chat_presentation.generated.resources.Res
import mena.core_chat_presentation.generated.resources.empty
import net.thechance.mena.core_chat.presentation.components.AnimatedSnackBarHost
import net.thechance.mena.core_chat.presentation.components.SnackBarData
import net.thechance.mena.core_chat.presentation.screen.chats.ChatsScreen
import net.thechance.mena.core_chat.presentation.screen.contacts.ContactsScreen
import net.thechance.mena.core_chat.presentation.screen.messaging.MessagingScreen
import net.thechance.mena.core_chat.presentation.screen.syncContacts.SyncContactsScreen
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.koin.compose.koinInject
import kotlin.collections.component1
import kotlin.collections.component2

val LocalNavController = staticCompositionLocalOf<NavController> {
    error("No NavController provided")
}

@Composable
fun ChatNavHost(
    chatEffector: ChatEffector = koinInject(),
) {

    val navController = rememberNavController()
    var snackBarDataState by remember {
        mutableStateOf(
            SnackBarData(
                title = Res.string.empty,
                message = Res.string.empty
            )
        )
    }
    var isSnackBarVisible by remember { mutableStateOf(false) }

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

            is ChatEffect.ShowSnackBar -> {
                snackBarDataState = effect.snackBarData
                isSnackBarVisible = true
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        CompositionLocalProvider(
            LocalNavController provides navController
        ) {
            NavHost(
                modifier = Modifier.fillMaxSize(),
                navController = navController,
                startDestination = MessagingRoute(0),
            ) {
                composable<ChatsRoute> { ChatsScreen() }
                composable<ContactsRoute> { ContactsScreen() }
                composable<SyncContactsRoute> { SyncContactsScreen() }
                composable<MessagingRoute> { MessagingScreen() }
            }
        }

        Box(
            modifier = Modifier.fillMaxSize().statusBarsPadding()
                .padding(horizontal = Theme.spacing._16),
            contentAlignment = Alignment.TopCenter
        ) {
            AnimatedSnackBarHost(
                isVisible = isSnackBarVisible,
                data = snackBarDataState,
                onDismiss = { isSnackBarVisible = false }
            )
        }
    }
}