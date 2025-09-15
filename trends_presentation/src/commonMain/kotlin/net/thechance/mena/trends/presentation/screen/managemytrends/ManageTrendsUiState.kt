package net.thechance.mena.trends.presentation.screen.managemytrends


import net.thechance.mena.trends.domain.entity.Category
import org.jetbrains.compose.resources.StringResource
data class ManageTrendsUiState(
    val reels: List<ReelUiState> = emptyList(),
    val userName: String = "",
    val profileImageUrl: String = "",
    val currentTab: String = "",
    val isLoading: Boolean = true,
    val errorMessage: StringResource? = null,
)
data class ReelUiState(
    val id: Int,
    val thumbnailUrl: String,
)