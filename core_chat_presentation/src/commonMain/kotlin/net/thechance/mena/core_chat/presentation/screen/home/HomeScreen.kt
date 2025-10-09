package net.thechance.mena.core_chat.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mena.core_chat_presentation.generated.resources.Res
import mena.core_chat_presentation.generated.resources.chats
import mena.core_chat_presentation.generated.resources.ic_coin
import mena.core_chat_presentation.generated.resources.ic_plus
import mena.core_chat_presentation.generated.resources.mena
import net.thechance.mena.core_chat.presentation.screen.home.components.ChatCard
import net.thechance.mena.designsystem.presentation.component.appBar.AppBar
import net.thechance.mena.designsystem.presentation.component.button.FabButton
import net.thechance.mena.designsystem.presentation.component.icon.Icon
import net.thechance.mena.designsystem.presentation.component.text.Text
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@Composable
fun HomeScreen(
    // viewModel: HomeViewModel = koinViewModel<HomeViewModel>(),
    fakeData: List<HomeUiState>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .background(
                    color = Theme.colorScheme.background.surface
                )
        ) {
            AppBar(
                modifier = Modifier.fillMaxWidth().padding(vertical = 18.dp),
                title = stringResource(Res.string.mena),
                trailingContent = {
                    Text(
                        text = "${viewModel.state.value.balance}",
                        color = Theme.colorScheme.shadeSecondary,
                        style = Theme.typography.label.small,
                        modifier = Modifier.padding(vertical = Theme.spacing._4),
                    )
                    Icon(
                        painter = painterResource(Res.drawable.ic_coin),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp).clickable {
                            viewModel.onWalletClicked()
                        }
                    )
                }
            )
            Text(
                text = stringResource(Res.string.chats),
                color = Theme.colorScheme.shadePrimary,
                style = Theme.typography.title.small,
                modifier = Modifier.fillMaxWidth().padding(horizontal = Theme.spacing._16)
            )
            LazyColumn(
                modifier = Modifier.padding(horizontal = Theme.spacing._16)
                    .fillMaxWidth()
                    .weight(1f),
                contentPadding = PaddingValues(vertical = Theme.spacing._12),
                verticalArrangement = Arrangement.spacedBy(Theme.spacing._16)
            ) {
                items(fakeData.size) {
                    ChatCard(
                        chats = fakeData[it],
                    )
                }
            }
        }
        FabButton(
            painter = painterResource(Res.drawable.ic_plus),
            onClick = viewModel::onNewChatClicked,
            modifier = Modifier.align(Alignment.BottomEnd)
                .padding(Theme.spacing._16)
        )
    }
}

@Composable
@Preview
private fun HomeScreenPreview() {
    MenaTheme {
        val fakeData = listOf(
            HomeScreenState.HomeUiState(
                id = Uuid.random(),
                name = "Sara Mahmoud",
                imageUrl = "https://i.ibb.co/3y0TLrqV/f685ab4ed41c4c97bc4ffc6b3795175d4a29cd41.jpg",
                lastMessage = "Hello Noha, nice to meet u!!",
                time = "12:33 PM",
                status = HomeScreenState.HomeUiState.Status.UnRead(12),
                isMine = false,
            ),
            HomeScreenState.HomeUiState(
                id = Uuid.random(),
                name = "Maryam Saleh",
                imageUrl = "https://i.ibb.co/DjJLNHm/ef7bf477a8366d411f62a575dc169f0858ca1fec.jpg",
                lastMessage = "You: Ok, thanks 😊",
                time = "11:24 PM",
                status = HomeScreenState.HomeUiState.Status.Read,
                isMine = true,
            ),
            HomeScreenState.HomeUiState(
                id = Uuid.random(),
                name = "Ayad Saddon",
                imageUrl = null,
                lastMessage = "Do you think going out in the rain right no...",
                time = "Yesterday",
                status = HomeScreenState.HomeUiState.Status.UnRead(3),
                isMine = false,
            ),
            HomeScreenState.HomeUiState(
                id = Uuid.random(),
                name = "Aseel Rahman",
                imageUrl = "https://i.ibb.co/k2tY9Pcf/f538e7ed045b525721bd578ffa86d22fb58a9245.jpg",
                lastMessage = "Shared 10 photos",
                time = "17-03-2025",
                status = HomeScreenState.HomeUiState.Status.Received,
                isMine = false,
            ),
            HomeScreenState.HomeUiState(
                id = Uuid.random(),
                name = "The princess 👑",
                imageUrl = "https://i.ibb.co/XrjSgT95/6a9470e240d86a266e0d9fa666c6fce3e0659d2e.jpg",
                lastMessage = "Shared a link",
                time = "17-03-2025",
                status = HomeScreenState.HomeUiState.Status.Received,
                isMine = false,
            ),
            HomeScreenState.HomeUiState(
                id = Uuid.random(),
                name = "Mansour Mogyad Ibrahee...",
                imageUrl = "https://i.ibb.co/m5TCtW9w/afe181934c9bdfd994e1a0f1a9e8ecbb935908a9.jpg",
                lastMessage = "You: Send a photo",
                time = "17-03-2025",
                status = HomeScreenState.HomeUiState.Status.Sent,
                isMine = true,
            ),
            HomeScreenState.HomeUiState(
                id = Uuid.random(),
                name = "Maha Fares",
                imageUrl = "https://i.ibb.co/0RG3LYJB/10a780bcc5534f15472c4f67093c27d7ea30d861.jpg",
                lastMessage = "Shared a photo",
                time = "19-03-2025",
                status = HomeScreenState.HomeUiState.Status.Read,
                isMine = false,
            ),
            HomeScreenState.HomeUiState(
                id = Uuid.random(),
                name = "Aseel Rahman",
                imageUrl = "https://i.ibb.co/tPYDxg0L/cedbd762f4ea4d209b0187b254769d0b89988d8e.jpg",
                lastMessage = "★",
                time = "17-03-2025",
                status = HomeScreenState.HomeUiState.Status.UnRead(12),
                isMine = false,
            )
        )
        HomeScreen(
            fakeData
        )
    }
}