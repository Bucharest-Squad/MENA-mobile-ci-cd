package net.thechance.mena.wallet.presentation.component.filter.datePicker

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.thechance.mena.designsystem.presentation.component.text.Text
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import net.thechance.mena.wallet.presentation.component.filter.datePicker.DateConstants.MAX_YEAR
import net.thechance.mena.wallet.presentation.component.filter.datePicker.DateConstants.MIN_YEAR
import net.thechance.mena.wallet.presentation.utils.getMonthName
import net.thechance.mena.wallet.presentation.utils.getNumberOfDaysInMonth
import kotlin.math.abs
import kotlin.math.absoluteValue

@Composable
fun WheelDatePicker(
    dayPagerState: PagerState,
    monthPagerState: PagerState,
    yearPagerState: PagerState,
    modifier: Modifier = Modifier
) {
    val days = remember(monthPagerState.currentPage, yearPagerState.currentPage) {
        val month = monthPagerState.currentPage + 1
        val year = yearPagerState.currentPage + MIN_YEAR
        (1..getNumberOfDaysInMonth(year, month))
            .map { number -> if (number < 10) "0$number" else number.toString() }
    }

    LaunchedEffect(days.size) {
        if (dayPagerState.currentPage >= days.size) {
            dayPagerState.scrollToPage(days.lastIndex)
        }
    }

    var rowWidth by remember { mutableStateOf(0) }


    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp),
        contentAlignment = Alignment.Center
    ) {

        ChoiceIndicator(
            modifier = Modifier
                .width(with(LocalDensity.current) { rowWidth.toDp() })
                .fillMaxHeight()
        )

        Row(
            modifier = Modifier
                .onSizeChanged { rowWidth = it.width }
                .align(Alignment.Center),
            horizontalArrangement = Arrangement.spacedBy(27.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            VerticalPicker(
                state = dayPagerState,
                options = days
            )

            VerticalPicker(
                state = monthPagerState,
                options = (1..12).map { getMonthName(it) }
            )

            VerticalPicker(
                state = yearPagerState,
                options = (MIN_YEAR..MAX_YEAR).map { it.toString() }
            )

        }
    }
}

@Composable
private fun ChoiceIndicator(modifier: Modifier = Modifier) {
    val borderColor = Theme.colorScheme.stroke
    Canvas(
        modifier = modifier
    ) {
        val centerY = size.height / 2f
        val itemHeight = 40.dp.toPx()
        val topBorderY = centerY - (itemHeight / 2f)
        val bottomBorderY = centerY + (itemHeight / 2f)

        val strokeWidth = 1.dp.toPx()

        drawLine(
            color = borderColor,
            start = Offset(0f, topBorderY),
            end = Offset(size.width, topBorderY),
            strokeWidth = strokeWidth
        )

        drawLine(
            color = borderColor,
            start = Offset(0f, bottomBorderY),
            end = Offset(size.width, bottomBorderY),
            strokeWidth = strokeWidth
        )
    }
}

@Composable
private fun VerticalPicker(
    state: PagerState,
    options: List<String>,
    modifier: Modifier = Modifier,
) {
    val textMeasurer = rememberTextMeasurer()
    val baseStyle = Theme.typography.body.large

    val maxWidth = remember(options) {
        options.maxOf { option ->
            textMeasurer.measure(
                text = option,
                style = baseStyle
            ).size.width
        }
    }

    Box(
        modifier = modifier
            .width(with(LocalDensity.current) { maxWidth.toDp() })
            .fillMaxHeight()
    ) {
        VerticalPager(
            state = state,
            modifier = Modifier.fillMaxHeight().wrapContentWidth(),
            beyondViewportPageCount = 5,
            horizontalAlignment = Alignment.CenterHorizontally,
            pageSpacing = 4.dp,
            contentPadding = PaddingValues(vertical = 80.dp),
            pageSize = PageSize.Fixed(40.dp),
            flingBehavior = PagerDefaults.flingBehavior(
                state = state,
                pagerSnapDistance = PagerSnapDistance.atMost(1),
                snapAnimationSpec = tween(durationMillis = 200, easing = LinearOutSlowInEasing)
            )
        ) { page ->
            val pageOffset by remember {
                derivedStateOf {
                    ((state.currentPage - page) + state.currentPageOffsetFraction).absoluteValue
                }
            }

            val baseStyle = Theme.typography.body.large
            val distance = abs(state.currentPage - page)
            val fontSize = (baseStyle.fontSize.value - distance).coerceAtLeast(14f).sp

            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .wrapContentWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = options.getOrNull(page) ?: "",
                    style = baseStyle.copy(fontSize = fontSize),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start,
                    color = Theme.colorScheme.shadePrimary
                )
            }
        }
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xE6F2F4F7),
                            Color.Transparent,
                            Color(0xFFF2F4F7)
                        )
                    )
                )
        )
    }
}
