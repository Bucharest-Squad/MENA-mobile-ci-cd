package net.thechance.mena.wallet.presentation.screen.wallet

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import mena.wallet_presentation.generated.resources.Res
import mena.wallet_presentation.generated.resources.back_button
import mena.wallet_presentation.generated.resources.ic_arrow_left
import mena.wallet_presentation.generated.resources.my_wallet
import net.thechance.mena.designsystem.presentation.component.appBar.AppBar
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import net.thechance.mena.wallet.presentation.screen.wallet.component.BalanceCard
import net.thechance.mena.wallet.presentation.util.ObserveAsEffect
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun WalletScreen(
    viewModel: WalletViewModel
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ObserveAsEffect(
        effect = viewModel.uiEffect,
        onEvent = ::onWalletScreenEvent
    )

    WalletScreenContent(
        state = state,
        interactionListener = viewModel
    )
}
@Composable
private fun WalletScreenContent(
    state: WalletUiState,
    interactionListener: WalletInteractionListener
) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(Theme.colorScheme.background.surface)
    ){
        AppBar(
            title = stringResource(Res.string.my_wallet),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            leadingContent = {
                Icon(
                    painter = painterResource(Res.drawable.ic_arrow_left),
                    contentDescription = stringResource(Res.string.back_button)
                )
            },
            onLeadingClick = interactionListener::onBackClick,
        )
        AnimatedContent(
            targetState = state.isLoading,
            modifier = Modifier.weight(1f)
        ) { isLoading ->
            if (isLoading) {
                Box(modifier = Modifier.fillMaxSize()) {
                    //TODO: replace with design loading animation
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center),
                        color = Theme.colorScheme.shadePrimary
                    )
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp),
                ) {
                    BalanceCard(
                        balance = state.balance,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }

        }
    }
}

private fun onWalletScreenEvent(event: WalletUiEvent) {
    when (event) {
        is WalletUiEvent.NavigateBack -> TODO("Handle navigation back")
    }
}

@Preview
@Composable
private fun WalletScreenPreview() {
    MenaTheme {
        WalletScreenContent(
            state = WalletUiState(isLoading = true),
            interactionListener = object : WalletInteractionListener {
                override fun onBackClick() {}
            }
        )
    }
}