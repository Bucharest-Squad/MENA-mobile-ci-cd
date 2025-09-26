package net.thechance.mena.dukan.presentation.screen.pendingDukan

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import mena.dukan_presentation.generated.resources.Res
import mena.dukan_presentation.generated.resources.ic_arrow_left
import mena.dukan_presentation.generated.resources.my_dukan
import net.thechance.mena.designsystem.presentation.component.appBar.AppBar
import net.thechance.mena.designsystem.presentation.component.icon.Icon
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import net.thechance.mena.dukan.presentation.viewModel.mainScreen.MainScreenUiState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun PendingDukanScreen(
    dukanName: String,
    dukanStatus: MainScreenUiState.DukanStatusUi,
    onBackClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colorScheme.background.surface)
            .systemBarsPadding()
    ) {
        AppBar(
            title = stringResource(Res.string.my_dukan),
            leadingContent = {
                Icon(
                    painter = painterResource(Res.drawable.ic_arrow_left),
                    contentDescription = "left_arrow",
                )
            },
            onLeadingClick = onBackClick
        )

        Spacer(modifier = Modifier.weight(1f))

        DukanStatusContent(
            dukanState = MainScreenUiState.DukanState(
                name = dukanName,
                status = dukanStatus
            ),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(1f))
    }
}


@Preview
@Composable
private fun Preview() {
    MenaTheme {
        PendingDukanScreen(
            dukanName = "Calvin Klein",
            onBackClick = {},
            dukanStatus = MainScreenUiState.DukanStatusUi.Approved
        )
    }
}