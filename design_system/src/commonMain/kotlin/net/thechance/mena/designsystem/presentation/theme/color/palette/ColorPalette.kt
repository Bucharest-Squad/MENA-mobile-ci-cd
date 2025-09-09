package net.thechance.mena.designsystem.presentation.theme.color.palette

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
data class ColorPalette(
    val navy: ColorScale,
    val coffee: ColorScale,
    val gray: ColorScale,
    val red: ColorScale,
    val yellow: ColorScale,
    val green: ColorScale
) {
    data class ColorScale(
        val _50: Color,
        val _100: Color,
        val _200: Color,
        val _300: Color,
        val _400: Color,
        val _500: Color,
        val _600: Color,
        val _700: Color,
        val _800: Color,
        val _900: Color
    )
}