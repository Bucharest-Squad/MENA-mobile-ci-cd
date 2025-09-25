package net.thechance.mena.wallet.presentation.component.filter.datePicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import net.thechance.mena.wallet.presentation.component.filter.datePicker.DateConstants.MIN_YEAR
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun DatePickerBottomSheetContent(
    onPickClick: (Int, Int, Int) -> Unit,
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
            onLeadingClick = {/*TODO make listener function to handle navigating back*/ },
        )

        WheelDatePicker(
            dayPagerState = dayPagerState,
            monthPagerState = monthPagerState,
            yearPagerState = yearPagerState,
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = Theme.spacing._16)
                .align(Alignment.CenterHorizontally)
        )

        PickButton(
            onPickClick = {
                val day = dayPagerState.currentPage + 1
                val month = monthPagerState.currentPage + 1
                val year = MIN_YEAR + yearPagerState.currentPage
                onPickClick(day, month, year)
            },
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun PickButton(
    onPickClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onPickClick,
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = Theme.spacing._8),
        shape = RoundedCornerShape(Theme.radius.md),
        containerColor = Theme.colorScheme.primary.primary,
        contentColor = Theme.colorScheme.primary.onPrimary,
    ) { contentColor ->
        Text(
            text = stringResource(Res.string.pick),
            color = contentColor,
            style = Theme.typography.label.medium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(
                    vertical = 13.dp,
                    horizontal = Theme.spacing._24
                )
        )
    }
}


