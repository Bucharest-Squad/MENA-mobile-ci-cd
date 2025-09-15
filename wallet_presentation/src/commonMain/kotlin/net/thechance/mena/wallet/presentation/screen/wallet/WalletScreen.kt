package net.thechance.mena.wallet.presentation.screen.wallet

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import net.thechance.mena.wallet.presentation.utils.ObserveAsEffect
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
        onEvent = ::onWalletEffect
    )

    WalletContent(
        state = state,
        interactionListener = viewModel
    )
}
@Composable
private fun WalletContent(
    state: WalletUiState,
    interactionListener: WalletInteractionListener
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .background(Theme.colorScheme.background.surface)
    ) {
        AppBar(
            title = stringResource(Res.string.my_wallet),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            leadingContent = {
                Icon(
                    painter = painterResource(Res.drawable.ic_arrow_left),
                    contentDescription = stringResource(Res.string.back_button)
                )
            },
            onLeadingClick = interactionListener::onBackClicked,
        )

        AnimatedContent(
            targetState = state.isLoading,
            modifier = Modifier.weight(1f)
        ) { isLoading ->
            if (isLoading) {
                LoadingContent()
            } else {
                MainContent(state = state, interactionListener = interactionListener)
            }
        }
    }
}

@Composable
private fun LoadingContent() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            color = Theme.colorScheme.shadePrimary
        )
    }
}

@Composable
private fun MainContent(
    state: WalletUiState,
    interactionListener: WalletInteractionListener
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
    ) {
        BalanceCard(
            balance = state.balance,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}


private fun onWalletEffect(effect: WalletEffect) {
    when (effect) {
        is WalletEffect.NavigateBack -> TODO("Handle navigation back")
    }
}

@Preview
@Composable
private fun WalletScreenPreview() {
    MenaTheme {
        WalletContent(
            state = WalletUiState(
                isLoading = false,
                balance = 530320.55
            ),
            interactionListener = object : WalletInteractionListener {
                override fun onBackClicked() {}
            }
        )
    }
}