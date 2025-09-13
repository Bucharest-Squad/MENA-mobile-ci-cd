package net.thechance.mena.dukan.presentation.screen.createDukan.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mena.dukan_presentation.generated.resources.Res
import mena.dukan_presentation.generated.resources.ic_arrow_left
import mena.dukan_presentation.generated.resources.ic_edit
import net.thechance.mena.designsystem.presentation.component.appBar.AppBar
import net.thechance.mena.designsystem.presentation.component.button.PrimaryButton
import net.thechance.mena.designsystem.presentation.component.snackbar.SnackBar
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import net.thechance.mena.dukan.presentation.viewModel.createDukan.CreateDukanInteractionListener
import net.thechance.mena.dukan.presentation.viewModel.createDukan.CreateDukanUiState
import net.thechance.mena.dukan.presentation.viewModel.createDukan.CreateDukanViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun CreateDukanContent(
    state: CreateDukanUiState,
    listener: CreateDukanInteractionListener
) {

    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { CreateDukanViewModel.MAX_STEPS }
    )

    SyncPageWithScreenState(
        state = state,
        pagerState = pagerState
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colorScheme.background.surface)
            .systemBarsPadding()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            AppBar(
                title = stringResource(Res.string.create_new_dukan),
                onLeadingClick = listener::onBackClicked,
                leadingContent = {
                    Icon(
                        painter = painterResource(Res.drawable.ic_arrow_left),
                        contentDescription = "Back Arrow"
                    )
                }
            )
            HorizontalPager(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(top = Theme.spacing._12),
                state = pagerState,
                userScrollEnabled = false
            ) { pageIndex ->
                when (pageIndex) {
                    CreateDukanViewModel.BASIC_INFORMATION_INDEX -> CreateDukanContentBasicInformation(
                        state,
                        listener
                    )

                    CreateDukanViewModel.SELECT_IMAGE_INDEX -> CreateDukanContentSelectImage()
                    CreateDukanViewModel.SELECT_LOCATION_INDEX -> CreateDukanContentSelectLocation()
                    CreateDukanViewModel.SELECT_STYLE_INDEX -> CreateDukanContentSelectStyle()
                }
            }

            PrimaryButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Theme.spacing._16),
                text = if (state.currentStep == CreateDukanViewModel.SELECT_STYLE_INDEX)
                    "Create"
                else
                    "Next",
                onClick = listener::onButtonClicked,
                trailingIcon = painterResource(Res.drawable.ic_edit),
                isEnabled = state.isButtonEnabled,
                isLoading = state.isButtonLoading
            )
        }
        ErrorSnackBar(state)
    }
}

@Composable
private fun ErrorSnackBar(state: CreateDukanUiState) {
    if (state.showSnackBar) {
        SnackBar(
            title = stringResource(Res.string.error),
            message = stringResource(Res.string.dukan_name_is_already_exist),
            leadingIcon = painterResource(Res.drawable.ic_error),
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 12.dp),
            tint = Theme.colorScheme.error,
            contentDescription = stringResource(Res.string.error)
        )
    }
}

@Composable
private fun SyncPageWithScreenState(
    state: CreateDukanUiState,
    pagerState: PagerState
) {
    val currentStepIndex = state.currentStep
    LaunchedEffect(currentStepIndex) {
        if (currentStepIndex == pagerState.currentPage) return@LaunchedEffect
        try {
            pagerState.animateScrollToPage(currentStepIndex)
        } catch (_: Exception) {
        }
    }
}