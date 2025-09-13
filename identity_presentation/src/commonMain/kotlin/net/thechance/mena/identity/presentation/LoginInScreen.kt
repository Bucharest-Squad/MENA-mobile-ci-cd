package net.thechance.mena.identity.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import net.thechance.mena.identity.data.repository.AuthenticationRepositoryImpl
import org.koin.compose.getKoin

@Composable
fun LoginScreen() {
    val repo = getKoin().get<AuthenticationRepositoryImpl>()
    val scope = rememberCoroutineScope()
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            modifier = Modifier,
            onClick = {
                println("onClick: ")
                scope.launch {
                    try {
                        repo.login("07701111111", "12341234")
                    } catch (e: Exception) {
                        println(e.message)
                    }
                }
            }
        ) {
            Text("Login")
        }
    }
}
