package net.thechance.mena.dukan.presentation.screen.CreateDukan.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mena.dukan_presentation.generated.resources.Res
import mena.dukan_presentation.generated.resources.alert
import mena.dukan_presentation.generated.resources.category
import mena.dukan_presentation.generated.resources.dukan_name
import mena.dukan_presentation.generated.resources.enter_dukan_name
import mena.dukan_presentation.generated.resources.enter_your_dukan_information
import mena.dukan_presentation.generated.resources.fill_name_and_select_category
import mena.dukan_presentation.generated.resources.ic_alert_circle
import mena.dukan_presentation.generated.resources.ic_edit
import mena.dukan_presentation.generated.resources.ic_shop
import mena.dukan_presentation.generated.resources.you_can_choose_up_to_3_categories
import net.thechance.mena.designsystem.presentation.component.chip.Chip
import net.thechance.mena.designsystem.presentation.component.icon.MenaIcon
import net.thechance.mena.designsystem.presentation.component.text.MenaText
import net.thechance.mena.designsystem.presentation.component.textField.TextField
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import net.thechance.mena.dukan.domain.entity.Category
import net.thechance.mena.dukan.presentation.viewModel.createDukan.CreateDukanInteractionListener
import net.thechance.mena.dukan.presentation.viewModel.createDukan.CreateDukanUiState
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun CreateDukanContentBasicInformation(
    state: CreateDukanUiState,
    interactionListener: CreateDukanInteractionListener
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colorScheme.background.surface),
        contentPadding = PaddingValues(bottom = 12.dp)
    ) {
        item {
            HeaderSection()
        }

        item {
            TextField(
                value = state.name,
                onValueChanged = interactionListener::onNameChanged,
                placeholder = stringResource(Res.string.enter_dukan_name),
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 12.dp),
                leadingIcon = painterResource(Res.drawable.ic_shop),
                title = stringResource(Res.string.dukan_name),
                leadingIconTint = Theme.colorScheme.shadePrimary
            )
        }

        item {
            CategoryHeaderSection()
            CategorySelectionRow(
                state = state,
                listener = interactionListener
            )
        }
    }
}

@Composable
private fun HeaderSection() {
    MenaText(
        text = stringResource(Res.string.enter_your_dukan_information),
        style = Theme.typography.title.medium,
        color = Theme.colorScheme.shadePrimary,
        fontSize = 18.sp,
        lineHeight = 28.sp,
        maxLines = 1,
        modifier = Modifier.padding(horizontal = 16.dp),
        textAlign = TextAlign.Start,
        fontWeight = FontWeight.SemiBold,
    )
    MenaText(
        text = stringResource(Res.string.fill_name_and_select_category),
        style = Theme.typography.body.small,
        color = Theme.colorScheme.shadeSecondary,
        fontSize = 18.sp,
        lineHeight = 22.sp,
        maxLines = 1,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
        textAlign = TextAlign.Start,
        fontWeight = FontWeight.Normal
    )
}

@Composable
private fun CategoryHeaderSection() {
    MenaText(
        text = stringResource(Res.string.category),
        style = Theme.typography.title.small,
        color = Theme.colorScheme.shadePrimary,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        maxLines = 1,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 4.dp),
        textAlign = TextAlign.Start,
        fontWeight = FontWeight.Medium,
    )

    Row {
        MenaIcon(
            modifier = Modifier.padding(start = 16.dp, end = 2.dp),
            painter = painterResource(Res.drawable.ic_alert_circle),
            contentDescription = stringResource(Res.string.alert)
        )
        MenaText(
            text = stringResource(Res.string.you_can_choose_up_to_3_categories),
            style = Theme.typography.title.small,
            color = Theme.colorScheme.shadePrimary,
            fontSize = 10.sp,
            lineHeight = 16.sp,
            maxLines = 1,
            modifier = Modifier.padding(end = 16.dp, bottom = 8.dp),
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.Medium,
        )
    }
}

@Composable
private fun CategorySelectionRow(
    state: CreateDukanUiState,
    listener: CreateDukanInteractionListener
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        state.availableCategories.forEach { category ->
            item {
                CategoryChip(
                    category = category,
                    state = state,
                    listener = listener
                )
            }
        }
    }
}

@Composable
private fun CategoryChip(
    category: Category,
    state: CreateDukanUiState,
    listener: CreateDukanInteractionListener
) {
    val isSelected = state.selectedCategories.contains(category)
    val isEnabled = isSelected || state.selectedCategories.size < 3

    Chip(
        text = category.name,
        painter = painterResource(Res.drawable.ic_edit),
        isSelected = isSelected,
        isEnabled = isEnabled,
        modifier = Modifier,
        iconSize = 16.dp,
        shape = RoundedCornerShape(Theme.radius.full),
        onClick = {
            when {
                isSelected -> listener.onCategoryDeselected(category)
                state.selectedCategories.size < 3 -> listener.onCategorySelected(category)
            }
        },
    )
}
