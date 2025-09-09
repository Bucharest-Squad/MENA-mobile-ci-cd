package net.thechance.mena.designsystem.presentation.theme.radius

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Radius(
    val xxs: Dp,
    val xs: Dp,
    val md: Dp,
    val xl: Dp,
    val _2xl: Dp,
    val _4xl: Dp,
    val full: Dp
)

val MenaRadius = Radius(
    xxs = 2.dp,
    xs = 4.dp,
    md = 8.dp,
    xl = 12.dp,
    _2xl = 16.dp,
    _4xl = 24.dp,
    full = 100.dp
)

val LocalRadius = staticCompositionLocalOf { MenaRadius }