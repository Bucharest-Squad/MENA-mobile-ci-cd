package net.thechance.mena.designsystem.presentation.component.check_box

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import net.thechance.mena.designsystem.presentation.theme.color.scheme.ColorScheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme

@Immutable
object CheckboxDefaults {
    val shape: Shape
        @Composable get() = RoundedCornerShape(Theme.radius.xs)

    val intermediateLineShape: Shape
        @Composable get() = RoundedCornerShape(Theme.radius.xxs)

    val ContentPadding = PaddingValues(3.dp)

    @Composable
    fun checkboxColors() = Theme.colorScheme.defaultCheckboxColors

    @Composable
    fun checkboxColors(
        containerColor: Color = Color.Unspecified,
        contentColor: Color = Color.Unspecified,
        disabledContainerColor: Color = Color.Unspecified,
        disabledContentColor: Color = Color.Unspecified,
        uncheckedBorderColor: Color = Color.Unspecified,
        uncheckedContainerColor: Color = Color.Unspecified
    ): ColorScheme.CheckboxColors = Theme.colorScheme.defaultCheckboxColors.copy(
        containerColor = containerColor,
        contentColor = contentColor,
        disabledContainerColor = disabledContainerColor,
        disabledContentColor = disabledContentColor,
        uncheckedBorderColor = uncheckedBorderColor,
        uncheckedContainerColor = uncheckedContainerColor
    )
}