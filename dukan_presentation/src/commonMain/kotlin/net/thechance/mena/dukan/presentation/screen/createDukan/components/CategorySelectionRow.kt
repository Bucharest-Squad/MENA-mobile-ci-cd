package net.thechance.mena.dukan.presentation.screen.createDukan.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mena.dukan_presentation.generated.resources.Res
import mena.dukan_presentation.generated.resources.ic_edit
import net.thechance.mena.designsystem.presentation.component.chip.Chip
import net.thechance.mena.designsystem.presentation.theme.theme.Theme
import net.thechance.mena.dukan.domain.entity.Category
import org.jetbrains.compose.resources.painterResource

private const val MAX_CATEGORIES = 3

@Composable
fun CategorySelectionRow(
    availableCategories: List<Category>,
    selectedCategories: Set<Category>,
    onCategorySelected: (Category) -> Unit,
    onCategoryDeselected: (Category) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = Theme.spacing._16),
        horizontalArrangement = Arrangement.spacedBy(Theme.spacing._8)
    ) {
        availableCategories.forEach { category ->
            item {
                val isSelected = selectedCategories.contains(category)
                val isEnabled = isSelected || selectedCategories.size < MAX_CATEGORIES
                CategoryChip(
                    category = category,
                    isSelected = isSelected,
                    isEnabled = isEnabled,
                    onCategorySelected = onCategorySelected,
                    onCategoryDeselected = onCategoryDeselected
                )
            }
        }
    }
}

@Composable
private fun CategoryChip(
    category: Category,
    isSelected: Boolean,
    isEnabled: Boolean,
    onCategorySelected: (Category) -> Unit,
    onCategoryDeselected: (Category) -> Unit
) {
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
                isSelected -> onCategoryDeselected(category)
                isEnabled -> onCategorySelected(category)
            }
        },
    )
}