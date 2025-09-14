package net.thechance.mena.core_chat.presentation.screen.chats

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import net.thechance.mena.core_chat.presentation.navigation.ContactsRoute
import net.thechance.mena.core_chat.presentation.navigation.LocalNavController
import net.thechance.mena.designsystem.presentation.component.button.Button

@Composable
fun ChatsScreen() {

    val navController = LocalNavController.current

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    )    {

        Button (onClick = { navController.navigate(ContactsRoute) }){
        }
    }
}
