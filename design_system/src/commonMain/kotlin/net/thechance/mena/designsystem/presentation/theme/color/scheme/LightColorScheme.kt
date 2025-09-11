package net.thechance.mena.designsystem.presentation.theme.color.scheme

import net.thechance.mena.designsystem.presentation.theme.color.Black
import net.thechance.mena.designsystem.presentation.theme.color.White
import net.thechance.mena.designsystem.presentation.theme.color.White38
import net.thechance.mena.designsystem.presentation.theme.color.White60
import net.thechance.mena.designsystem.presentation.theme.color.colorPalette

internal val LightColorScheme = ColorScheme(
    brand = ColorScheme.Brand(
        brand = colorPalette.navy._900,
        brandVariant = colorPalette.navy._50,
        onBrand = White
    ),
    primary = ColorScheme.Primary(
        primary = Black,
        onPrimary = White,
        onPrimaryBody = White60,
        onPrimaryHint = White38
    ),
    secondary = ColorScheme.Secondary(
        secondary = colorPalette.coffee._800,
        secondaryText = colorPalette.coffee._600,
        secondaryVariant = colorPalette.coffee._200
    ),
    border = ColorScheme.Border(
        disabled = colorPalette.gray._400,
        brand = colorPalette.navy._900,
        error = colorPalette.red._700,
        success = colorPalette.green._700
    ),
    background = ColorScheme.Background(
        surfaceLow = colorPalette.gray._50,
        surface = colorPalette.gray._200,
        surfaceHigh = colorPalette.gray._300,
        bgError = colorPalette.red._50,
        bgWarning = colorPalette.yellow._50,
        bgSuccess = colorPalette.green._50
    ),
    shadePrimary = colorPalette.gray._800,
    shadeSecondary = colorPalette.gray._600,
    shadeTertiary = colorPalette.gray._500,
    stroke = colorPalette.gray._300,
    textDisabled = colorPalette.gray._300,
    disabled = colorPalette.gray._400,
    error = colorPalette.red._700,
    warning = colorPalette.yellow._600,
    success = colorPalette.green._600,
    defaultCheckboxColors = ColorScheme.CheckboxColors(
        containerColor = Black,
        contentColor = White,
        disabledContainerColor = colorPalette.gray._300,
        disabledContentColor = colorPalette.gray._400,
        uncheckedBorderColor = colorPalette.gray._400,
        uncheckedContainerColor = colorPalette.gray._50,
        labelColor = colorPalette.gray._800,
        uncheckLabelColor = colorPalette.gray._500,
        disabledLabelColor = colorPalette.gray._400
    ),
    defaultRadioButtonColors = ColorScheme.RadioButtonColors(
        selectedColor = Black,
        unselectedColor = colorPalette.gray._400,
        unselectedContentColor = colorPalette.gray._50,
        disabledSelectedBorderColor = colorPalette.gray._300,
        disabledUnselectedBorderColor = colorPalette.gray._400,
        selectedLabelColor = colorPalette.gray._800,
        unselectedLabelColor = colorPalette.gray._500,
        disabledLabelColor = colorPalette.gray._400
    )
)