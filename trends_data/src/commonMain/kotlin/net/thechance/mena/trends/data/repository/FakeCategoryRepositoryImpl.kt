package net.thechance.mena.trends.data.repository

import net.thechance.mena.trends.domain.entity.Category
import net.thechance.mena.trends.domain.repository.CategoryRepository
import org.koin.core.annotation.Single

// @Single(binds = [CategoryRepository::class])
class FakeCategoryRepositoryImpl : CategoryRepository {
    override suspend fun getAllCategories(): List<Category> {
        return FakeCategoriesSource.categories
    }

    override suspend fun isCategoriesAlreadySelectedByUser(): Boolean {
        return FakeCategoriesSource.userCategories.isNotEmpty()
    }

    override suspend fun updateUserInterestedCategories(categoriesIds: List<String>) {
        FakeCategoriesSource.userCategories.clear()
        FakeCategoriesSource.categories.forEach { category ->
            if (categoriesIds.contains(category.id)) {
                FakeCategoriesSource.userCategories.add(category)
            }
        }
    }
}

private object FakeCategoriesSource {
    val categories = mutableListOf(
        Category(id = "1", name = "Sports", emoji = "⚽️"),
        Category(id = "2", name = "Politics", emoji = "⚖️"),
        Category(id = "3", name = "Technology", emoji = "💻"),
        Category(id = "4", name = "Health", emoji = "🩺"),
        Category(id = "5", name = "Education", emoji = "📚"),
        Category(id = "6", name = "Science", emoji = "🔬"),
        Category(id = "7", name = "Music", emoji = "🎵"),
        Category(id = "8", name = "Movies", emoji = "🎬"),
        Category(id = "9", name = "Art", emoji = "🎨"),
        Category(id = "10", name = "Travel", emoji = "✈️"),
        Category(id = "11", name = "Food", emoji = "🍔"),
        Category(id = "12", name = "Fashion", emoji = "👗"),
        Category(id = "13", name = "History", emoji = "📜"),
        Category(id = "14", name = "Books", emoji = "📖"),
        Category(id = "15", name = "Gaming", emoji = "🎮"),
        Category(id = "16", name = "Nature", emoji = "🌳"),
        Category(id = "17", name = "Animals", emoji = "🐶"),
        Category(id = "18", name = "Space", emoji = "🚀"),
        Category(id = "19", name = "Finance", emoji = "💰"),
        Category(id = "20", name = "Business", emoji = "📈"),
        Category(id = "21", name = "Economy", emoji = "💹"),
        Category(id = "22", name = "Fitness", emoji = "🏋️")
    )

    val userCategories: MutableList<Category> = mutableListOf()
}