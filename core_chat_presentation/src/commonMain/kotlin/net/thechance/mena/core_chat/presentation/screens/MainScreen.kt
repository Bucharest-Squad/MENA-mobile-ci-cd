package net.thechance.mena.core_chat.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import net.thechance.mena.core_chat.presentation.navigation.ContactsRoute
import net.thechance.mena.core_chat.presentation.navigation.LocalNavController

@Composable
fun MainScreen() {

    val navController = LocalNavController.current

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    )    {

        Button (onClick = { navController.navigate(ContactsRoute) }){
            Text(text = "Show contacts")
        }
    }
}
