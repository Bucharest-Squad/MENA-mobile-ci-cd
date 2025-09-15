package net.thechance.mena

import androidx.compose.runtime.Composable
import net.thechance.mena.core_chat.api.CoreChatApi
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.getKoin

@Composable
@Preview
fun App() {
//    KoinApplication(
//        application = {
//            modules(
//                chatDataModule,
//                chatPresentationModule,
//            )
//        }
//    ) {
        MenaTheme {
            val coreChatApi: CoreChatApi = getKoin().get()
            coreChatApi.launch()
        }
//    }
}