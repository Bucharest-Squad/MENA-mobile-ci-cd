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
import net.thechance.mena.wallet.presentation.base.UiState
import net.thechance.mena.wallet.presentation.screen.wallet.component.BalanceCard
import net.thechance.mena.wallet.presentation.utils.ObserveAsEffect
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun WalletScreen(
    viewModel: WalletViewModel
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEffect(
        effect = viewModel.uiEffect,
        onEffect = ::onWalletEffect
    )

    WalletContent(
        state = state,
        interactionListener = viewModel
    )
}
@Composable
private fun WalletContent(
    state: WalletScreenState,
    interactionListener: WalletInteractionListener
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colorScheme.background.surface)
            .statusBarsPadding()
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
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        ) {
            BalanceCard(
                balance = state.balance,
                onRetry = interactionListener::onRetryLoadBalanceClicked,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
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
            state = WalletScreenState(
                balance = UiState.Success(530320.55)
            ),
            interactionListener = object : WalletInteractionListener {
                override fun onBackClicked() {}
                override fun onRetryLoadBalanceClicked() {}
            }
        )
    }
}