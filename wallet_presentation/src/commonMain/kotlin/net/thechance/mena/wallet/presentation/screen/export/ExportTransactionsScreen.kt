package net.thechance.mena.wallet.presentation.screen.export

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import mena.wallet_presentation.generated.resources.Res
import mena.wallet_presentation.generated.resources.all_transactions
import mena.wallet_presentation.generated.resources.back_button
import mena.wallet_presentation.generated.resources.custom_filtering
import mena.wallet_presentation.generated.resources.download
import mena.wallet_presentation.generated.resources.export_transactions
import mena.wallet_presentation.generated.resources.ic_arrow_left
import mena.wallet_presentation.generated.resources.share
import mena.wallet_presentation.generated.resources.view_and_share
import net.thechance.mena.designsystem.presentation.component.appBar.AppBar
import net.thechance.mena.designsystem.presentation.component.button.OutlinedButton
import net.thechance.mena.designsystem.presentation.component.button.PrimaryButton
import net.thechance.mena.designsystem.presentation.component.icon.Icon
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.wallet.presentation.component.SnackBarContainer
import net.thechance.mena.wallet.presentation.component.WalletScaffold
import net.thechance.mena.wallet.presentation.component.filter.FilterContent
import net.thechance.mena.wallet.presentation.model.FilterStatus
import net.thechance.mena.wallet.presentation.model.FilterType
import net.thechance.mena.wallet.presentation.screen.export.component.CustomToast
import net.thechance.mena.wallet.presentation.screen.export.component.SelectCard
import net.thechance.mena.wallet.presentation.screen.export.component.horizontalDivider
import net.thechance.mena.wallet.presentation.utils.ObserveAsEffect
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ExportTransactionScreen(
    viewModel: ExportTransactionsViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEffect(
        effect = viewModel.uiEffect,
        onEffect = ::onExportTransactionsEffect
    )

    ExportTransactionScreenContent(
        state = state,
        interactionListener = viewModel
    )

}

@Composable
private fun ExportTransactionScreenContent(
    state: ExportTransactionsState,
    interactionListener: ExportTransactionsListener
) {

    WalletScaffold(
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            AppBar(
                title = stringResource(Res.string.export_transactions),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                leadingContent = {
                    Icon(
                        painter = painterResource(Res.drawable.ic_arrow_left),
                        contentDescription = stringResource(Res.string.back_button)
                    )
                },
                onLeadingClick = { interactionListener::onBackClicked },
            )
        },
        snackBar = {
            SnackBarContainer(snackBarState = state.snackBar)
        },
        toast = {
            CustomToast(
                toastState = state.toast,
            )
        }
    ) {
        Box {
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
                AnimatedVisibility(
                    visible = state.isCustomFilterCardSelected
                )
                {
                    Column {
                        horizontalDivider()
                        FilterContent(
                            selectedTypes = state.selectedTransactionsTypes,
                            selectedStatus = state.selectedTransactionsStatus,
                            fromDate = state.startDate ?: "",
                            toDate = state.endDate ?: "",
                            onTypeSelected = interactionListener::onTypeSelected,
                            onStatusSelected = interactionListener::onStatusSelected,
                            onFromClick = interactionListener::onFromDateClicked,
                            onToClick = interactionListener::onToDateClicked
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
    }
}

private fun onExportTransactionsEffect(effect: ExportTransactionsEffect) {
    when (effect) {
        is ExportTransactionsEffect.NavigateBack -> {/* TODO("Handle navigation back") */
        }

        is ExportTransactionsEffect.NavigateToViewFileScreen
            -> {/*TODO("Handle navigation to view file screen ") */
        }
    }
}

@Composable
@Preview
private fun ExportTransactionScreenPreview() {
    MenaTheme {
        ExportTransactionScreenContent(
            state = ExportTransactionsState(
                isCustomFilterCardSelected = true
            ),
            interactionListener = object : ExportTransactionsListener {
                override fun onBackClicked() {}
                override fun onAllTransactionsClicked() {}
                override fun onCustomFilteringClicked() {}
                override fun onTypeSelected(type: FilterType) {}
                override fun onStatusSelected(status: FilterStatus) {}
                override fun onFromDateClicked() {}
                override fun onToDateClicked() {}
                override fun onViewAndShareClicked() {}
                override fun onDownloadClicked() {}
            }
        )
    }
}