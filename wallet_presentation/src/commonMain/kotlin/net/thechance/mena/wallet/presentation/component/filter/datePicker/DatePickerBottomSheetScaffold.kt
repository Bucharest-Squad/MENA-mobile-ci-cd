package net.thechance.mena.wallet.presentation.component.filter.datePicker

import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import net.thechance.mena.designsystem.presentation.component.bottomSheet.BottomSheet
import net.thechance.mena.designsystem.presentation.component.scaffold.Scaffold
import net.thechance.mena.wallet.presentation.component.filter.datePicker.DateConstants.MAX_YEAR
import net.thechance.mena.wallet.presentation.component.filter.datePicker.DateConstants.MIN_YEAR
import net.thechance.mena.wallet.presentation.component.filter.datePicker.DateConstants.START_YEAR
import net.thechance.mena.wallet.presentation.utils.getNumberOfDaysInMonth

@Composable
fun DatePickerBottomSheetScaffold() {
    var isSheetVisible by remember { mutableStateOf(false) }

    val monthPagerState = rememberPagerState(initialPage = 0, pageCount = { 12 })
    val yearPagerState = rememberPagerState(
        initialPage =  START_YEAR - MIN_YEAR,
        pageCount = { MAX_YEAR - MIN_YEAR + 1 }
    )

    val currentMonth = monthPagerState.currentPage + 1
    val currentYear = yearPagerState.currentPage + MIN_YEAR
    val daysInMonth = remember(currentMonth, currentYear) {
        getNumberOfDaysInMonth(currentYear, currentMonth)
    }

    val dayPagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { daysInMonth }
    )

    LaunchedEffect(daysInMonth) {
        if (dayPagerState.currentPage >= daysInMonth) {
            dayPagerState.scrollToPage(daysInMonth - 1)
        }
    }

    Scaffold(
        overlays = {
            bottomSheet(isVisible = isSheetVisible) {
                BottomSheet(
                    isVisible = isSheetVisible,
                    onDismissRequest = { isSheetVisible = false },
                    sheetContent = {
                        item {
                            DatePickerBottomSheetContent(
                                dayPagerState = dayPagerState,
                                monthPagerState = monthPagerState,
                                yearPagerState = yearPagerState,
                                onPickClick = { day, month, year ->
                                    /*TODO make listener function to save the date and close the bottom sheet*/
                                },
                            )
                        }
                    }
                )
            }
        }
    ) {
        /*TODO integrate with the main screen*/
    }
}


