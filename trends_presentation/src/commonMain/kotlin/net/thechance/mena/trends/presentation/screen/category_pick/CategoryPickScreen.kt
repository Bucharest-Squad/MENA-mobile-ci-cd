package net.thechance.mena.trends.presentation.screen.category_pick

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import mena.trends_presentation.generated.resources.Res
import mena.trends_presentation.generated.resources.change_tags
import mena.trends_presentation.generated.resources.choose_interests
import mena.trends_presentation.generated.resources.help_text
import mena.trends_presentation.generated.resources.ic_arrow_left
import mena.trends_presentation.generated.resources.save_change
import net.thechance.mena.designsystem.presentation.component.appBar.AppBar
import net.thechance.mena.designsystem.presentation.component.button.Button
import net.thechance.mena.designsystem.presentation.component.icon.MenaIcon
import net.thechance.mena.designsystem.presentation.component.text.MenaText
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import net.thechance.mena.trends.presentation.shared.component.CategoryItem
import net.thechance.mena.trends.presentation.shared.util.ObserveAsEffect
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CategoryPickScreen(
    navController: NavController,
    viewModel: CategoryPickViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEffect(viewModel.effect) { effect ->
        when (effect) {
            is CategoryPickScreenEffect.NavigateBack -> navController.popBackStack()
            is CategoryPickScreenEffect.NavigateToTrends -> navController.navigate("") // TODO
        }
    }

    CategoryPickScreenContent(
        state = state,
        listener = viewModel,
    )
}

@Composable
private fun CategoryPickScreenContent(
    state: CategoryPickScreenState,
    listener: CategoryPickInteractionListener,
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
                title = stringResource(Res.string.change_tags),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Theme.colorScheme.background.surface)
                    .padding(bottom = Theme.spacing._16),
            )

            if (!state.isLoading) {
                MenaText(
                    text = stringResource(Res.string.choose_interests),
                    style = Theme.typography.title.medium,
                    color = Theme.colorScheme.shadePrimary,
                    modifier = Modifier
                        .padding(
                            bottom = Theme.spacing._4,
                            start = Theme.spacing._16,
                            end = Theme.spacing._16
                        )
                )

                MenaText(
                    text = stringResource(Res.string.help_text),
                    style = Theme.typography.body.small,
                    color = Theme.colorScheme.shadeSecondary,
                    modifier = Modifier
                        .padding(
                            bottom = Theme.spacing._24,
                            start = Theme.spacing._16,
                            end = Theme.spacing._16
                        )
                )

                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Theme.spacing._16),
                ) {
                    state.categories.forEach { category ->
                        CategoryItem(
                            category = category,
                            onClick = { id -> listener.onCategoryClick(id) },
                            modifier = Modifier
                                .padding(bottom = Theme.spacing._12, end = Theme.spacing._8)
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = { listener.onSaveClick() },
                    isEnabled = state.isSavingEnabled(),
                    isLoading = state.isSaveButtonLoading,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = Theme.spacing._24,
                            start = Theme.spacing._16,
                            end = Theme.spacing._16
                        ),
                    shape = RoundedCornerShape(Theme.spacing._12),
                    containerColor = Theme.colorScheme.primary.primary,
                    disabledContainerColor = Theme.colorScheme.primary.primary.copy(alpha = 0.5f),
                    contentColor = Theme.colorScheme.primary.onPrimary,
                    disabledContentColor = Theme.colorScheme.primary.onPrimary.copy(alpha = 0.5f),
                ) { contentColor ->
                    MenaText(
                        text = stringResource(Res.string.save_change),
                        color = contentColor,
                        style = Theme.typography.label.medium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(
                            horizontal = Theme.spacing._24,
                            vertical = Theme.spacing._12
                        )
                    )
                }
            }
        }

        if (state.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Theme.colorScheme.background.surface.copy(alpha = 0.7f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}