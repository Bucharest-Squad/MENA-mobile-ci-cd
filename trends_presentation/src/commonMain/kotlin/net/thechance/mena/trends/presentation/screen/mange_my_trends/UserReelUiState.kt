package net.thechance.mena.trends.presentation.screen.mange_my_trends

import org.jetbrains.compose.resources.StringResource

data class UserReelUiState(
    val username: String = "",
    val thumbnail: String = "",
    val createdAt: String = "",
    val isLoading: Boolean = false,
    val viewsCount: Int = 0,
    val likesCount: Int = 0,
    val description: String = "",
    val errorMessage: StringResource? = null,
    val isConfirmationDialogVisible: Boolean = false,
    val isReelDeleted: Boolean = false,
)