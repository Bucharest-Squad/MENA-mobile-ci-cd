package net.thechance.mena.wallet.presentation.screen.export

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mena.wallet_presentation.generated.resources.Res
import mena.wallet_presentation.generated.resources.back_button
import mena.wallet_presentation.generated.resources.download
import mena.wallet_presentation.generated.resources.ic_arrow_left
import mena.wallet_presentation.generated.resources.share
import net.thechance.mena.designsystem.presentation.component.appBar.AppBar
import net.thechance.mena.designsystem.presentation.component.button.OutlinedButton
import net.thechance.mena.designsystem.presentation.component.button.PrimaryButton
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.wallet.presentation.component.SnackBarContainer
import net.thechance.mena.wallet.presentation.component.WalletScaffold
import net.thechance.mena.wallet.presentation.screen.export.component.SelectCard
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
private fun ExportTransactionScreenContent(
    state: ExportTransactionsState
) {

    WalletScaffold(
        modifier = Modifier.statusBarsPadding(),
        topBar = {
            AppBar(
                title = "Export Transactions",
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                leadingContent = {
                    Icon(
                        painter = painterResource(Res.drawable.ic_arrow_left),
                        contentDescription = stringResource(Res.string.back_button)
                    )
                },
                onLeadingClick = {},
            )
        },
        snackBar = {
            SnackBarContainer(snackBarState = state.snackBar)
        }
    ) {
        Box() {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
            )
            {
                SelectCard(
                    cardText = "All Transactions",
                    modifier = Modifier.padding(bottom = 12.dp)
                )
                SelectCard(
                    cardText = "Custom Filtering",
                    isSelected = true,
                    isRadioButtonEnabled = true
                )
                Spacer(modifier = Modifier.weight(1f))

                OutlinedButton(
                    text = "View & Share",
                    trailingIcon = painterResource(Res.drawable.share),
                    onClick = {},
                    contentPadding = PaddingValues(
                        vertical = 13.dp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                )
                PrimaryButton(
                    text = "Download",
                    trailingIcon = painterResource(Res.drawable.download),
                    onClick = {},
                    contentPadding = PaddingValues(
                        vertical = 13.dp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
            }
        }
    }
}

@Composable
@Preview
private fun ExportTransactionScreenPreview() {
    MenaTheme {
        ExportTransactionScreenContent(
            state = ExportTransactionsState()
        )
    }
}