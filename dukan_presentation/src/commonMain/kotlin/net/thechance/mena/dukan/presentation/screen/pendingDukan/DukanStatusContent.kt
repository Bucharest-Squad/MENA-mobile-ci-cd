package net.thechance.mena.dukan.presentation.screen.pendingDukan

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import mena.dukan_presentation.generated.resources.Res
import mena.dukan_presentation.generated.resources.approved_dukan
import mena.dukan_presentation.generated.resources.creating_shelf
import mena.dukan_presentation.generated.resources.dukan_approved_now
import mena.dukan_presentation.generated.resources.dukan_blur
import mena.dukan_presentation.generated.resources.dukan_pending
import mena.dukan_presentation.generated.resources.dukan_request_pending
import mena.dukan_presentation.generated.resources.dukan_waiting_approval
import net.thechance.mena.designsystem.presentation.component.text.Text
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import net.thechance.mena.dukan.presentation.component.AnnotatedText
import net.thechance.mena.dukan.presentation.component.ImageWithTextContainer
import net.thechance.mena.dukan.presentation.viewModel.mainScreen.MainScreenUiState
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DukanStatusContent(
    dukanState: MainScreenUiState.DukanState,
    modifier: Modifier = Modifier
) {
    when (dukanState.status) {
        MainScreenUiState.DukanStatusUi.Pending -> PendingContent(
            dukanName = dukanState.name,
            modifier = modifier
        )

        MainScreenUiState.DukanStatusUi.None -> {}

        MainScreenUiState.DukanStatusUi.Approved -> ApprovedContent(
            modifier = modifier
        )
    }
}

@Composable
private fun PendingContent(
    dukanName: String,
    modifier: Modifier = Modifier
) {
    val titleText = buildPendingDukanTitle(
        brandName = dukanName,
        titleTemplate = stringResource(Res.string.dukan_request_pending)
    )

    ImageWithTextContainer(
        blurImageRes = Res.drawable.dukan_blur,
        foregroundImageRes = Res.drawable.dukan_pending,
        header = {
            AnnotatedText(
                text = titleText,
                style = TextStyle(textAlign = TextAlign.Center)
            )
        },
        bodyText = stringResource(Res.string.dukan_waiting_approval),
        modifier = modifier
    )
}

@Composable
private fun ApprovedContent(
    modifier: Modifier = Modifier
) {
    ImageWithTextContainer(
        blurImageRes = Res.drawable.dukan_blur,
        foregroundImageRes = Res.drawable.approved_dukan,
        header = {
            Text(
                text = stringResource(Res.string.dukan_approved_now),
                style = Theme.typography.title.small,
                textAlign = TextAlign.Center
            )
        },
        bodyText = stringResource(Res.string.creating_shelf),
        modifier = modifier
    )
}

@Composable
private fun buildPendingDukanTitle(
    brandName: String,
    titleTemplate: String,
) = androidx.compose.ui.text.buildAnnotatedString {
    val parts = titleTemplate.split("%s")
    withStyle(Theme.typography.title.small.toSpanStyle()) {
        append(parts.getOrElse(0) { "" })
    }
    withStyle(Theme.typography.title.medium.toSpanStyle()) {
        append(brandName)
    }
    withStyle(Theme.typography.title.small.toSpanStyle()) {
        append(parts.getOrElse(1) { "" })
    }
}

@Preview
@Composable
private fun PendingDukanScreenPreview() {
    MenaTheme {
        DukanStatusContent(
            dukanState = MainScreenUiState.DukanState(
                name = "My Dukan",
                status = MainScreenUiState.DukanStatusUi.Pending
            )
        )
    }
}

@Preview
@Composable
private fun ApprovedDukanScreenPreview() {
    MenaTheme {
        DukanStatusContent(
            dukanState =
                MainScreenUiState.DukanState(
                    status = MainScreenUiState.DukanStatusUi.Approved
                )
        )
    }
}