package net.thechance.mena.trends.presentation.screen.user_reel

data class UserReelState(
    val id: String = "",
    val username: String = "",
    val thumbnail: String = "",
    val createdAt: String = "",
    val isLoading: Boolean = false,
    val viewsCount: Int = 0,
    val likesCount: Int = 0,
    val description: String = "",
    val isConfirmationDialogVisible: Boolean = false,
    val isReelDeleted: Boolean = false,
    val isDescriptionExpanded: Boolean = false
)