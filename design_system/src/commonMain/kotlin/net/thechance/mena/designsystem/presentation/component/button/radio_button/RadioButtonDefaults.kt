package net.thechance.mena.designsystem.presentation.component.button.radio_button

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import net.thechance.mena.designsystem.presentation.theme.color.scheme.ColorScheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme

@Immutable
object RadioButtonDefaults {
    val shape: Shape
        @Composable get() = RoundedCornerShape(Theme.radius.full)


    @Composable
    fun radioButtonColors() = Theme.colorScheme.defaultRadioButtonColors

    @Composable
    fun radioButtonColors(
        selectedColor: Color = Color.Unspecified,
        unselectedColor: Color = Color.Unspecified,
        unselectedContentColor: Color = Color.Unspecified,
        disabledColor: Color = Color.Unspecified,
        disabledBorderColor: Color = Color.Unspecified,
        selectedLabelColor: Color = Color.Unspecified,
        unselectedLabelColor: Color = Color.Unspecified,
        disabledLabelColor: Color = Color.Unspecified
    ): ColorScheme.RadioButtonColors = Theme.colorScheme.defaultRadioButtonColors.copy(
        selectedColor = selectedColor,
        unselectedBorderColor = unselectedColor,
        unselectedContentColor = unselectedContentColor,
        disabledSelectedBorderColor = disabledColor,
        disabledUnselectedBorderColor = disabledBorderColor,
        selectedLabelColor = selectedLabelColor,
        unselectedLabelColor = unselectedLabelColor,
        disabledLabelColor = disabledLabelColor
    )

}