package net.thechance.mena.trends.presentation.screen.managemytrends


import kotlinx.datetime.LocalDateTime
import net.thechance.mena.trends.domain.entity.Category
import org.jetbrains.compose.resources.StringResource
data class ManageTrendsUiState(
    val reels: List<ReelUiState> = emptyList(),
    val userName: String = "",
    val profileImageUrl: String = "",
    val currentTab: String = "",
    val isLoading: Boolean = true,
    val errorMessage: StringResource? = null,
) {
    companion object {
        fun preview() = ManageTrendsUiState(
            reels = listOf(
                ReelUiState(
                    id = 1,
                    thumbnailUrl = "https://example.com/thumbnail1.jpg",
                    videoUrl = "https://example.com/video1.mp4",
                    description = "Sample trend video 1: Exploring new ideas in tech.",
                    likesCount = 150,
                    viewsCount = 1200,
                    createdAt = "",
                    categories = listOf(
                        Category(
                            id = 1,
                            name = "Technology",
                            emoji = ""
                        ),
                        Category(
                            id = 2, name = "Innovation",
                            emoji = ""
                        )
                    )
                ),
                ReelUiState(
                    id = 2,
                    thumbnailUrl = "https://example.com/thumbnail2.jpg",
                    videoUrl = "https://example.com/video2.mp4",
                    description = "Sample trend video 2: Daily life hacks for productivity.",
                    likesCount = 89,
                    viewsCount = 650,
                    createdAt = "",
                    categories = listOf(
                        Category(
                            id = 3, name = "Lifestyle",
                            emoji =""
                        ),
                        Category(
                            id = 4, name = "Productivity",
                            emoji = ""
                        )
                    )
                ),
                ReelUiState(
                    id = 3,
                    thumbnailUrl = "https://example.com/thumbnail3.jpg",
                    videoUrl = "https://example.com/video3.mp4",
                    description = "Sample trend video 3: Cooking a quick meal.",
                    likesCount = 220,
                    viewsCount = 1800,
                    createdAt ="",
                    categories = listOf(
                        Category(
                            id = 5, name = "Food",
                            emoji = ""
                        ),
                        Category(
                            id = 6, name = "Quick Recipes",
                            emoji = ""
                        )
                    )
                )
            ),
            userName = "John Doe",
            profileImageUrl = "https://example.com/profile.jpg",
            currentTab = "My Trends",
            isLoading = false,
            errorMessage = null
        )
    }
}
data class ReelUiState(
    val id: Int,
    val thumbnailUrl: String,
    val videoUrl: String,
    val description: String,
    val likesCount: Int,
    val viewsCount: Int,
    val createdAt: LocalDateTime,
    val categories: List<Category>
)