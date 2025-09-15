package net.thechance.mena.dukan.presentation.screen.CreateDukan.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import mena.dukan_presentation.generated.resources.Res
import mena.dukan_presentation.generated.resources.alert
import mena.dukan_presentation.generated.resources.category
import mena.dukan_presentation.generated.resources.dukan_name
import mena.dukan_presentation.generated.resources.enter_dukan_name
import mena.dukan_presentation.generated.resources.enter_your_dukan_information
import mena.dukan_presentation.generated.resources.fill_name_and_select_category
import mena.dukan_presentation.generated.resources.ic_alert_circle
import mena.dukan_presentation.generated.resources.ic_shop
import mena.dukan_presentation.generated.resources.you_can_choose_up_to_3_categories
import net.thechance.mena.designsystem.presentation.component.icon.MenaIcon
import net.thechance.mena.designsystem.presentation.component.text.MenaText
import net.thechance.mena.designsystem.presentation.component.textField.TextField
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import net.thechance.mena.dukan.presentation.screen.createDukan.components.CategorySelectionRow
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
        contentPadding = PaddingValues(bottom = Theme.spacing._12)
    ) {
        item {
            HeaderSection()
        }

        item {
            TextField(
                value = state.name,
                onValueChanged = interactionListener::onNameChanged,
                placeholder = stringResource(Res.string.enter_dukan_name),
                modifier = Modifier.padding(
                    start = Theme.spacing._16,
                    end = Theme.spacing._16,
                    bottom = Theme.spacing._12
                ),
                leadingIcon = painterResource(Res.drawable.ic_shop),
                title = stringResource(Res.string.dukan_name),
                leadingIconTint = Theme.colorScheme.shadePrimary
            )
        }

        item {
            CategoryHeaderSection()
            CategorySelectionRow(
                availableCategories = state.availableCategories,
                selectedCategories = state.selectedCategories,
                onCategorySelected = interactionListener::onCategorySelected,
                onCategoryDeselected = interactionListener::onCategoryDeselected
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
        maxLines = 1,
        modifier = Modifier.padding(horizontal = Theme.spacing._16),
        textAlign = TextAlign.Start
    )

    MenaText(
        text = stringResource(Res.string.fill_name_and_select_category),
        style = Theme.typography.body.small,
        color = Theme.colorScheme.shadeSecondary,
        maxLines = 1,
        modifier = Modifier.padding(
            start = Theme.spacing._16,
            end = Theme.spacing._16,
            bottom = Theme.spacing._16
        ),
        textAlign = TextAlign.Start
    )
}

@Composable
private fun CategoryHeaderSection() {
    MenaText(
        text = stringResource(Res.string.category),
        style = Theme.typography.title.small,
        color = Theme.colorScheme.shadePrimary,
        maxLines = 1,
        modifier = Modifier.padding(
            start = Theme.spacing._16,
            end = Theme.spacing._16,
            bottom = Theme.spacing._4
        ),
        textAlign = TextAlign.Start
    )

    Row {
        MenaIcon(
            modifier = Modifier.padding(start = Theme.spacing._16, end = Theme.spacing._2),
            painter = painterResource(Res.drawable.ic_alert_circle),
            contentDescription = stringResource(Res.string.alert)
        )
        MenaText(
            text = stringResource(Res.string.you_can_choose_up_to_3_categories),
            style = Theme.typography.title.small,
            color = Theme.colorScheme.shadePrimary,
            maxLines = 1,
            modifier = Modifier.padding(end = Theme.spacing._16, bottom = Theme.spacing._8),
            textAlign = TextAlign.Start
        )
    }
}

