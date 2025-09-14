package net.thechance.mena.trends.presentation.screen.interestpick

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import mena.trends_presentation.generated.resources.Res
import mena.trends_presentation.generated.resources.ic_arrow_left
import net.thechance.mena.designsystem.presentation.component.appBar.AppBar
import net.thechance.mena.designsystem.presentation.component.button.Button
import net.thechance.mena.designsystem.presentation.component.icon.MenaIcon
import net.thechance.mena.designsystem.presentation.component.text.MenaText
import net.thechance.mena.designsystem.presentation.theme.theme.MenaTheme
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.runtime.getValue
import net.thechance.mena.trends.presentation.screen.interestpick.InterestsUiState.CategoryUiModel
import net.thechance.mena.trends.presentation.shared.component.CategoryItem
import net.thechance.mena.trends.presentation.shared.model.CategoryUiState
import net.thechance.mena.trends.presentation.shared.model.Selectable

@Composable
fun InterestsScreen(
    viewModel: InterestsPickViewModel = koinViewModel(),
    onBack: () -> Unit,
    onSave: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is InterestsPickUiEffect.NavigateBack -> onBack()
                is InterestsPickUiEffect.NavigateToSave -> onSave()
                is InterestsPickUiEffect.ShowError -> {}
            }
        }
    }

    InterestsContent(
        state = state,
        listener = viewModel,
    )
}

@Composable
fun InterestsContent(
    state: InterestsUiState,
    listener: InterestsPickInteractionListener,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Theme.colorScheme.background.surface)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
                AppBar(
                    leadingContent = {
                        MenaIcon(
                            painter = painterResource(Res.drawable.ic_arrow_left),
                            contentDescription = null,
                            modifier = Modifier.clickable { listener.onBackClick() }
                        )
                    },
                    title = "Change tags",
                    modifier = Modifier
                    .fillMaxWidth()
                    .background(Theme.colorScheme.background.surface)
                    .padding(bottom = 16.dp),
                )

            MenaText(
                text = "Choose your interests",
                style = Theme.typography.title.medium,
                fontWeight = FontWeight.Bold,
                color = Theme.colorScheme.shadePrimary,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 4.dp)
            )

            MenaText(
                text = "Help us understand your interests so we can show you trends that match your taste.",
                style = Theme.typography.body.small,
                color = Theme.colorScheme.shadeSecondary,
                modifier = Modifier.padding(horizontal = 16.dp)
                    .padding(bottom = 24.dp)
            )

            if (state.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                ) {
                    state.categories.forEach { category ->
                        CategoryItem(
                            category = Selectable(
                                value = CategoryUiState(
                                    id = category.id,
                                    name = category.name,
                                    emoji = category.emoji
                                ),
                                isSelected = category.isSelected
                            ),
                            onClick = { id -> listener.onCategoryClick(id) },
                            modifier = Modifier.padding(bottom = 12.dp)
                                .padding(end = 8.dp)
                        )
                    }
                }
            }
        }

        Button(
            onClick = { listener.onSaveClick() },
            isEnabled = !state.isSaving,
            isLoading = state.isSaving,
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 24.dp)
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(12.dp),
            containerColor = Theme.colorScheme.primary.primary,
            disabledContainerColor = Theme.colorScheme.primary.primary.copy(alpha = 0.5f),
            contentColor = Theme.colorScheme.primary.onPrimary,
            disabledContentColor = Theme.colorScheme.primary.onPrimary.copy(alpha = 0.5f),
        ) { contentColor ->
            MenaText(
                text = "Save change",
                color = contentColor,
                style = Theme.typography.label.medium,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
            )
        }
    }
}


@Preview()
@Composable
fun InterestsContentPreview() {
    val mockState = InterestsUiState(
        isLoading = false,
        isSaving = false,
        categories = listOf(
            CategoryUiModel(id = 1, name = "Sport", emoji = "🎬", isSelected = true),
            CategoryUiModel(id = 2, name = "Food", emoji = "🎵", isSelected = false),
            CategoryUiModel(id = 3, name = "Educationnnn", emoji = "⚽", isSelected = false),
            CategoryUiModel(id = 4, name = "Food", emoji = "🍔", isSelected = true),
            CategoryUiModel(id = 5, name = "Travel", emoji = "✈️", isSelected = false),
            CategoryUiModel(id = 1, name = "Sport", emoji = "🎬", isSelected = true),
            CategoryUiModel(id = 2, name = "Food", emoji = "🎵", isSelected = false),
            CategoryUiModel(id = 3, name = "Educationnnn", emoji = "⚽", isSelected = false),
            CategoryUiModel(id = 4, name = "Food", emoji = "🍔", isSelected = true),
            CategoryUiModel(id = 5, name = "Travel", emoji = "✈️", isSelected = false),
        )
    )

    val mockListener = object : InterestsPickInteractionListener {
        override fun onBackClick() {}
        override fun onSaveClick() {}
        override fun onCategoryClick(id: Int) {}
    }

    MenaTheme {
        InterestsContent(
            state = mockState,
            listener = mockListener
        )
    }
}