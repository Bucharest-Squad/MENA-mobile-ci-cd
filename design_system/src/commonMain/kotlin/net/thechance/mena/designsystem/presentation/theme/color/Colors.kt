package net.thechance.mena.designsystem.presentation.theme.color

import androidx.compose.ui.graphics.Color
import net.thechance.mena.designsystem.presentation.theme.color.palette.ColorPalette
import net.thechance.mena.designsystem.presentation.theme.color.palette.ColorPalette.ColorScale

val Black: Color = Color(0xFF000000)
val colorPalette = ColorPalette(
    navy = ColorScale(
        _50 = Color(0xFFE6EBFA),
        _100 = Color(0xFFc3cfe1),
        _200 = Color(0xFFA1AEC4),
        _300 = Color(0xFF7F8EA8),
        _400 = Color(0xFF677793),
        _500 = Color(0xFF4E6180),
        _600 = Color(0xFF415470),
        _700 = Color(0xFF32425A),
        _800 = Color(0xFF233045),
        _900 = Color(0xFF111D2E)
    ),
    coffee = ColorScale(
        _50 = Color(0xFFFCF9F5),
        _100 = Color(0xFFF5F0E9),
        _200 = Color(0xFFF0E7DA),
        _300 = Color(0xFFE0D1BC),
        _400 = Color(0xFFCCB89B),
        _500 = Color(0xFFB29B79),
        _600 = Color(0xFF997D56),
        _700 = Color(0xFF806338),
        _800 = Color(0xFF66481F),
        _900 = Color(0xFF4D330F)
    ),
    gray = ColorScale(
        _50 = Color(0xFFFFFFFF),
        _100 = Color(0xFFF8FAFC),
        _200 = Color(0xFFFF2F4F7),
        _300 = Color(0xFFEAECF0),
        _400 = Color(0xFFBEC0CC),
        _500 = Color(0xFF818599),
        _600 = Color(0xFF3E4252),
        _700 = Color(0xFF12141C),
        _800 = Color(0xFF0E1017),
        _900 = Color(0xFF0A0C12),
    ),
    red = ColorScale(
        _50 = Color(0xFFFEE4E2),
        _100 = Color(0xFFFEE4E2),
        _200 = Color(0xFFFECDCA),
        _300 = Color(0xFFFDA29B),
        _400 = Color(0xFFF97066),
        _500 = Color(0xFFF04438),
        _600 = Color(0xFFD92D20),
        _700 = Color(0xFFB42318),
        _800 = Color(0xFF912018),
        _900 = Color(0xFF7A271A)
    ),
    yellow = ColorScale(
        _50 = Color(0xFFFFFAEB),
        _100 = Color(0xFFFEF0C7),
        _200 = Color(0xFFFEDF89),
        _300 = Color(0xFFFEC84B),
        _400 = Color(0xFFFDB022),
        _500 = Color(0xFFF79009),
        _600 = Color(0xFFDC6803),
        _700 = Color(0xFFB54708),
        _800 = Color(0xFF93370D),
        _900 = Color(0xFF7A2E0E)
    ),
    green = ColorScale(
        _50 = Color(0xFFE6F6EA),
        _100 = Color(0xFFC3E8CB),
        _200 = Color(0xFF9BD9A9),
        _300 = Color(0xFF71CB87),
        _400 = Color(0xFF4EBF6D),
        _500 = Color(0xFF23B353),
        _600 = Color(0xFF19A44A),
        _700 = Color(0xFF06923E),
        _800 = Color(0xFF008133),
        _900 = Color(0xFF00621F)
    )
)
internal val White = Color(0xFFFFFFFF)
internal val White60 = White.copy(alpha = 0.6f)
internal val White38 = White.copy(alpha = 0.38f)