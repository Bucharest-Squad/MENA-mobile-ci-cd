package net.thechance.mena.designsystem.presentation.component.checkBox

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import mena.design_system.generated.resources.Res
import mena.design_system.generated.resources.checkmark
import net.thechance.mena.designsystem.presentation.theme.color.scheme.ColorScheme
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun Checkbox(
    checkedState: ToggleableState,
    onCheckedChange: ((ToggleableState) -> Unit)?,
    modifier: Modifier = Modifier,
    label: String? = null,
    isEnabled: Boolean = true,
    shape: Shape = CheckboxDefaults.shape,
    intermediateLineShape: Shape = CheckboxDefaults.intermediateLineShape,
    colors: ColorScheme.CheckboxColors = CheckboxDefaults.checkboxColors(),
    contentPadding: PaddingValues = CheckboxDefaults.ContentPadding
) {

    val animatedContainerColor by animateColorAsState(
        targetValue = if (isEnabled)
            colors.containerColor else colors.disabledContainerColor
    )

    val animatedContentColor by animateColorAsState(
        targetValue = if (isEnabled)
            colors.contentColor else colors.disabledContentColor
    )

    val animatedUncheckedBorderColor by animateColorAsState(
        targetValue = if (checkedState == ToggleableState.Off)
            colors.uncheckedBorderColor else Color.Unspecified
    )


    val animatedUncheckedContainerColor by animateColorAsState(
        targetValue = if (checkedState == ToggleableState.Off)
            colors.uncheckedContainerColor else animatedContainerColor
    )

    val animatedUncheckedBorderDp by animateDpAsState(
        targetValue = if (checkedState == ToggleableState.Off)
            1.dp else 0.dp
    )


    val animatedUncheckedLabelColor by animateColorAsState(
        targetValue = if (checkedState == ToggleableState.Off)
            colors.uncheckLabelColor else colors.labelColor
    )

    val animatedLabelColor by animateColorAsState(
        targetValue = if (isEnabled)
            animatedUncheckedLabelColor else colors.disabledLabelColor
    )

    val clickableModifier = onCheckedChange?.let {
        Modifier.clickable(
            enabled = isEnabled,
            indication = null,
            interactionSource = remember { MutableInteractionSource() },
            role = Role.Checkbox
        ) {
            onCheckedChange(checkedState)
        }
    } ?: Modifier

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
    ) {
        Box(
            modifier = Modifier
                .size(18.dp)
                .background(
                    color = animatedUncheckedContainerColor,
                    shape = shape
                )
                .border(animatedUncheckedBorderDp, animatedUncheckedBorderColor, shape)
                .clip(shape)
                .then(clickableModifier)
                .padding(contentPadding),
            contentAlignment = Alignment.Center
        ) {
            when (checkedState) {
                ToggleableState.On -> Icon(
                    modifier = Modifier,
                    tint = animatedContentColor,
                    painter = painterResource(Res.drawable.checkmark),
                    contentDescription = "Checkmark icon"
                )

                ToggleableState.Indeterminate -> HorizontalDivider(
                    thickness = 2.dp,
                    color = animatedContentColor,
                    modifier = Modifier
                        .size(10.dp, 2.dp)
                        .clip(intermediateLineShape)
                )

                ToggleableState.Off -> {}
            }
        }

        label?.let { text ->
            Text(
                text = text,
                color = animatedLabelColor,
                style = Theme.typography.label.medium
            )
        }

    }
}

@Preview
@Composable
private fun CheckboxPreview() {
    MenaTheme {
        var checkboxState by remember { mutableStateOf(ToggleableState.Off) }

        Box(
            modifier = Modifier
                .size(180.dp)
                .background(Theme.colorScheme.background.surface),
            contentAlignment = Alignment.Center
        ) {
            Checkbox(
                checkedState = checkboxState,
                label = "Label",
                onCheckedChange = { currentState ->
                    checkboxState = currentState.getNextCheckboxState()
                }
            )
        }
    }
}
