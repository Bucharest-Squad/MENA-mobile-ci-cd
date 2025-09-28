package net.thechance.mena.wallet.presentation.screen.export.component

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mena.wallet_presentation.generated.resources.Res
import mena.wallet_presentation.generated.resources.all_transactions
import mena.wallet_presentation.generated.resources.custom_filtering
import mena.wallet_presentation.generated.resources.download
import mena.wallet_presentation.generated.resources.filter_crossfade
import mena.wallet_presentation.generated.resources.share
import mena.wallet_presentation.generated.resources.view_and_share
import net.thechance.mena.designsystem.presentation.component.button.OutlinedButton
import net.thechance.mena.designsystem.presentation.component.button.PrimaryButton
import net.thechance.mena.wallet.presentation.screen.export.ExportTransactionsListener
import net.thechance.mena.wallet.presentation.screen.export.ExportTransactionsState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ExportTransactionContentBody(
    state: ExportTransactionsState,
    interactionListener: ExportTransactionsListener
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp)
    )
    {
        SelectCard(
            cardText = stringResource(Res.string.all_transactions),
            onCardSelected = interactionListener::onAllTransactionsClicked,
            isSelected = (!state.isCustomFilterCardSelected),
            modifier = Modifier.padding(bottom = 12.dp)
        )
        SelectCard(
            cardText = stringResource(Res.string.custom_filtering),
            isSelected = state.isCustomFilterCardSelected,
            onCardSelected = interactionListener::onCustomFilteringClicked,
        )
        Crossfade(
            targetState = state.isCustomFilterCardSelected,
            label = stringResource(Res.string.filter_crossfade),

            )
        { visible ->
            if (visible) {
                FilterSection(
                    state = state,
                    interactionListener = interactionListener
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        OutlinedButton(
            text = stringResource(Res.string.view_and_share),
            trailingIcon = painterResource(Res.drawable.share),
            onClick = interactionListener::onViewAndShareClicked,
            isLoading = state.isViewAndShareLoading,
            isEnabled = state.isViewAndShareButtonEnabled,
            contentPadding = PaddingValues(
                vertical = 13.dp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
        )
        PrimaryButton(
            text = stringResource(Res.string.download),
            trailingIcon = painterResource(Res.drawable.download),
            onClick = interactionListener::onDownloadClicked,
            isLoading = state.isDownloadLoading,
            isEnabled = state.isDownloadButtonEnabled,
            contentPadding = PaddingValues(
                vertical = 13.dp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
    }
}