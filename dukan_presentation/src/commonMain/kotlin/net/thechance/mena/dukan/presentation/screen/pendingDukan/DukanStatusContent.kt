package net.thechance.mena.dukan.presentation.screen.pendingDukan

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import mena.dukan_presentation.generated.resources.*
import net.thechance.mena.designsystem.presentation.component.image.Image
import net.thechance.mena.designsystem.presentation.component.text.Text
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import net.thechance.mena.dukan.presentation.component.AnnotatedText
import net.thechance.mena.dukan.presentation.viewModel.mainScreen.MainScreenUiState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

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
            dukanName = dukanState.name,
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

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(Theme.spacing._24),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item("pending") {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(Res.drawable.dukan_blur),
                    contentDescription = "dukan_pending_blur",
                    modifier = Modifier
                        .blur(30.dp, BlurredEdgeTreatment.Unbounded)
                        .offset(y = 20.dp)
                        .align(Alignment.BottomCenter)
                )
                Image(
                    painter = painterResource(Res.drawable.dukan_pending),
                    contentDescription = "dukan_pending",
                )
            }

            AnnotatedText(
                text = titleText,
                style = TextStyle(textAlign = TextAlign.Center),
                modifier = Modifier.padding(top = Theme.spacing._12)
            )

            Text(
                stringResource(Res.string.dukan_waiting_approval),
                style = Theme.typography.body.small,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(Theme.spacing._2)
            )
        }
    }
}

@Composable
private fun ApprovedContent(
    dukanName: String,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(Theme.spacing._24),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item("approved") {
            Image(
                painter = painterResource(Res.drawable.dukan_approved),
                contentDescription = "dukan_approved"
            )
            Text(
                text = "create dukan request it approved now",
                style = Theme.typography.title.medium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = Theme.spacing._12)
            )
        }
    }
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
