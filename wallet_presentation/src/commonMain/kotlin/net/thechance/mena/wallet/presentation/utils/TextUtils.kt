package net.thechance.mena.wallet.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.takeOrElse

@Composable
fun calculateTextHeight(
    style: TextStyle,
): Dp {
    val density = LocalDensity.current

    return with(density) {
        val lineHeightSp = style.lineHeight.takeOrElse { style.fontSize }
        val lineHeightPx = lineHeightSp.toPx()
        lineHeightPx.toDp()
    }
}