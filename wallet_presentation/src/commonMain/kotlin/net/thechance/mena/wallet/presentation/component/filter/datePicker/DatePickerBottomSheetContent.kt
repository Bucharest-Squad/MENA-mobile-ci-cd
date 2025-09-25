package net.thechance.mena.wallet.presentation.component.filter.datePicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import mena.wallet_presentation.generated.resources.Res
import mena.wallet_presentation.generated.resources.back_button
import mena.wallet_presentation.generated.resources.ic_arrow_left
import mena.wallet_presentation.generated.resources.pick
import mena.wallet_presentation.generated.resources.pick_start_date
import net.thechance.mena.designsystem.presentation.component.appBar.AppBar
import net.thechance.mena.designsystem.presentation.component.button.Button
import net.thechance.mena.designsystem.presentation.component.button.PrimaryButton
import net.thechance.mena.designsystem.presentation.component.icon.Icon
import net.thechance.mena.designsystem.presentation.component.text.Text
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import net.thechance.mena.wallet.presentation.component.filter.datePicker.DateConstants.MIN_YEAR
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun DatePickerBottomSheetContent(
    onPickClick: (Int, Int, Int) -> Unit,
    onDismiss: () -> Unit,
    dayPagerState: PagerState,
    monthPagerState: PagerState,
    yearPagerState: PagerState,
) {
    Column(
        modifier = Modifier
            .navigationBarsPadding()
            .background(Theme.colorScheme.background.surface)
            .fillMaxWidth()
    ) {
        AppBar(
            title = stringResource(Res.string.pick_start_date),
            titleColor = Theme.colorScheme.shadePrimary,
            contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp),
            leadingContent = {
                Icon(
                    painter = painterResource(Res.drawable.ic_arrow_left),
                    contentDescription = stringResource(Res.string.back_button)
                )
            },
            onLeadingClick = onDismiss,
        )

        WheelDatePicker(
            dayPagerState = dayPagerState,
            monthPagerState = monthPagerState,
            yearPagerState = yearPagerState,
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = Theme.spacing._16)
                .align(Alignment.CenterHorizontally)
        )

        PrimaryButton(
            text = stringResource(Res.string.pick),
            contentPadding = PaddingValues(horizontal = Theme.spacing._24, vertical = 13.dp),
            onClick = {
                val day = dayPagerState.currentPage + 1
                val month = monthPagerState.currentPage + 1
                val year = MIN_YEAR + yearPagerState.currentPage
                onPickClick(day, month, year)
            },
            modifier = Modifier
                .padding(bottom = Theme.spacing._8)
                .fillMaxWidth()
        )

    }
}

@Preview()
@Composable
fun DatePickerBottomSheetContentPreview() {
    val dayPagerState = rememberPagerState(initialPage = 0, pageCount = { 31 })
    val monthPagerState = rememberPagerState(initialPage = 0, pageCount = { 12 })
    val yearPagerState = rememberPagerState(initialPage = 0, pageCount = { 12 })

    MenaTheme {
        DatePickerBottomSheetContent(
            onPickClick = { _, _, _ -> },
            onDismiss = {},
            dayPagerState = dayPagerState,
            monthPagerState = monthPagerState,
            yearPagerState = yearPagerState
        )
    }
}




